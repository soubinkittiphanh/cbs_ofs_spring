package com.ldb.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class Index {
    @Value("${t24_hours}")
    private String appTitle;
    private Logger logger=LoggerFactory.getLogger(Index.class);
    @GetMapping("/")
    public String greetingSpring(){
        logger.info("Request involked at home path /");
        return "Hello you are the best "+appTitle;
    }
}
