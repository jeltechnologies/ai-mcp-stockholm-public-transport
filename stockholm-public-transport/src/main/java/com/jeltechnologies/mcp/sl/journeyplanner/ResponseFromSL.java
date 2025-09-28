package com.jeltechnologies.mcp.sl.journeyplanner;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseFromSL(
        List<Object> systemMessages,
        List<Journey> journeys) {
    
    public record Journey(
            int tripDuration,
            int tripRtDuration,
            int rating,
            boolean isAdditional,
            int interchanges,
            List<Leg> legs,
            Daysofservice daysOfService) {
    }
    
    public record Leg(
            List<Object> infos,
            int duration,
            Origin origin,
            Destination destination,
            Transportation transportation,
            List<Stopsequence> stopSequence,
            List<List<Double>> coords,
            List<String> realtimeStatus,
            boolean isRealtimeControlled) {
    }

    public record Properties(
            String stopId) {
    }

    public record Parent(
            boolean isGlobalId,
            String id,
            String name,
            String disassembledName,
            String type,
            Parent parent,
            Properties properties,
            List<Double> coord,
            int niveau) {
    }

    public record Origin(
            boolean isGlobalId,
            String id,
            String name,
            String disassembledName,
            String type,
            List<Double> coord,
            int niveau,
            Parent parent,
            List<Integer> productClasses,
            String departureTimeBaseTimetable,
            String departureTimePlanned,
            String departureTimeEstimated,
            Properties properties) {
    }

    public record Destination(
            boolean isGlobalId,
            String id,
            String name,
            String disassembledName,
            String type,
            List<Double> coord,
            int niveau,
            Parent parent,
            List<Integer> productClasses,
            String arrivalTimeBaseTimetable,
            String arrivalTimePlanned,
            String arrivalTimeEstimated,
            Properties properties) {
    }

    public record Product(
            int id,
            @JsonProperty(value = "class") int clazz,
            String name,
            int iconId) {
    }

    public record Operator(
            String id,
            String name) {
    }

    public record Transportation(
            String id,
            String name,
            String number,
            Product product,
            Operator operator,
            Destination destination,
            Properties properties,
            boolean isSamtrafik,
            String disassembledName) {
    }

    public record Stopsequence(
            boolean isGlobalId,
            String id,
            String name,
            String disassembledName,
            String type,
            List<Double> coord,
            int niveau,
            Parent parent,
            List<Integer> productClasses,
            Properties properties,
            String departureTimePlanned,
            String departureTimeEstimated) {
    }

    public record Daysofservice(
            String rvb) {
    }


}
