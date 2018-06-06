package com.github.bilakpoc.spoon.integration.dateservice;

import java.time.LocalDate;

import com.github.bilakpoc.spoon.integration.AbstractIntegrationCaller;
import com.github.bilakpoc.spoon.integration.ExternalService;

/**
 * @author Lukáš Vasek
 */
public class DateServiceIntegration extends AbstractIntegrationCaller<LocalDate, LocalDate> {

    @Override
    public LocalDate callExternalService(ExternalService service, LocalDate request) {

        return request.plusDays(1);
    }
}
