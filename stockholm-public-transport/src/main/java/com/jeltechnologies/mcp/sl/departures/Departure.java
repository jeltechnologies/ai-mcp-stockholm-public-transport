package com.jeltechnologies.mcp.sl.departures;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Departure(String destination,
        String direction_code,
        String direction,
        String display,
        String via,
        LocalDateTime scheduled,
        LocalDateTime expected,
        Journey journey,
        @JsonProperty(value = "stop_area") StopArea stopArea,
        @JsonProperty(value = "stop_point") StopPoint stopPoint,
        Line line)
{

}
