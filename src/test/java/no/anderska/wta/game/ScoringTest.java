package no.anderska.wta.game;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Test;

public class ScoringTest {

    private final GameHandler gameHandler = new GameHandler();
    private final PlayerHandler playerHandler = gameHandler.getPlayerHandler();
    private final String playerId = playerHandler.createPlayer("Some name");
    private final Engine engine = createMockEngine();

    @Test
    public void shouldGivePointsOnCorrectAnswer() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", engine, Arrays.asList(question));

        AnswerStatus status = gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        assertThat(status).isEqualTo(AnswerStatus.OK);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(engine.points());
    }

    @Test
    public void shouldOnlyGivePointsOnce() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", engine, Arrays.asList(question));

        gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        AnswerStatus status = gameHandler.answer(playerId, asList(question.getCorrectAnswer()));
        assertThat(status).isEqualTo(AnswerStatus.OK);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(engine.points());
    }

    @Test
    public void shouldGiveNoPointsOnWrongAnswer() throws Exception {
        Question question = new Question("a", "b");
        gameHandler.putQuestion(playerId, "some category", engine, Arrays.asList(question));

        AnswerStatus status = gameHandler.answer(playerId, Arrays.asList("Wrong answer"));
        assertThat(status).isEqualTo(AnswerStatus.WRONG);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(0);
    }

    @Test
    public void shouldGiveNoPointsOnUnaskedQuestion() {
        AnswerStatus status = gameHandler.answer(playerId, Arrays.asList("Correct answer"));
        assertThat(status).isEqualTo(AnswerStatus.ERROR);
        assertThat(playerHandler.getPoints(playerId))
            .isEqualTo(0);
    }

    private Engine createMockEngine() {
        Engine engine = mock(Engine.class);
        when(engine.points()).thenReturn(5);
        return engine;
    }

}
