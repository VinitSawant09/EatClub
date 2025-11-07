package com.eatclub.util;

import com.eatclub.dto.Restaurants;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Slf4j
public class DealsUtil {

    public Restaurants fetchRestaurants() {
        log.info("Fetching Restaurants");
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = getClass().getClassLoader().getResourceAsStream("data.json");
        Restaurants restaurants = mapper.readValue(input, Restaurants.class);
        log.info("Restaurants {}", restaurants);
        return restaurants;
    }
}
