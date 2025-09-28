package com.jeltechnologies.mcp.sl.departures;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.jeltechnologies.mcp.StringUtils;

@Service
public class NextDepartureDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(NextDepartureDataSource.class);
    
    private final SiteDataSource siteDataSource;

    public NextDepartureDataSource(SiteDataSource siteDataSource) {
        this.siteDataSource = siteDataSource;
    }

    public NextDepartureAnswer getNextDeparturesFrom(@ToolParam(description = "Station name or bus stop") String station) throws Exception {
        LOGGER.debug("getNextDeparturesFromStation '" + station + "'");
        Site site = siteDataSource.getSites().getSiteByName(station);
        LOGGER.debug("Found site: " + site);
        List<DepartureRecord> records = new ArrayList<DepartureRecord>();
        String stationName;
        if (site != null) {
            stationName = site.name();
            Departures departures = siteDataSource.getDepartures(site);
            for (Departure departure : departures) {
                String lineName = null;
                Line line = departure.line();
                if (line != null) {
                    lineName = line.groupOfLines();
                    if (lineName == null) {
                        lineName = line.designation();
                    } else {
                        if (!lineName.startsWith("Tunnelbana")) {
                            lineName = lineName + " " + line.designation();
                        }
                    }
                }
                String expectedTimeISO = StringUtils.toString(departure.expected());
                records.add(new DepartureRecord(expectedTimeISO, departure.destination(), lineName));
            }
        } else {
            stationName = "A station or bus stop, with the name " + station + ", could no be found";
        }
        
        NextDepartureAnswer answer= new NextDepartureAnswer(stationName, records);
        LOGGER.debug("Result: " + answer.toString());
        return answer;
    }
    
}
