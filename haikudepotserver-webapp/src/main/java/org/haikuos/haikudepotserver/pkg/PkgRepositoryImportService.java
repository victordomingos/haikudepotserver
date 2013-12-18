/*
 * Copyright 2013, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.haikudepotserver.pkg;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Queues;
import com.google.common.io.InputSupplier;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.haikuos.haikudepotserver.dataobjects.Repository;
import org.haikuos.haikudepotserver.pkg.model.PkgRepositoryImportJob;
import org.haikuos.haikudepotserver.support.Closeables;
import org.haikuos.pkg.PkgIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>This object is responsible for migrating a HPKR file from a remote repository into the Haiku Depot Server
 * database.  It will copy the data into a local file and then work through it there.  Note that this does
 * not run the entire process in a single transaction; it will execute one transaction per package.  So if the
 * process fails, a repository update is likely to be partially imported.</p>
 *
 * <p>The system works by the caller lodging a request to update from a remote repository.  The request may be
 * later superceeded by another request for the same repository.  When the import process has capacity then it
 * will undertake the import process.</p>
 */

@Service
public class PkgRepositoryImportService {

    protected static Logger logger = LoggerFactory.getLogger(PkgRepositoryImportService.class);

    public final static int SIZE_QUEUE = 10;

    @Resource
    ServerRuntime serverRuntime;

    @Resource
    PkgImportService importPackageService;

    private ThreadPoolExecutor executor = null;

    private ArrayBlockingQueue<Runnable> runnables = Queues.newArrayBlockingQueue(SIZE_QUEUE);

    private ThreadPoolExecutor getExecutor() {
        if(null==executor) {
            executor = new ThreadPoolExecutor(
                    0, // core pool size
                    1, // max pool size
                    1l, // time to shutdown threads
                    TimeUnit.MINUTES,
                    runnables,
                    new ThreadPoolExecutor.AbortPolicy());
        }

        return executor;
    }

    /**
     * <p>This method will check that there is not already a job in the queue for this repository and then will
     * add it to the queue so that it is run at some time in the future.</p>
     * @param job
     */

    public void submit(final PkgRepositoryImportJob job) {
        Preconditions.checkNotNull(job);

        // first thing to do is to validate the request; does the repository exist and what is it's URL?
        Optional<Repository> repositoryOptional = Repository.getByCode(serverRuntime.getContext(), job.getCode());

        if(!repositoryOptional.isPresent()) {
            throw new RuntimeException("unable to import repository data because repository was not able to be found for code; "+job.getCode());
        }

        if(!Iterables.tryFind(runnables, new Predicate<Runnable>() {
            @Override
            public boolean apply(java.lang.Runnable input) {
                ImportRepositoryDataJobRunnable importRepositoryDataJobRunnable = (ImportRepositoryDataJobRunnable) input;
                return importRepositoryDataJobRunnable.equals(job);
            }
        }).isPresent()) {
            getExecutor().submit(new ImportRepositoryDataJobRunnable(this,job));
            logger.info("have submitted job to import repository data; {}", job.toString());
        }
        else {
            logger.info("ignoring job to import repository data as there is already one waiting; {}", job.toString());

        }
    }

    protected void run(PkgRepositoryImportJob job) {
        Preconditions.checkNotNull(job);

        Repository repository = Repository.getByCode(serverRuntime.getContext(), job.getCode()).get();
        URL url;

        try {
            url = new URL(repository.getUrl());
        }
        catch(MalformedURLException mue) {
            throw new IllegalStateException("the repository "+job.getCode()+" has a malformed url; "+repository.getUrl(),mue);
        }

        // now shift the URL's data into a temporary file and then process it.

        File temporaryFile = null;
        InputStream urlInputStream = null;

        try {

            urlInputStream = url.openStream();
            temporaryFile = File.createTempFile(job.getCode()+"__import",".hpkr");
            final InputStream finalUrlInputStream = urlInputStream;

            com.google.common.io.Files.copy(
                    new InputSupplier<InputStream>() {
                        @Override
                        public InputStream getInput() throws IOException {
                            return finalUrlInputStream;
                        }
                    },
                    temporaryFile);

            logger.info("did copy data for repository {} ({}) to temporary file",job.getCode(),url.toString());

            org.haikuos.pkg.HpkrFileExtractor fileExtractor = new org.haikuos.pkg.HpkrFileExtractor(temporaryFile);
            PkgIterator pkgIterator = new PkgIterator(fileExtractor.getPackageAttributesIterator());

            long startTimeMs = System.currentTimeMillis();
            logger.info("will process data for repository {}",job.getCode());

            while(pkgIterator.hasNext()) {
                importPackageService.run(repository.getObjectId(), pkgIterator.next());
            }

            logger.info("did process data for repository {} in {}ms",job.getCode(),System.currentTimeMillis()-startTimeMs);

        }
        catch(Throwable th) {
            logger.error("a problem has arisen processing a repository file for repository "+job.getCode()+" from url '"+url.toString()+"'",th);
        }
        finally {
            Closeables.closeQuietly(urlInputStream);

            if(null!=temporaryFile && temporaryFile.exists()) {
                temporaryFile.delete();
            }
        }

    }

    /**
     * <p>This is the object that gets enqueued to actually do the work.</p>
     */

    public static class ImportRepositoryDataJobRunnable implements Runnable {

        private PkgRepositoryImportJob job;

        private PkgRepositoryImportService service;

        public ImportRepositoryDataJobRunnable(
                PkgRepositoryImportService service,
                PkgRepositoryImportJob job) {
            Preconditions.checkNotNull(service);
            Preconditions.checkNotNull(job);
            this.service = service;
            this.job = job;
        }

        @Override
        public void run() {
            service.run(job);
        }

    }

}