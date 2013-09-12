package no.anderska.wta.engines;

import org.joda.time.DateTimeZone;

public class City {
    public String name;
    public DateTimeZone timeZone;

    public City(String name, DateTimeZone timeZone) {
        this.name = name;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public DateTimeZone getTimeZone() {
        return timeZone;
    }
}
