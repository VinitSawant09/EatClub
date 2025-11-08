package com.eatclub.dto;

import lombok.Data;
import java.util.List;

@Data
public class Restaurant {
    private String objectId;
    private String name;
    private String address1;
    private String suburb;
    private String[] cuisines;
    private String imageLink;
    private String open;
    private String close;
    private List<Deal> deals;
}
