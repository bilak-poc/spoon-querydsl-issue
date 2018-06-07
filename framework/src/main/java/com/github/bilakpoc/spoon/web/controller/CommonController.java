package com.github.bilakpoc.spoon.web.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.bilakpoc.spoon.service.BooleanService;
import com.github.bilakpoc.spoon.service.DateService;

/**
 * @author Lukáš Vasek
 */
@RestController
public class CommonController {

    private final BooleanService booleanService;
    private final DateService dateService;

    public CommonController(final BooleanService booleanService, final DateService dateService) {
        this.booleanService = booleanService;
        this.dateService = dateService;
    }

    @GetMapping("/negations")
    public Boolean negate(@RequestParam Boolean value) {
        return booleanService.negate(value);
    }

    @PostMapping("/dates")
    public LocalDate addDay(@RequestParam LocalDate date) {
        return dateService.addDay(date);
    }
}
