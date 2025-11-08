package com.eatclub.service;

import com.eatclub.dto.ActiveDealsResponse;
import com.eatclub.dto.RestaurantDeals;
import com.eatclub.util.DealsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


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
}
