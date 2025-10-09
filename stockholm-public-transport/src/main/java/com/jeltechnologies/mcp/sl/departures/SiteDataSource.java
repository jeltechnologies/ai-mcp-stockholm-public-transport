package com.jeltechnologies.mcp.sl.departures;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class SiteDataSource {
    
    private final SiteRepository repository;
    
    public SiteDataSource(SiteRepository repository) {
        this.repository = repository;
    }

    public Sites getSites() throws IOException {
        return repository.getSites();
    }
}
