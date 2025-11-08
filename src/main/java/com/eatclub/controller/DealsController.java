package com.eatclub.controller;

import com.eatclub.dto.ActiveDealsResponse;
import com.eatclub.dto.Peak;
import com.eatclub.service.DealsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deals")
@Slf4j
@AllArgsConstructor
public class DealsController {

    private final DealsService dealsService;

    @GetMapping("/active")
    public ResponseEntity<ActiveDealsResponse> getActiveDeals(@RequestParam String timeOfDay){
        log.info("Inside DealsController: getActiveDeals with timeOfDay {}", timeOfDay);
        return ResponseEntity.ok(dealsService.getActiveDeals(timeOfDay));
    }

    @GetMapping("/peak")
    public ResponseEntity<Peak> getPeak(){
        log.info("Inside DealsController: getPeak");
        return ResponseEntity.ok(dealsService.getPeak());
    }
}
