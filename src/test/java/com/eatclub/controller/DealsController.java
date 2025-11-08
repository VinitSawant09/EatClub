package com.eatclub.controller;

import com.eatclub.dto.ActiveDealsResponse;
import com.eatclub.dto.Peak;
import com.eatclub.dto.RestaurantDeals;
import com.eatclub.service.DealsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(DealsController.class)
@Import(DealsControllerTest.MockDealsServiceConfig.class)
class DealsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DealsService dealsService;

    @TestConfiguration
    static class MockDealsServiceConfig {
        @Bean
        public DealsService dealsService() {
            return Mockito.mock(DealsService.class);
        }
    }

    @Test
    void testGetActiveDeals_returnsExpectedResponse() throws Exception {
        String timeOfDay = "03:00";

        ActiveDealsResponse mockResponse = getActiveDealsResponse();
        Mockito.when(dealsService.getActiveDeals(timeOfDay)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/deals/active")
                        .param("timeOfDay", timeOfDay))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deals[0].restaurantName").value("Pizza Palace"))
                .andExpect(jsonPath("$.deals[0].restaurantSuburb").value("Downtown"))
                .andExpect(jsonPath("$.deals.length()").value(3));
    }

    @Test
    void testGetPeak_returnsExpectedPeak() throws Exception {
        Peak mockPeak = new Peak("18:00", "20:00");
        Mockito.when(dealsService.getPeak()).thenReturn(mockPeak);

        mockMvc.perform(get("/api/v1/deals/peak"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.peakTimeStart").value("18:00"))
                .andExpect(jsonPath("$.peakTimeEnd").value("20:00"));
    }

    private static ActiveDealsResponse getActiveDealsResponse() {

        RestaurantDeals deal1 = new RestaurantDeals();
        deal1.setRestaurantObjectId("001");
        deal1.setRestaurantName("Pizza Palace");
        deal1.setRestaurantAddress1("123 Main Street");
        deal1.setRestaurantSuburb("Downtown");
        deal1.setRestaurantOpen("10:00 AM");
        deal1.setRestaurantClose("10:00 PM");
        deal1.setDealObjectId("deal-001");
        deal1.setDiscount("20%");
        deal1.setDineIn("Yes");
        deal1.setLightning("No");
        deal1.setQtyLeft("15");


        RestaurantDeals deal2 = new RestaurantDeals();
        deal2.setRestaurantObjectId("002");
        deal2.setRestaurantName("Sushi Central");
        deal2.setRestaurantAddress1("456 Ocean Avenue");
        deal2.setRestaurantSuburb("Uptown");
        deal2.setRestaurantOpen("11:00 AM");
        deal2.setRestaurantClose("9:00 PM");
        deal2.setDealObjectId("deal-002");
        deal2.setDiscount("10%");
        deal2.setDineIn("No");
        deal2.setLightning("Yes");
        deal2.setQtyLeft("8");

        RestaurantDeals deal3 = new RestaurantDeals();
        deal3.setRestaurantObjectId("003");
        deal3.setRestaurantName("Burger Hub");
        deal3.setRestaurantAddress1("789 Grill Road");
        deal3.setRestaurantSuburb("Midtown");
        deal3.setRestaurantOpen("12:00 PM");
        deal3.setRestaurantClose("11:00 PM");
        deal3.setDealObjectId("deal-003");
        deal3.setDiscount("25%");
        deal3.setDineIn("Yes");
        deal3.setLightning("Yes");
        deal3.setQtyLeft("20");

        List<RestaurantDeals> mockDeals = List.of(deal1, deal2, deal3);
        return new ActiveDealsResponse(mockDeals);
    }

}

