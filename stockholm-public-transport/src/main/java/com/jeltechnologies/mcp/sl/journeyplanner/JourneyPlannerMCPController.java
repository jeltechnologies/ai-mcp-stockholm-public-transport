package com.jeltechnologies.mcp.sl.journeyplanner;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;

public class JourneyPlannerMCPController {
    
    private final JourneyPlannerDataSource journeyPlannerDataSource;
    
    public JourneyPlannerMCPController(JourneyPlannerDataSource journeyPlannerDataSource) {
        this.journeyPlannerDataSource = journeyPlannerDataSource;
    }
        
    @Tool(description = "Plan a journey using public transportation in Stockholm province. This includes bus, train, subway and boats")
    public List<Trip> planJourney(String from, String to) throws Exception {
        List<Trip> trips = journeyPlannerDataSource.planJourney(from, to);
        return trips;
    }
}
