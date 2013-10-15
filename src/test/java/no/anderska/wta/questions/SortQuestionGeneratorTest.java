package no.anderska.wta.questions;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;

import no.anderska.wta.game.QuestionSet;

import org.junit.Test;

public class SortQuestionGeneratorTest {

    private final SortQuestionGenerator generator = new SortQuestionGenerator();

    @Test
    public void shouldGeneratedUnsortedList() {
        QuestionSet questionSet = generator.generateQuestionSet("", "");
        assertThat(questionSet.questions()).isNotEmpty();
    }

    @Test
    public void shouldAcceptSortedArray() {
        assertThat(generator.generateAnswers(Arrays.asList("c", "b", "10", "9", "a")))
            .containsExactly("10", "9", "a", "b", "c");
    }

    @Test
    public void sortNumericQuestionGenerator() {
        SortNumericQuestionGenerator generator = new SortNumericQuestionGenerator();
        assertThat(generator.generateAnswers(Arrays.asList(10, 9, 12, 11)))
            .containsExactly(9, 10, 11, 12);
    }

    @Test
    public void sortRomanQuestionGenerator() {
        SortRomanQuestionGenerator generator = new SortRomanQuestionGenerator();
        assertThat(generator.generateAnswers(Arrays.asList(10, 9, 12, 11)))
            .containsExactly(9, 10, 11, 12);
    }


}
