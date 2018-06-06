package com.github.bilakpoc.spoon.integration.dateservice;

import com.github.bilakpoc.spoon.integration.ExternalService;

/**
 * @author Lukáš Vasek
 */
public enum DateServiceEnum implements ExternalService {

    V1("dateService_V1");

    private final String nameWithVersion;

    DateServiceEnum(final String nameWithVersion) {
        this.nameWithVersion = nameWithVersion;
    }

    @Override
    public String getNameWithVersion() {
        return nameWithVersion;
    }
}
