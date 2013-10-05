package no.anderska.wta.questions.timegen;

import org.joda.time.DateTimeZone;

public class City {
    private String name;
    private DateTimeZone timeZone;

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
