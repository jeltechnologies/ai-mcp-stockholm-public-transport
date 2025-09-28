package com.jeltechnologies.mcp.sl.journeyplanner;

public record TripLeg(String line, String destination, String from, String start, String to, String end, String duration, int stops) {
}
