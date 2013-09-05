package no.anderska.wta.engines;

import no.anderska.wta.game.Question;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class PrimeFactorEngineTest {
    @Test
    public void shouldHavePrimeFactors() throws Exception {
        assertThat(new PrimeFactorEngine(100, 1, 1).getPrimeFactor()).contains(2,3,5,7,11).excludes(1,4,9,12,15);
    }

    @Test
    public void shouldReturnQuestion() throws Exception {
        List<Question> questions = new PrimeFactorEngine(100, 4, 5).generateQuestions("playerone");
        assertThat(questions).hasSize(5);
    }
}
