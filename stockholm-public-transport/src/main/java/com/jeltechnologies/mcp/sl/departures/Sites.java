package com.jeltechnologies.mcp.sl.departures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jeltechnologies.mcp.StringCompareUtils;

public record Sites(List<Site> sites) implements Iterable<Site> {

    private List<String> getAllNames() {
        List<String> allNames = new ArrayList<String>(sites.size());
        for (Site site : sites) {
            allNames.add(site.name());
        }
        return allNames;
    }

    public Site getSiteByName(String name) {
        Site found = null;
        List<String> closestMatches = StringCompareUtils.findBestMatches(name, getAllNames());
        if (!closestMatches.isEmpty()) {
            String closestName = closestMatches.getFirst();
            for (int i = 0; i < sites.size() && found == null; i++) {
                if (sites.get(i).name().equals(closestName)) {
                    found = sites.get(i);
                }
            }
        }
        return found;
    }

    @Override
    public Iterator<Site> iterator() {
        return sites.iterator();
    }
}
