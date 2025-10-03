package com.jeltechnologies.mcp.sl.departures;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NextDeparturesRestController {
    private final NextDeparturesDataSource nextDeparturesDataSource;

    public NextDeparturesRestController(NextDeparturesDataSource nextDeparturesDataSource) {
        this.nextDeparturesDataSource = nextDeparturesDataSource;
    }

    @GetMapping("/departures")
    public NextDeparturesAnswer getNextDeparturesFrom(@RequestParam String from) throws Exception {
        return nextDeparturesDataSource.getNextDeparturesFrom(from);
    }
}
