package com.github.bilakpoc.spoon.integration.booleanservice;

import com.github.bilakpoc.spoon.integration.AbstractIntegrationCaller;
import com.github.bilakpoc.spoon.integration.ExternalService;

/**
 * @author Lukáš Vasek
 */
public class BooleanServiceIntegration extends AbstractIntegrationCaller<Boolean, Boolean> {

    @Override
    public Boolean callExternalService(ExternalService service, Boolean request) {

        return !request;
    }
}
