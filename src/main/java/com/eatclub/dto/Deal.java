package com.eatclub.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Deal {
    private String objectId;
    private String discount;
    private String dineIn;
    private String lightning;
    @JsonAlias({"open", "start"})
    private String open;
    @JsonAlias({"close", "end"})
    private String close;
    private String qtyleft;
}
