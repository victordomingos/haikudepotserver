package org.haikuos.haikudepotserver.pkg;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.haikuos.haikudepotserver.dataobjects.PkgCategory;
import org.haikuos.haikudepotserver.job.AbstractJobRunner;
import org.haikuos.haikudepotserver.job.model.JobSpecification;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public abstract class AbstractPkgCategorySpreadsheetJobRunner<T extends JobSpecification>
        extends AbstractJobRunner<T> {

    /**
     * <p>This string is inserted into a cell in order to indicate that the combination of the
     * package on the row and the category on the column are true.</p>
     */

    protected static String MARKER = "*";

    @Resource
    protected ServerRuntime serverRuntime;

    @Resource
    protected PkgOrchestrationService pkgOrchestrationService;

    protected List<String> getPkgCategoryCodes() {
        return Lists.transform(
                PkgCategory.getAll(serverRuntime.getContext()),
                new Function<PkgCategory, String>() {
                    @Override
                    public String apply(PkgCategory input) {
                        return input.getCode();
                    }
                }
        );
    }

    protected String[] getHeadingRow(List<String> pkgCategoryCodes) {
        List<String> headings = Lists.newArrayList();
        headings.add("pkg-name");
        headings.add("any-summary");
        headings.add("none");
        Collections.addAll(headings, pkgCategoryCodes.toArray(new String[pkgCategoryCodes.size()]));
        headings.add("action");
        return headings.toArray(new String[headings.size()]);
    }

}
