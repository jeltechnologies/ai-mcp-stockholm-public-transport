package com.jeltechnologies.mcp.sl.journeyplanner;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.jeltechnologies.mcp.RestClientConfigurations;
import com.jeltechnologies.mcp.StringUtils;
import com.jeltechnologies.mcp.sl.departures.Site;
import com.jeltechnologies.mcp.sl.departures.SiteDataSource;
import com.jeltechnologies.mcp.sl.departures.Sites;
import com.jeltechnologies.mcp.sl.journeyplanner.ResponseFromSL.Journey;
import com.jeltechnologies.mcp.sl.journeyplanner.ResponseFromSL.Leg;
import com.jeltechnologies.mcp.sl.journeyplanner.ResponseFromSL.Stopsequence;

@Service
public class JourneyPlannerDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(JourneyPlannerDataSource.class);
    private final static ZoneId STOCKHOLM = ZoneId.of("Europe/Stockholm");
    private final static int DEFAULT_NR_OF_TRIPS = 3;

    private final RestClient slJourneyPlannerRestClient;
    private final SiteDataSource siteDataSource;

    public JourneyPlannerDataSource(RestClient slJourneyPlannerRestClient, SiteDataSource siteDataSource) {
        this.slJourneyPlannerRestClient = slJourneyPlannerRestClient;
        this.siteDataSource = siteDataSource;
    }

    public List<Trip> planJourney(String from, String to) throws IOException {
        Sites sites = siteDataSource.getSites();

        Site siteFrom = sites.getSiteByName(from);
        Site siteTo = sites.getSiteByName(to);

        if (siteFrom == null) {
            throw new IllegalArgumentException("From station not found");
        }
        if (siteTo == null) {
            throw new IllegalArgumentException("To station not found");
        }

        StringBuilder u = new StringBuilder("/trips");
        u.append("?type_origin=any&type_destination=any");
        u.append("&name_origin=").append(siteFrom.gid());
        u.append("&name_destination=").append(siteTo.gid());
        u.append("&calc_number_of_trips=").append(DEFAULT_NR_OF_TRIPS);
        u.append("&calc_one_direction=true");
        u.append("&gen_c=").append("false");
        String uri = u.toString();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GET " + RestClientConfigurations.SL_JOURNEY_PLANNER_URL + uri);
        }
        ResponseFromSL responseFromSL = slJourneyPlannerRestClient.get()
                .uri(uri)
                .retrieve()
                .body(ResponseFromSL.class);

        List<Journey> journies = responseFromSL.journeys();
        List<Trip> result = new ArrayList<Trip>();
        if (journies != null) {
            for (Journey journey : journies) {
                List<Leg> legs = journey.legs();
                List<TripLeg> tripLegs = new ArrayList<TripLeg>();
                if (legs != null) {
                    for (Leg leg : legs) {
                        String fromName = leg.origin().parent().disassembledName();
                        String toName = leg.destination().parent().disassembledName();
                        fromName = StringUtils.firstCharToUpperCase(fromName);
                        toName =  StringUtils.firstCharToUpperCase(toName);
                        ZonedDateTime receivedFromTime = ZonedDateTime.parse(leg.origin().departureTimeEstimated());
                        ZonedDateTime receivedToTime = ZonedDateTime.parse(leg.destination().arrivalTimeEstimated());
                        ZonedDateTime localTimeFrom = receivedFromTime.withZoneSameInstant(STOCKHOLM);
                        ZonedDateTime localTimeTo = receivedToTime.withZoneSameInstant(STOCKHOLM);
                        String fromTime = StringUtils.localTimeRoundedToMinutes(localTimeFrom);
                        String toTime = StringUtils.localTimeRoundedToMinutes(localTimeTo);
                        int durationInSeconds = (int) Duration.between(receivedFromTime, receivedToTime).toSeconds();
                        String duration = StringUtils.durationDescriptionRoundedToMinutes(durationInSeconds);

                        String transportation = leg.transportation().number();
                        if (transportation != null) {
                            if (!transportation.equalsIgnoreCase("footpath")) {
                                List<String> stops = new ArrayList<String>();
                                List<Stopsequence> stopsequences = leg.stopSequence();
                                if (stopsequences != null) {
                                    for (Stopsequence stopSequence : stopsequences) {
                                        String stop = stopSequence.disassembledName();
                                        stops.add(stop);
                                    }
                                }
                                String destination = leg.transportation().destination().name();
                                String line = StringUtils.firstCharToUpperCase(transportation);
                                TripLeg tripLeg = new TripLeg(line, destination, fromName, fromTime, toName, toTime, duration, stops.size());
                                tripLegs.add(tripLeg);
                            }
                        }
                    }
                    String departureTime;
                    String arrivalTime;
                    if (!tripLegs.isEmpty()) {
                        departureTime = tripLegs.getFirst().start();
                        arrivalTime = tripLegs.getLast().end();
                    } else {
                        arrivalTime = "";
                        departureTime = "";
                    }
                    String duration = StringUtils.durationDescriptionRoundedToMinutes(journey.tripRtDuration());
                    int transfers = tripLegs.size() - 1;
                    Trip trip = new Trip(siteFrom.name(), departureTime, siteTo.name(), arrivalTime, duration, transfers, tripLegs);
                    result.add(trip);
                }
            }

        }
        if (LOGGER.isDebugEnabled()) {
            log(result);
        }
        return result;
    }

    private void log(List<Trip> trips) {
        int nr = 1;
        LOGGER.debug("Planned journey");
        for (Trip trip : trips) {
            LOGGER.debug("Alternative " + nr);
            LOGGER.debug("From " + trip.from() + " to " + trip.to() + ", departure " + trip.start() + " , arrival " + trip.end() + ", duration: " + trip.duration()
                    + ", transfers: " + trip.transfers());
            for (TripLeg leg : trip.legs()) {
                LOGGER.debug("    " + leg);
            }
            nr++;
        }
    }

}
