package no.anderska.wta.game;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.questions.DummyQuestionGenerator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuestionSetTest {
    private final QuestionGenerator generator = new DummyQuestionGenerator();

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
