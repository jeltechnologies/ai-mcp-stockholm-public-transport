package com.jeltechnologies.mcp.sl.departures;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SiteDataSource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteDataSource.class);

    private final RestClient slTransportRestClient;
    
    private final SiteRepository repository;
    
    public SiteDataSource(SiteRepository repository, RestClient slTransportRestClient) {
        this.repository = repository;
        this.slTransportRestClient = slTransportRestClient;
    }

    public Sites getSites() throws IOException {
        List<Site> sites = repository.load();
        if (sites == null) {
            sites = slTransportRestClient.get()
                .uri("/sites?expand=true")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Site>>() {
                });
            repository.save(sites);
        }
        return new Sites(sites);
    }
    
    public Departures getDepartures(Site site) throws IOException {
        String uri = "/sites/" + site.id() + "/departures";
        LOGGER.info("GET " + uri);
        Departures departures = slTransportRestClient.get()
                .uri(uri)
                .retrieve()
                .body(Departures.class);
        return departures;
    }

}
