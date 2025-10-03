package com.jeltechnologies.mcp.sl.departures;

import java.io.IOException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SiteDataSource {
    
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
}
