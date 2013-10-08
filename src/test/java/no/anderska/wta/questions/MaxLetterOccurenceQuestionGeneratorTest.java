package no.anderska.wta.questions;

import no.anderska.wta.questions.MaxLetterOccurenceQuestionGenerator;
import org.fest.assertions.Assertions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

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
