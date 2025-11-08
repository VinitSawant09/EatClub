package com.eatclub.util;

import com.eatclub.dto.Restaurants;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
public class DealsUtil {

    public Restaurants fetchRestaurants() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = getClass().getClassLoader().getResourceAsStream("data.json");
        return mapper.readValue(input, Restaurants.class);
    }

    public boolean isBetween(String timeOfDay, String open, String close) {
        if(open==null || close==null){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

        String normalizedTime = timeOfDay.trim().toUpperCase().replaceAll("(?<=\\d)(AM|PM)", " $1");
        String normalizedOpen = open.trim().toUpperCase().replaceAll("(?<=\\d)(AM|PM)", " $1");
        String normalizedClose = close.trim().toUpperCase().replaceAll("(?<=\\d)(AM|PM)", " $1");

        LocalTime time = LocalTime.parse(normalizedTime, formatter);
        LocalTime start = LocalTime.parse(normalizedOpen, formatter);
        LocalTime end = LocalTime.parse(normalizedClose, formatter);

        if (end.isBefore(start)) {
            return !time.isBefore(start) || !time.isAfter(end);
        } else {
            return !time.isBefore(start) && !time.isAfter(end);
        }
    }
}
