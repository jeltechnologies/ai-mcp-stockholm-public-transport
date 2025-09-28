package com.jeltechnologies.mcp.sl.journeyplanner;

import java.util.List;

public record Trip (String from, String start, String to, String end, String duration, int transfers, List<TripLeg> legs) {
}


