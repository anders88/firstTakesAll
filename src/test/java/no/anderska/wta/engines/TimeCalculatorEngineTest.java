package no.anderska.wta.engines;

import no.anderska.wta.game.Question;
import no.anderska.wta.questions.TimeCalculationGenerator;
import no.anderska.wta.questions.timegen.City;
import no.anderska.wta.questions.timegen.RandomTimeGeneration;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;
import java.util.TimeZone;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeCalculatorEngineTest {
    @Test
    public void shouldHaveCorrectWithinTimezones() {
        DateTimeZone timeZone = DateTimeZone.forID("Europe/Paris");
        TimeCalculationGenerator engine = new TimeCalculationGenerator();
        RandomTimeGeneration randomTimeGeneration = mock(RandomTimeGeneration.class);
        engine.setRandom(randomTimeGeneration);

        when(randomTimeGeneration.pickRandomCity()).thenReturn(new City("Paris", timeZone),new City("Oslo", timeZone));

        when(randomTimeGeneration.pickMinutes()).thenReturn(510,180);


        Question actual = engine.makeRandomTrip();

        assertThat(actual.getQuestion()).isEqualTo("From Paris 2013-09-12 at 08:30 to Oslo. Flighttime 3h 0m");
        assertThat(actual.isCorrect("2013-09-12 at 11:30")).isTrue();
    }

    @Test
    public void shouldGenereateDifferentZones() throws Exception {
        DateTimeZone tcParis = DateTimeZone.forID("Europe/Paris");
        DateTimeZone tcLondon = DateTimeZone.forID("Europe/London");
        TimeCalculationGenerator engine = new TimeCalculationGenerator();
        RandomTimeGeneration randomTimeGeneration = mock(RandomTimeGeneration.class);
        engine.setRandom(randomTimeGeneration);

        when(randomTimeGeneration.pickRandomCity()).thenReturn(new City("Paris", tcParis),new City("London", tcLondon));

        when(randomTimeGeneration.pickMinutes()).thenReturn(510,180);


        Question actual = engine.makeRandomTrip();

        assertThat(actual.getQuestion()).isEqualTo("From Paris 2013-09-12 at 08:30 to London. Flighttime 3h 0m");
        assertThat(actual.isCorrect("2013-09-12 at 10:30")).isTrue();
    }

    @Test
    @Ignore
    public void checkZones() throws Exception {
        Set<String> zoneIds = DateTimeZone.getAvailableIDs();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("ZZ");

        for(String zoneId:zoneIds)
        {
            System.out.println("("+dateTimeFormatter.withZone(DateTimeZone.forID(zoneId)).print(0) +") "
                    +zoneId+", "
                    + TimeZone.getTimeZone(zoneId).getDisplayName());
        }

    }
}
