package com.jeltechnologies.mcp.sl.departures;

import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class NextDeparturesMCPController {
    private final NextDepartureDataSource nextDepartureDataSource;

    public NextDeparturesMCPController(NextDepartureDataSource nextDepartureDataSource) {
        this.nextDepartureDataSource = nextDepartureDataSource;
    }

    //@Tool(description = "Find the next departures for bus, train and tram from a station in the province of Stockholm.")
    public NextDepartureAnswer getNextDeparturesFrom(@ToolParam(description = "Station name or busstop") String station) throws Exception {
        return nextDepartureDataSource.getNextDeparturesFrom(station);
    }
}
