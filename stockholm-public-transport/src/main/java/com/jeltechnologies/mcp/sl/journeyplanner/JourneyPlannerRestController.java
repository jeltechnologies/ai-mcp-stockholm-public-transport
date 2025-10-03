package com.jeltechnologies.mcp.sl.journeyplanner;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JourneyPlannerRestController {
    
    private final JourneyPlannerDataSource journeyPlannerDataSource;
    
    public JourneyPlannerRestController(JourneyPlannerDataSource journeyPlannerDataSource) {
        this.journeyPlannerDataSource = journeyPlannerDataSource;
    }
    
    @GetMapping("/journey")
    public List<Trip> planJourney(@RequestParam String from, @RequestParam String to) throws Exception {
        List<Trip> trips = journeyPlannerDataSource.planJourney(from, to);
        return trips;
    }

}
