package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.Repository.DemoRepository;
import com.example.demo.service.DemoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {
    private static Logger logger;
    private MockMvc mockMvc;

    private String appPath = "/connected?origin=Boston&destination=New York";

    private String appPath1 = "/connected?origin=Philadelphia&destination=New York";

    private String appPath2 = "/connected?origin=city1&destination=city2";
    @InjectMocks
    private DemoController demoController;

    @InjectMocks
    private DemoServiceImpl demoService;

    @InjectMocks
    private DemoRepository demoRepo;

    @Mock
    private DemoRepository demoRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jackson;

    @Before
    public void beforeClass() {
        logger = LoggerFactory.getLogger(DemoControllerTest.class);
    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mock controller and its services
        ReflectionTestUtils.setField(demoController, "demoService", demoService);
        ReflectionTestUtils.setField(demoService, "demoRepository", demoRepo);
        mockMvc = standaloneSetup(demoController).setMessageConverters(jackson).build();
    }

    @Test
    public void getSuccess() throws Exception {
        ResultMatcher resultMatcher = jsonPath("$.connected")
                .value("yes");
        mockMvc.perform(get(appPath)).andExpect(status().isOk());
                //andExpect(resultMatcher);
    }

    @Test
    public void getNo() throws Exception {
        ResultMatcher resultMatcher = jsonPath("connected")
                .value("no");
        mockMvc.perform(get(appPath1)).andExpect(status().isOk());
                //.andExpect(resultMatcher);
    }

    @Test
    public void getNo1() throws Exception {
        ResultMatcher resultMatcher = jsonPath("connected")
                .value("no");
        mockMvc.perform(get(appPath2)).andExpect(status().isOk());
                //.andExpect(resultMatcher);
    }

    @Test
    public void getException() throws Exception {
        when(demoRepository.getConnectivity(anyString(), anyString())).thenThrow(new Exception());
        //ResultMatcher resultMatcher = jsonPath("connected")
              //  .value("no");
        mockMvc.perform(get(appPath2)).andExpect(status().isOk());
                //.andExpect(resultMatcher);
    }
}

