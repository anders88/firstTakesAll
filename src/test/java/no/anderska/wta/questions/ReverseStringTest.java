package no.anderska.wta.questions;

import org.fest.assertions.Assertions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ReverseStringTest {
    @Test
    public void shouldReverseText() throws Exception {
        assertThat(new ReverseStringQuestionGenerator().revrseText("abc")).isEqualTo("cba");
    }
}
