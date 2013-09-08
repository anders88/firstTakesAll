package no.anderska.wta.engines;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import no.anderska.wta.game.Question;

import org.junit.Test;

public class PrimeFactorEngineTest {
    @Test
    public void shouldHavePrimeFactors() throws Exception {
        assertThat(new PrimeFactorEngine().getPrimeFactor()).contains(2,3,5,7,11).excludes(1,4,9,12,15);
    }

    @Test
    public void shouldReturnQuestion() throws Exception {
        List<Question> questions = new PrimeFactorEngine().generateQuestions("playerone");
        assertThat(questions).hasSize(15);
    }
}
