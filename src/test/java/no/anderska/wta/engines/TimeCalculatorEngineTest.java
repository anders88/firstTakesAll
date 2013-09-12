package no.anderska.wta.engines;

import no.anderska.wta.game.Question;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeCalculatorEngineTest {
    @Test
    public void shouldHaveCorrectWithinTimezones() {
        DateTimeZone timeZone = DateTimeZone.forID("Europe/Paris");
        TimeCalculationEngine engine = new TimeCalculationEngine();
        RandomTimeGeneration randomTimeGeneration = mock(RandomTimeGeneration.class);
        engine.setRandom(randomTimeGeneration);

        when(randomTimeGeneration.pickRandomCity()).thenReturn(new City("Paris", timeZone),new City("Oslo", timeZone));

        when(randomTimeGeneration.pickMinutes()).thenReturn(510,180);


        Question actual = engine.sameDaySameZoneoneQuestion();

        assertThat(actual.getQuestion()).isEqualTo("From Paris 2013-09-12 at 08:30 to Oslo. Flighttime 3h 0m");
        assertThat(actual.isCorrect("2013-09-12 at 11:30")).isTrue();
    }

}
