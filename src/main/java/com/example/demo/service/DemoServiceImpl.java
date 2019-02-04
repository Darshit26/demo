package com.example.demo.service;

import com.example.demo.Repository.DemoRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    public Boolean getConnectivity(String origin, String destination) throws Exception {
        Boolean isConnected;

        isConnected = demoRepository.getConnectivity(origin, destination);

        return isConnected;
    }
}
