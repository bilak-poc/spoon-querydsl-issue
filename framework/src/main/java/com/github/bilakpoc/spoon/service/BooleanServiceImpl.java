package com.github.bilakpoc.spoon.service;

import org.springframework.stereotype.Service;

import com.github.bilakpoc.spoon.integration.booleanservice.BooleanServiceEnum;
import com.github.bilakpoc.spoon.integration.booleanservice.BooleanServiceIntegration;

@Service
public class BooleanServiceImpl implements BooleanService {

    private static final BooleanServiceIntegration INTEGRATION = new BooleanServiceIntegration();

    @Override
    public Boolean negate(final Boolean value) {
        return INTEGRATION.callExternalService(BooleanServiceEnum.V1, value);
    }
}
