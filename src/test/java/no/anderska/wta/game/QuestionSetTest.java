package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import org.junit.Test;

import java.util.Arrays;

import static org.fest.assertions.Assertions.*;

public class QuestionSetTest {
    @Test
    public void shouldHandleCorrectAnswer() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")));

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);

    }

    @Test
    public void shouldHandleWrongAnswer() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")));

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "wrong"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.WRONG);

    }
}
