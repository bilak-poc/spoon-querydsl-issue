package com.github.bilakpoc.spoon.integration.booleanservice;

import com.github.bilakpoc.spoon.integration.ExternalService;

/**
 * @author Lukáš Vasek
 */
public enum BooleanServiceEnum implements ExternalService {

    V1("booleanService_V1");

    private final String nameWithVersion;

    BooleanServiceEnum(final String nameWithVersion) {
        this.nameWithVersion = nameWithVersion;
    }

    @Override
    public String getNameWithVersion() {
        return nameWithVersion;
    }
}
