/*
 * Copyright 2015, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haiku.haikudepotserver.dataobjects;

import com.google.common.base.Preconditions;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.haiku.haikudepotserver.dataobjects.auto._PkgProminence;

import java.util.List;

public class PkgProminence extends _PkgProminence {

    public static List<PkgProminence> findByPkg(ObjectContext context, Pkg pkg) {
        Preconditions.checkNotNull(context);
        Preconditions.checkArgument(null != pkg, "a package is required to identify the pkg prominences to return");
        SelectQuery query = new SelectQuery(PkgProminence.class, ExpressionFactory.matchExp(PKG_PROPERTY, pkg));
        return (List<PkgProminence>) context.performQuery(query);
    }

}