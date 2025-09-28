package com.jeltechnologies.mcp.sl.departures;

import java.util.Iterator;
import java.util.List;

public record Departures(List<Departure> departures) implements Iterable<Departure>{

    @Override
    public Iterator<Departure> iterator() {
        return departures.iterator();
    }
    
}
