package no.anderska.wta.engines;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import no.anderska.wta.game.Question;

import org.junit.Test;

public class PrimeFactorQuestionGeneratorTest {
    @Test
    public void shouldHavePrimeFactors() throws Exception {
        assertThat(new PrimeFactorQuestionGenerator().getPrimeFactor()).contains(2,3,5,7,11).excludes(1,4,9,12,15);
    }

    @Test
    public void shouldReturnQuestion() throws Exception {
        List<Question> questions = new PrimeFactorQuestionGenerator().generateQuestions("playerone");
        assertThat(questions).hasSize(15);
    }
}
