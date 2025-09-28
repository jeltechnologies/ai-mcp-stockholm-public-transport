package com.jeltechnologies.mcp.sl.departures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Line(
        int id, 
        String designation, 
        int transport_authority_id, 
        @JsonProperty(value = "transport_mode") String transportMode, 
        @JsonProperty(value = "group_of_lines") String groupOfLines) {
}
