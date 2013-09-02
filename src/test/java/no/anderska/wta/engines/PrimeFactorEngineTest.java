package no.anderska.wta.engines;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class PrimeFactorEngineTest {
    @Test
    public void shouldHavePrimeFactors() throws Exception {
        Assertions.assertThat(new PrimeFactorEngine(100,1).getPrimeFactor()).contains(1,2,3,5,7,11).excludes(4,9,12,15);

    }
}
