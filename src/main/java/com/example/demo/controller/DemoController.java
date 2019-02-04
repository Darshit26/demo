package com.example.demo.controller;

import com.example.demo.service.DemoService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/connected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConnectivity(
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "destination", required = false) String destination) throws Exception {
        Boolean isConnected = Boolean.FALSE;

        try {
            isConnected = demoService.getConnectivity(origin, destination);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<String>("{\"connected\":\"no\"}", HttpStatus.OK);
        }
        if (isConnected) {
            return new ResponseEntity<String>("{\"connected\":\"yes\"}", HttpStatus.OK);
        }
        return new ResponseEntity<String>("{\"connected\":\"no\"}", HttpStatus.OK);

    }

}
