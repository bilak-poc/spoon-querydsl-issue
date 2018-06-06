package com.github.bilakpoc.spoon.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.github.bilakpoc.spoon.integration.dateservice.DateServiceEnum;
import com.github.bilakpoc.spoon.integration.dateservice.DateServiceIntegration;

@Service
public class DateServiceImpl implements DateService {

    private static final DateServiceIntegration INTEGRATION = new DateServiceIntegration();

    @Override
    public LocalDate addDay(final LocalDate date) {
        return INTEGRATION.callExternalService(DateServiceEnum.V1, date);
    }
}
