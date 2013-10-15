package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class HtmlEscapeQuestionGeneratorTest {

    @Test
    public void shouldGenerateHtmlStrings() {
        HtmlEscapeQuestionGenerator generator = new HtmlEscapeQuestionGenerator();
        assertThat(generator.createQuestionString()).contains("<").contains(">");
    }

    @Test
    public void shouldEscapeHtmlStrings() {
        HtmlEscapeQuestionGenerator generator = new HtmlEscapeQuestionGenerator();
        assertThat(generator.createAnswer("<b>Scream & Shout</b>"))
            .isEqualTo("&lt;b&gt;Scream &amp; Shout&lt;/b&gt;");
    }

}
