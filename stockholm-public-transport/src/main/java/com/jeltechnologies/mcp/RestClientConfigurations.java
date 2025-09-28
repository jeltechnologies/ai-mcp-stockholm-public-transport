package com.jeltechnologies.mcp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfigurations {

    public static final String SL_SITES_AND_DEPARTURES_URL = "https://transport.integration.sl.se/v1";
    
    public static final String SL_JOURNEY_PLANNER_URL = "https://journeyplanner.integration.sl.se/v2";

    @Bean 
    RestClient slTransportRestClient(RestClient.Builder builder) {
        return builder.baseUrl(SL_SITES_AND_DEPARTURES_URL).build();
    }
    
    @Bean 
    RestClient slJourneyPlannerRestClient(RestClient.Builder builder) {
        return builder.baseUrl(SL_JOURNEY_PLANNER_URL).build();
    }
}
