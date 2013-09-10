package no.anderska.wta.game;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.QuestionList;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Before;
import org.junit.Test;

public class GameHandlerTest {

    private final GameHandler gameHandler = new GameHandler();
    private final PlayerHandler playerHandler = gameHandler.getPlayerHandler();
    private final QuestionGenerator engine = mock(QuestionGenerator.class);

    @Test
    public void shouldGiveQuestions() throws Exception {
        String playerid = playerHandler.createPlayer("Player name");

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));

        QuestionList questions = gameHandler.questions(playerid, "one");
        verify(engine).generateQuestions(playerid);
        assertThat(questions.isOk()).isTrue();
    }

    @Test
    public void shouldGiveErrorOnWrongPlayer() throws Exception {
        QuestionList questions = gameHandler.questions("playerone", "one");

        verify(engine,never()).generateQuestions(anyString());
        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown player 'playerone'");
    }

    @Test
    public void shouldGiveErrorOnWrongCategory() throws Exception {
        String playerid = playerHandler.createPlayer("Player");
        QuestionList questions = gameHandler.questions(playerid, "two");

        verify(engine,never()).generateQuestions(anyString());
        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown category 'two'");
    }

    @Test
    public void shouldHandleCorrectAnswer() throws Exception {
        String playerid = playerHandler.createPlayer("Player");

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));

        gameHandler.questions(playerid, "one");

        AnswerStatus answerStatus = gameHandler.answer(playerid, Arrays.asList("factone", "facttwo"));

        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);

    }

    @Test
    public void shouldGiveCorrectStatus() throws Exception {
        String playerid = playerHandler.createPlayer("Player One");

        when(engine.generateQuestions(anyString())).thenReturn(Arrays.asList(new Question("one", "factone"), new Question("two", "facttwo")));
        when(engine.points()).thenReturn(4);
        when(engine.description()).thenReturn("Category description");

        gameHandler.questions(playerid, "one");


        gameHandler.answer(playerid, Arrays.asList("factone", "facttwo"));

        List<CategoryDTO> categoryDTOs = gameHandler.categoryStatus();
        assertThat(categoryDTOs).hasSize(1);
        CategoryDTO categoryDTO = categoryDTOs.get(0);

        assertThat(categoryDTO.getId()).isEqualTo("one");
        assertThat(categoryDTO.getName()).isEqualTo("Category description");
        assertThat(categoryDTO.getAnsweredBy()).isEqualTo("Player One");
    }

    @Test
    public void shouldHandleNoQuestion() throws Exception {
        AnswerStatus answerStatus = gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.ERROR);
    }

    @Test
    public void shouldRegisterCorrectAnswers() throws Exception {
        String playerid1 = playerHandler.createPlayer("Player One");
        String playerid2 = playerHandler.createPlayer("Player Two");

        Question question1 = new Question("one", "factone");
        Question question2 = new Question("two", "facttwo");
        when(engine.generateQuestions(anyString())).thenReturn(asList(question1, question2));

        gameHandler.questions(playerid1, "one");
        gameHandler.answer(playerid1, asList(question1.getCorrectAnswer(), question2.getCorrectAnswer()));

        gameHandler.questions(playerid2, "one");
        gameHandler.answer(playerid2, asList(question1.getCorrectAnswer(), question2.getCorrectAnswer()));

        List<CategoriesAnsweredDTO> answered = gameHandler.categoriesAnswered();

        assertThat(answered).onProperty("playerName")
            .containsOnly("Player One", "Player Two");
        assertThat(answered.get(0).getCategories()).containsExactly("one");
        assertThat(answered.get(1).getCategories()).containsExactly("one");
    }

    @Before
    public void setup() {
        Map<String, QuestionGenerator> engines = new HashMap<>();
        engines.put("one", engine);
        gameHandler.setEngines(engines);
    }


}
