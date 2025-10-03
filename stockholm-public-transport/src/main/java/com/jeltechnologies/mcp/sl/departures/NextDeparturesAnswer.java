package com.jeltechnologies.mcp.sl.departures;

import java.util.List;

public record NextDeparturesAnswer(String from, List<DepartureRecord> departures) {
};
