package no.anderska.wta.game;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.QuestionList;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void shouldGiveErrorOnWrongCategory() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);

        QuestionList questions = gameHandler.questions("playerone", "two");

        verify(engine,never()).generateQuestions(anyString());
        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown category 'two'");
    }

    @Test
    public void shouldHandleCorrectAnswer() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));

        gameHandler.questions("playerone", "one");

        AnswerStatus answerStatus = gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));

        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);

    }

    @Test
    public void shouldGiveCorrectStatus() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));
        when(engine.points()).thenReturn(4);
        when(engine.description()).thenReturn("Category description");
        when(playerHandler.playerName("playerone")).thenReturn("Player One");

        gameHandler.questions("playerone", "one");


        gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));

        List<CategoryDTO> categoryDTOs = gameHandler.catergoryStatus();

        assertThat(categoryDTOs).hasSize(1);

        CategoryDTO categoryDTO = categoryDTOs.get(0);

        assertThat(categoryDTO.getId()).isEqualTo("one");
        assertThat(categoryDTO.getName()).isEqualTo("Category description");
        assertThat(categoryDTO.getAnsweredBy()).isEqualTo("Player One");



    }

    @Test
    public void shouldHandleNoQuestion() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);

        AnswerStatus answerStatus = gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));

        assertThat(answerStatus).isEqualTo(AnswerStatus.ERROR);
    }

    @Test
    public void shouldRegisterCorrectAnswers() throws Exception {
        when(playerHandler.playerPlaying(anyString())).thenReturn(true);
        when(playerHandler.playerName("playerone")).thenReturn("Player One");
        when(playerHandler.playerName("playertwo")).thenReturn("Player Two");

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));

        gameHandler.questions("playerone", "one");
        gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));

        gameHandler.questions("playertwo", "one");
        gameHandler.answer("playertwo", Arrays.asList("factone", "facttwo"));

        List<CategoriesAnsweredDTO> answered = gameHandler.categoriesAnswered();

        assertThat(answered).hasSize(2);

        CategoriesAnsweredDTO plone;
        CategoriesAnsweredDTO pltwo;

        assertThat("Player One".equals(answered.get(0).getPlayerName()) || "Player One".equals(answered.get(1).getPlayerName())).isTrue();
        assertThat("Player Two".equals(answered.get(0).getPlayerName()) || "Player Two".equals(answered.get(1).getPlayerName())).isTrue();

        List<String> catOne = answered.get(0).getCategories();
        List<String> catTwo = answered.get(1).getCategories();
        assertThat(catOne).hasSize(1);
        assertThat(catOne.get(0)).isEqualTo("one");
        assertThat(catTwo).hasSize(1);
        assertThat(catTwo.get(0)).isEqualTo("one");
    }

    @Before
    public void setup() {
        gameHandler.setPlayerHandler(playerHandler);
        Map<String, Engine> engines = new HashMap<>();
        engines.put("one", engine);
        gameHandler.setEngines(engines);
    }


}
