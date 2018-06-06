package com.github.bilakpoc.spoon.service;

import java.time.LocalDate;

/**
 * @author Lukáš Vasek
 */
public interface DateService {

    LocalDate addDay(final LocalDate date);
}
