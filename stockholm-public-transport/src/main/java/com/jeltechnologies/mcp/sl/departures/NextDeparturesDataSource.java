package com.jeltechnologies.mcp.sl.departures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.jeltechnologies.mcp.RestClientConfigurations;
import com.jeltechnologies.mcp.StringUtils;

@Service
public class NextDeparturesDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(NextDeparturesDataSource.class);
    
    private final SiteDataSource siteDataSource;
    
    private final RestClient slTransportRestClient;

    public NextDeparturesDataSource(SiteDataSource siteDataSource, RestClient slTransportRestClient) {
        this.siteDataSource = siteDataSource;
        this.slTransportRestClient = slTransportRestClient;
    }

    public NextDeparturesAnswer getNextDeparturesFrom(String from) throws Exception {
        LOGGER.debug("getNextDeparturesFromStation '" + from + "'");
        Site site = siteDataSource.getSites().getSiteByName(from);
        LOGGER.debug("Found site: " + site);
        List<DepartureRecord> records = new ArrayList<DepartureRecord>();
        String stationName;
        if (site != null) {
            stationName = site.name();
            
            String uri = "/sites/" + site.id() + "/departures";
            
            LOGGER.debug("GET " + RestClientConfigurations.SL_SITES_AND_DEPARTURES_URL + uri);
            Departures departures = slTransportRestClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(Departures.class);
            
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
                String time = StringUtils.timeRoundedToMinutes(departure.expected());
                String display = departure.display();
                records.add(new DepartureRecord(time, lineName, departure.destination(), display));
            }
        } else {
            stationName = "A station or bus stop, with the name " + from + ", could no be found";
        }
        NextDeparturesAnswer answer= new NextDeparturesAnswer(stationName, records);
        LOGGER.debug("Result: " + answer.toString());
        return answer;
    }
    
    
}
