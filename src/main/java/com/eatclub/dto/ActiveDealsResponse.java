package com.eatclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ActiveDealsResponse {
    private List<RestaurantDeals> deals;
}
