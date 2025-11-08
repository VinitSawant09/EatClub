package com.eatclub.service;

import com.eatclub.dto.*;
import com.eatclub.util.DealsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class DealsService {

    public ActiveDealsResponse getActiveDeals(final String timeOfDay){
        log.info("Fetching getActiveDeals for timeOfDay={}", timeOfDay);
        DealsUtil dealsUtil = new DealsUtil();
        List<RestaurantDeals> deals = dealsUtil.fetchRestaurants()
                .getRestaurants()
                .stream()
                .flatMap(restaurant -> restaurant.getDeals().stream()
                        .filter(deal -> dealsUtil.isBetween(timeOfDay, deal.getOpen(), deal.getClose()))
                        .map(deal -> {
                            RestaurantDeals rd = new RestaurantDeals();
                            rd.setRestaurantObjectId(restaurant.getObjectId());
                            rd.setRestaurantName(restaurant.getName());
                            rd.setRestaurantAddress1(restaurant.getAddress1());
                            rd.setRestaurantSuburb(restaurant.getSuburb());
                            rd.setRestaurantOpen(restaurant.getOpen());
                            rd.setRestaurantClose(restaurant.getClose());
                            rd.setDealObjectId(deal.getObjectId());
                            rd.setDiscount(deal.getDiscount());
                            rd.setDineIn(deal.getDineIn());
                            rd.setLightning(deal.getLightning());
                            rd.setQtyLeft(deal.getQtyleft());
                            return rd;
                        }))
                .toList();
        log.info("Number of Deals fetched for timeOfDay={} are {}", timeOfDay, deals.size());
        return new ActiveDealsResponse(deals);
    }

    public Peak getPeak() {
        log.info("Fetching getPeak");
        DealsUtil dealsUtil = new DealsUtil();

        List<TimePoint> timeline = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

        //Peak Time Range (Sweep Line)

        for (Deal deal : dealsUtil.fetchAllDeals()) {
            timeline.add(new TimePoint(LocalTime.parse(dealsUtil.normalizeTime(deal.getOpen()), formatter), +1));
            timeline.add(new TimePoint(LocalTime.parse(dealsUtil.normalizeTime(deal.getClose()), formatter), -1));
        }

        Collections.sort(timeline); //sort timepoints based on delta and time

        int active = 0;
        int maxActive = 0;
        LocalTime peakStart = null;
        LocalTime peakEnd = null;

        for (int i = 0; i < timeline.size(); i++) {
            active += timeline.get(i).getDelta();

            if (active > maxActive) { //only go in if +1 encountered
                maxActive = active;
                peakStart = timeline.get(i).getTime();

                // Find when this peak ends
                // Use a separate variable to track inner sweep
                int tempActive = active;
                for (int j = i + 1; j < timeline.size(); j++) {
                    tempActive += timeline.get(j).getDelta();
                    if (tempActive < maxActive) {
                        peakEnd = timeline.get(j).getTime(); //move to next i
                        break;
                    }else{
                        maxActive = tempActive; //to update max active to newer value.
                    }
                }
                break;
            }
        }
        log.info("Max deals active during peak time {}", maxActive);
        log.info("Peak time start time {} and end time {}", peakStart, peakEnd);
        return new Peak(peakStart != null ? peakStart.toString() : "N/A", peakEnd != null ? peakEnd.toString() : "N/A");
    }
}
