package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.mock;

public class QuestionSetTest {
    private QuestionGenerator generator = mock(QuestionGenerator.class);
    @Test
    public void shouldHandleCorrectAnswer() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")), generator, "dummyCategory");

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);
    }

    @Test
    public void shouldHandleWrongAnswer() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")), generator, "dummyCategory");

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "wrong"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.WRONG);
    }

    @Test
    public void shouldHandleWrongNumberOfAnswers() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")), generator, "dummyCategory");

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "facttwo","factthree"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.WRONG);
    }

    @Test
    public void shouldHandleLateAnswer() throws Exception {
        QuestionSet questionSet = new QuestionSet(Arrays.asList(new Question("one","factone"),new Question("two","facttwo")), generator, "dummyCategory");

        DateTimeUtils.setCurrentMillisFixed(new DateTime().plusSeconds(10).getMillis());

        AnswerStatus answerStatus = questionSet.validateAnswer(Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.LATE);

    }

    @Before
    public void freeceTime() {
        DateTimeUtils.setCurrentMillisFixed(new DateTime().getMillis());
    }

    @After
    public void resetTime() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}
