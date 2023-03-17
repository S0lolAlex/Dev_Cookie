package org.greenSnake.Times;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    @Getter
    @Setter
    private static String time_zone = null;
    public String getTime(String zone){
        ZoneId zoneId;
        if(!isTimeZoneValid(zone)){
            zone = time_zone;
        }
        zone = zone.replace(" ","+");
        zoneId = ZoneId.of(zone);
        Instant now = Instant.now();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(now,
                zoneId);
        return zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + zone;
    }

    public static boolean isTimeZoneValid(String timeZone) {
        if ("".equals(timeZone) || timeZone == null) {
            return false;
        }
        timeZone = timeZone.replace(" ", "+");
        try {
            ZoneId.of(timeZone);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
