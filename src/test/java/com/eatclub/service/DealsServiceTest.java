package com.eatclub.service;

import com.eatclub.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class DealsServiceTest {

    @InjectMocks
    private DealsService dealsService; // The class containing getActiveDeals()

    @ParameterizedTest
    @CsvSource({
            "3:00pm,3,DEA567C5-0000-3C03-FF00-E3B24909BE00,DEA567C5-F64C-3C03-FF00-E3B24909BE00,Masala Kitchen",
            "6:00pm,4,,,",
            "9:00pm,4,,,",
            "1:00am,0,,,",
            "2:00pm,1,,,"
    })
    void shouldReturnActiveDealsAtGivenTime(String timeOfDay, int expectedCount,
                                            String expectedDealId, String expectedRestaurantId, String expectedName) {
        ActiveDealsResponse response = dealsService.getActiveDeals(timeOfDay);

        assertNotNull(response);
        assertEquals(expectedCount, response.getDeals().size());

        if (expectedDealId != null) {
            RestaurantDeals result = response.getDeals().get(0);
            assertEquals(expectedDealId, result.getDealObjectId());
            assertEquals(expectedRestaurantId, result.getRestaurantObjectId());
            assertEquals(expectedName, result.getRestaurantName());
        }
    }

    @Test
    void shouldReturnPeakTime(){
        Peak peak = dealsService.getPeak();
        assertNotNull(peak);
        assertEquals("14:00", peak.getPeakTimeStart());
        assertEquals("21:00", peak.getPeakTimeEnd());
    }

}
