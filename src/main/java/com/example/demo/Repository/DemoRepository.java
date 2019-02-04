package com.example.demo.Repository;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

@Repository
public class DemoRepository {

    private static final Logger logger = LoggerFactory.getLogger(DemoRepository.class);
    private static Set<List<String>> listOfConnectedCities = null;

    public Boolean getConnectivity(String origin, String destination) throws Exception {

        try {
            if (null == listOfConnectedCities) {
                listOfConnectedCities = new HashSet<>();
                File f = new File("src/main/resources/city.txt");

                List<String> lines = FileUtils.readLines(f, "UTF-8");

                for (String line : lines) {
                    if (!StringUtils.isEmpty(line)) {
                        List<String> listOfCities = new ArrayList<>();

                        Arrays.asList(line.split(",")).forEach(s -> listOfCities.add(s.trim()));
                        listOfConnectedCities.add(listOfCities);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(listOfConnectedCities)) {
                for (List<String> citiesList : listOfConnectedCities) {
                    if (!CollectionUtils.isEmpty(citiesList)) {
                        if (citiesList.contains(origin) && citiesList.contains(destination)) {
                            return Boolean.TRUE;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

}

