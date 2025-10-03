package com.jeltechnologies.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jeltechnologies.mcp.sl.journeyplanner.JourneyPlannerDataSource;
import com.jeltechnologies.mcp.sl.journeyplanner.JourneyPlannerMCPController;

@SpringBootApplication
public class TransportTimesApplication {

//    @Autowired
//    private NextDeparturesDataSource nextDeparturesDataSource;

    @Autowired
    private JourneyPlannerDataSource journeyPlannerDataSource;

//    @Bean
//    ToolCallbackProvider nextDepartureService() {
//        return MethodToolCallbackProvider.builder().toolObjects(new NextDeparturesMCPController(nextDeparturesDataSource)).build();
//    }

    @Bean
    ToolCallbackProvider journeyPlannerService() {
        return MethodToolCallbackProvider.builder().toolObjects(new JourneyPlannerMCPController(journeyPlannerDataSource)).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(TransportTimesApplication.class, args);
    }

}
