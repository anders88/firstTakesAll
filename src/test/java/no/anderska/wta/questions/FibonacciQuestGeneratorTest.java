package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class FibonacciQuestGeneratorTest {

    private final FibonacciQuestionGenerator generator = new FibonacciQuestionGenerator();

    @Test
    public void zeroIsZero() throws Exception {
        assertThat(generator.fibonacci(0)).isEqualTo(0L);

    }

    @Test
    public void oneIsOne() throws Exception {
        assertThat(generator.fibonacci(1)).isEqualTo(1L);
    }

    @Test
    public void twoIsOne() throws Exception {
        assertThat(generator.fibonacci(2)).isEqualTo(1L);
    }

    @Test
    public void threeIsTwo() throws Exception {
        assertThat(generator.fibonacci(3)).isEqualTo(2L);
    }

    @Test
    public void fourIsThree() throws Exception {
        assertThat(generator.fibonacci(4)).isEqualTo(3L);
    }

    @Test
    public void someHigherNumbersIsCorrect() throws Exception {
        assertThat(generator.fibonacci(13)).isEqualTo(233L);
        assertThat(generator.fibonacci(20)).isEqualTo(6765L);
        assertThat(generator.fibonacci(50)).isEqualTo(12586269025L);

    }
}
