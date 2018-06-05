package com.github.bilakpoc.spoon;

import java.util.Map;

import org.springframework.jdbc.object.StoredProcedure;

public abstract class AbstractDSLDao extends StoredProcedure {

    @Override
    public Map<String, Object> execute(final Object... inParams) {

        return super.execute(inParams);
    }

}
