//package com.jeltechnologies.mcp.sl.departures;
//
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
//import org.springframework.stereotype.Service;
//
//@Service
//public class NextDeparturesMCPController {
//    private final NextDeparturesDataSource nextDeparturesDataSource;
//
//    public NextDeparturesMCPController(NextDeparturesDataSource nextDeparturesDataSource) {
//        this.nextDeparturesDataSource = nextDeparturesDataSource;
//    }
//
//    @Tool(description = "Get information on the next buses and trains, including their destinations, at any bus stop or train station within the Stockholm province.")
//    public NextDepartureAnswer getNextDeparturesFrom(@ToolParam(description = "Bus stop or train station") String station) throws Exception {
//        return nextDeparturesDataSource.getNextDeparturesFrom(station);
//    }
//}
