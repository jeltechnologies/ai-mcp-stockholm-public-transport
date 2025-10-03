package com.jeltechnologies.mcp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);
    
    public static String firstCharToUpperCase(String in) {
        String result;
        if (in == null || in.isBlank()) {
            result = "";
        }
        result = in.substring(0, 1).toUpperCase() + in.substring(1);
        return result;
    }
    
    public static String timeRoundedToMinutes(LocalDateTime time) {
        return timeRoundedToMinutes(time.getHour(), time.getMinute(), time.getSecond());
    }
    
    public static String timeRoundedToMinutes(ZonedDateTime time) {
        return timeRoundedToMinutes(time.getHour(), time.getMinute(), time.getSecond());
    }
    
    public static String timeRoundedToMinutes(int hour, int minute, int second) {
        StringBuilder b = new StringBuilder();
        int roundedMinute = minute;
        if (second > 29) {
            roundedMinute++;
        }
        if (hour < 10) {
            b.append(" ");
        }
        b.append(hour);
        b.append(":");
        if (roundedMinute < 10) {
            b.append("0");
        }
        b.append(roundedMinute);
        return b.toString();
    }
    

    public static String durationDescriptionRoundedToMinutes(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        int roundedMinutes = minutes;
        if (seconds > 29) {
            roundedMinutes++;
        }
        StringBuilder b = new StringBuilder();
        if (hours > 0) {
            b.append(hours).append(" hour");
            if (hours > 1) {
                b.append("s");
            }
        }
        if (roundedMinutes > 0) {
            if (!b.isEmpty()) {
                b.append(" and ");
            }
            b.append(roundedMinutes).append(" minute");
            if (roundedMinutes > 1) {
                b.append("s");
            }
        }
        String result = b.toString();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(totalSeconds + " seconds => " + result + " => " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
        return b.toString();
    }

}
