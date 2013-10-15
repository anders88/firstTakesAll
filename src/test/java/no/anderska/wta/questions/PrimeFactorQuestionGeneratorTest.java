package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class PrimeFactorQuestionGeneratorTest {
    @Test
    public void shouldHavePrimeFactors() throws Exception {
        assertThat(new PrimeFactorQuestionGenerator().getPrimeFactor()).contains(2,3,5,7,11).excludes(1,4,9,12,15);
    }

    @Test
    public void shouldReturnQuestion() throws Exception {
        PrimeFactorQuestionGenerator r = new PrimeFactorQuestionGenerator();
        assertThat(r.generateQuestionSet("", "").questions()).hasSize(15);
    }
}
