package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ReverseStringTest {
    @Test
    public void shouldReverseText() throws Exception {
        assertThat(new ReverseStringQuestionGenerator().revrseText("abc")).isEqualTo("cba");
    }
}
