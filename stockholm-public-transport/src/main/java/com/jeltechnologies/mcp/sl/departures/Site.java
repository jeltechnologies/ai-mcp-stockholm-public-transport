package com.jeltechnologies.mcp.sl.departures;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Site(String id, String gid, String name, String abbreviation, 
        @JsonProperty(value = "lat") double latitude,  @JsonProperty(value = "lon") double longitide, 
        @JsonProperty(value = "stop_areas") List<Integer> stopAreas) {
}
