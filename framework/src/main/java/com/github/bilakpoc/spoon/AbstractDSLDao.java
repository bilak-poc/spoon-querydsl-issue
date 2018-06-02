package com.github.bilakpoc.spoon;

import com.mysema.query.sql.RelationalPath;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.StringPath;

public abstract class AbstractDSLDao {

    protected StringPath rowid(RelationalPath<?> parent) {
          return new StringPath(PathMetadataFactory.forProperty(parent, "ROWID"));
    }

}
