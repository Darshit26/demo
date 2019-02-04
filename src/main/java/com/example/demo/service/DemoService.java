package com.example.demo.service;

import org.springframework.stereotype.Service;


public interface  DemoService {

    public Boolean getConnectivity(String origin, String destination) throws Exception;
}
