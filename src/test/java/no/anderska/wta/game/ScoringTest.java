package no.anderska.wta.game;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.questions.DummyQuestionGenerator;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ScoringTest {

    private final GameHandler gameHandler = new GameHandler();
    private final PlayerHandler playerHandler = gameHandler.getPlayerHandler();
    private final String playerId = playerHandler.createPlayer("Some name");
    private final QuestionGenerator generators = new DummyQuestionGenerator();
    private final GameLogger gameLogger = mock(GameLogger.class);
    private ArgumentCaptor<List<String>> answerCaptor;
    private ArgumentCaptor<List<String>> expectedCaptor;
    private ArgumentCaptor<List<String>> questionCaptor;

    @Before
    public void setUp() throws Exception {
        gameHandler.setGameLogger(gameLogger);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Class<List<String>> stringListClass = (Class)List.class;

        answerCaptor = ArgumentCaptor.forClass(stringListClass);
        expectedCaptor = ArgumentCaptor.forClass(stringListClass);
        questionCaptor = ArgumentCaptor.forClass(stringListClass);


    }

    @Test
    public void shouldGivePointsOnCorrectAnswer() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", generators, Arrays.asList(question));

        AnswerStatus status = gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        assertThat(status).isEqualTo(AnswerStatus.OK);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(generators.points());

        verify(gameLogger).answer(eq(playerId), eq("some category"),answerCaptor.capture(), expectedCaptor.capture(),questionCaptor.capture(),eq(AnswerStatus.OK),eq(110));

        assertThat(answerCaptor.getAllValues()).hasSize(1);
        assertThat(expectedCaptor.getAllValues()).hasSize(1);
        assertThat(questionCaptor.getAllValues()).hasSize(1);
        assertThat(answerCaptor.getValue()).containsExactly("b");
        assertThat(expectedCaptor.getValue()).containsExactly("b");
        assertThat(questionCaptor.getValue()).containsExactly("a");

    }

    @Test
    public void shouldOnlyGivePointsOnce() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", generators, Arrays.asList(question));

        gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        AnswerStatus status = gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        assertThat(status).isEqualTo(AnswerStatus.OK);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(generators.points());
    }

    @Test
    public void shouldGiveNoPointsOnWrongAnswer() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", generators, Arrays.asList(question));

        AnswerStatus status = gameHandler.answer(playerId, Arrays.asList("Wrong answer"));
        assertThat(status).isEqualTo(AnswerStatus.WRONG);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(0);

        verify(gameLogger).answer(eq(playerId), eq("some category"),answerCaptor.capture(), expectedCaptor.capture(),questionCaptor.capture(),eq(AnswerStatus.WRONG),eq(0));

        assertThat(answerCaptor.getAllValues()).hasSize(1);
        assertThat(expectedCaptor.getAllValues()).hasSize(1);
        assertThat(answerCaptor.getValue()).containsExactly("Wrong answer");
        assertThat(expectedCaptor.getValue()).containsExactly("b");
    }

    @Test
    public void shouldGiveNoPointsOnUnaskedQuestion() {
        AnswerStatus status = gameHandler.answer(playerId, Arrays.asList("Correct answer"));
        assertThat(status).isEqualTo(AnswerStatus.ERROR);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(0);
    }

}
