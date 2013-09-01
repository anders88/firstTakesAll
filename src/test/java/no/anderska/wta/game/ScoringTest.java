package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.servlet.PlayerHandler;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScoringTest {

    @Test
    public void shouldGivePointsOnCorrectAnswer() throws Exception {
        GameHandler gameHandler = new GameHandler();
        Engine engine = mock(Engine.class);
        Map<String,Engine> engines = new HashMap<>();
        engines.put("one",engine);

        gameHandler.setEngines(engines);
        PlayerHandler playerHandler = mock(PlayerHandler.class);
        gameHandler.setPlayerHandler(playerHandler);
        Map<String, QuestionSet> askedQuestions = new HashMap<>();
        QuestionSet questionSet = mock(QuestionSet.class);
        askedQuestions.put("playerone",questionSet);
        gameHandler.setAskedQuestions(askedQuestions);

        when(questionSet.validateAnswer(anyList())).thenReturn(AnswerStatus.OK);
        when(engine.points()).thenReturn(5);
        when(questionSet.getEngine()).thenReturn(engine);

        gameHandler.answer("playerone", Arrays.asList("Dummy"));

        verify(playerHandler).addPoints("playerone",5);
    }
}
