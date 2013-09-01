package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.servlet.PlayerHandler;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScoringTest {

    private final GameHandler gameHandler = new GameHandler();

    private final PlayerHandler playerHandler = mock(PlayerHandler.class);

    private final QuestionSet questionSet = mock(QuestionSet.class);

    @Test
    public void shouldGivePointsOnCorrectAnswer() throws Exception {
        when(questionSet.validateAnswer(anyList())).thenReturn(AnswerStatus.OK);

        gameHandler.answer("playerone", Arrays.asList("Dummy"));

        verify(playerHandler).addPoints("playerone", 5);
    }

    @Test
    public void shouldOnlyGivePointsOnce() throws Exception {
        when(questionSet.validateAnswer(anyList())).thenReturn(AnswerStatus.OK);

        gameHandler.answer("playerone", Arrays.asList("Dummy"));
        AnswerStatus answer = gameHandler.answer("playerone", Arrays.asList("Dummy"));

        verify(playerHandler).addPoints("playerone", 5);

        Assertions.assertThat(answer).isEqualTo(AnswerStatus.OK);

    }

    @Test
    public void shouldGiveNoPointsOnWrongAnswer() throws Exception {
        when(questionSet.validateAnswer(anyList())).thenReturn(AnswerStatus.WRONG);

        gameHandler.answer("playerone", Arrays.asList("Dummy"));

        verify(playerHandler,never()).addPoints(anyString(),anyInt());
    }

    @Before
    public void setup() {
        Map<String,Engine> engines = new HashMap<>();
        Engine engine = mock(Engine.class);
        Map<String,QuestionSet> askedQuestions = new HashMap<>();

        engines.put("someCategory", engine);
        gameHandler.setEngines(engines);
        gameHandler.setPlayerHandler(playerHandler);
        askedQuestions.put("playerone", questionSet);
        gameHandler.setAskedQuestions(askedQuestions);

        when(engine.points()).thenReturn(5);
        when(questionSet.getEngine()).thenReturn(engine);
        when(questionSet.getCategoryName()).thenReturn("someCategory");
    }
}
