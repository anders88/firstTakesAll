package no.anderska.wta.engines;

import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTimeGeneration {

    private List<City> cities;

    public RandomTimeGeneration() {
        cities = new ArrayList<City>();

        cities.add(new City("Oslo", DateTimeZone.forID("Europe/Oslo")));
        cities.add(new City("Paris", DateTimeZone.forID("Europe/Paris")));
        cities.add(new City("London", DateTimeZone.forID("Europe/London")));
        cities.add(new City("Los Angeles", DateTimeZone.forID("America/Los_Angeles")));
        cities.add(new City("Phoenix", DateTimeZone.forID("America/Phoenix")));
        cities.add(new City("Tokyo", DateTimeZone.forID("Asia/Tokyo")));

    }

    public City pickRandomCity() {
        int num = new Random().nextInt(cities.size());
        return cities.get(num);
    }

    public int pickMinutes() {
        Random rnd = new Random();
        return rnd.nextInt(1000)+45;
    }
}
