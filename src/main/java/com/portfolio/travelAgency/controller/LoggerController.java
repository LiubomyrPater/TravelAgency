package com.portfolio.travelAgency.controller;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class LoggerController {

    @RequestMapping("/logs")
    public void index(){
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }


}
