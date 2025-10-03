package com.jeltechnologies.mcp.sl.journeyplanner;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class JourneyPlannerMCPController {
    
    private final JourneyPlannerDataSource journeyPlannerDataSource;
    
    public JourneyPlannerMCPController(JourneyPlannerDataSource journeyPlannerDataSource) {
        this.journeyPlannerDataSource = journeyPlannerDataSource;
    }
        
    @Tool(description = "Plan a trip using public transport in the Stockholm region, including buses, trains, subways, and boats.")
    public List<Trip> planJourney(
        @ToolParam(description = "From station or bus stop") String from, 
        @ToolParam(description = "To station or bus stop") String to) throws Exception {
        List<Trip> trips = journeyPlannerDataSource.planJourney(from, to);
        return trips;
    }
}
