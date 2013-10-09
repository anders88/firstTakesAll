package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class MaxLetterOccurenceQuestionGeneratorTest {

    private final MaxLetterOccurenceQuestionGenerator generator = new MaxLetterOccurenceQuestionGenerator();

    @Test
    public void shouldCountOccurences() throws Exception {
        assertThat(generator.findMaxOccurence("abbc")).isEqualTo("b");
    }

    @Test
    public void shouldPickFirstLetterWhenSame() throws Exception {
        assertThat(generator.findMaxOccurence("aacccbbbde")).isEqualTo("b");
    }
}
