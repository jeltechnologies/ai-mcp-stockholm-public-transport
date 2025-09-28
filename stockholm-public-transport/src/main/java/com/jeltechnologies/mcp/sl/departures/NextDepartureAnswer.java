package com.jeltechnologies.mcp.sl.departures;

import java.util.List;

public record NextDepartureAnswer(String station, List<DepartureRecord> destinations) {
};
