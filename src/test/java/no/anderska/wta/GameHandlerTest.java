package no.anderska.wta;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.Question;
import no.anderska.wta.servlet.PlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.fest.assertions.Assertions.*;

public class GameHandlerTest {

    private final GameHandler gameHandler = new GameHandler();
    private final PlayerHandler playerHandler = mock(PlayerHandler.class);
    private final Engine engine = mock(Engine.class);

    @Test
    public void shouldGiveQuestions() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));

        QuestionList questions = gameHandler.questions("playerone", "one");

        verify(playerHandler).playerPlaying("playerone");
        verify(engine).generateQuestions("playerone");

        assertThat(questions.isOk()).isTrue();
    }

    @Test
    public void shouldGiveErrorOnWrongPlayer() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(false);

        QuestionList questions = gameHandler.questions("playerone", "one");

        verify(engine,never()).generateQuestions(anyString());
        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown player 'playerone'");

    }

    @Before
    public void setup() {
        gameHandler.setPlayerHandler(playerHandler);
        Map<String, Engine> engines = new HashMap<>();
        engines.put("one", engine);
        gameHandler.setEngines(engines);
    }


}
