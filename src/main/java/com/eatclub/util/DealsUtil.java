package com.eatclub.util;

import com.eatclub.dto.Deal;
import com.eatclub.dto.Restaurants;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

        String normalizedTime = normalizeTime(timeOfDay);
        String normalizedOpen = normalizeTime(open);
        String normalizedClose = normalizeTime(close);

        LocalTime time = LocalTime.parse(normalizedTime, formatter);
        LocalTime start = LocalTime.parse(normalizedOpen, formatter);
        LocalTime end = LocalTime.parse(normalizedClose, formatter);

        if (end.isBefore(start)) {
            return !time.isBefore(start) || !time.isAfter(end);
        } else {
            return !time.isBefore(start) && !time.isAfter(end);
        }
    }

    public List<Deal> fetchAllDeals(){
        return fetchRestaurants().getRestaurants()
                .stream()
                .flatMap(restaurant ->
                        restaurant.getDeals().stream()
                            .filter(deal -> deal.getOpen()!=null && deal.getClose()!=null))
                        .toList();
    }

    public String normalizeTime(String timeOfDay) {
        if (timeOfDay == null || timeOfDay.isBlank()) {
            throw new IllegalArgumentException("Time string cannot be null or blank");
        }
        return timeOfDay.trim()
                .toUpperCase()
                .replaceAll("(?<=\\d)(AM|PM)", " $1");
    }

}
