package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class MaxLetterOccurenceQuestionGeneratorTest {

    private final MaxLetterOccurenceQuestionGenerator generator = new MaxLetterOccurenceQuestionGenerator();

    @Test
    public void shouldCountOccurences() throws Exception {
        assertThat(generator.createAnswer("abbc")).isEqualTo("b");
    }

    @Test
    public void shouldPickFirstLetterWhenSame() throws Exception {
        assertThat(generator.createAnswer("aacccbbbde")).isEqualTo("b");
    }

    @Test
    public void shouldKeepCount() throws Exception {
        assertThat(generator.createAnswer("aaccca")).isEqualTo("a");
    }

}
