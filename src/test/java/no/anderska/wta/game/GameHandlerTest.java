package no.anderska.wta.game;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.QuestionList;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.LogEntryDetailDTO;
import no.anderska.wta.logging.MemoryGameLogger;
import no.anderska.wta.questions.DummyQuestionGenerator;
import no.anderska.wta.servlet.PlayerHandler;

import org.junit.Before;
import org.junit.Test;

public class GameHandlerTest {

    private final GameHandler gameHandler = new GameHandler();
    private final PlayerHandler playerHandler = gameHandler.getPlayerHandler();
    private final DummyQuestionGenerator generator =
            new DummyQuestionGenerator(Arrays.asList(new Question("q", "a")));
    private final MemoryGameLogger gameLogger = new MemoryGameLogger(playerHandler);

    private final String playerName = "Player";
    private final String playerid = playerHandler.createPlayer(playerName);

    @Test
    public void shouldGiveQuestions() throws Exception {
        generator.addQuestionSet(asList(new Question("one", "factone"), new Question("two", "facttwo")));

        QuestionList questions = gameHandler.questions(playerid, "one");
        assertThat(questions.isOk()).isTrue();
        assertThat(questions.getQuestions()).containsExactly("one", "two");
    }

    @Test
    public void shouldGiveErrorOnWrongPlayer() throws Exception {
        QuestionList questions = gameHandler.questions("playerone", "one");

        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown player 'playerone'");
    }

    @Test
    public void shouldGiveErrorOnWrongCategory() throws Exception {
        QuestionList questions = gameHandler.questions(playerid, "two");

        assertThat(questions.isOk()).isFalse();
        assertThat(questions.getErrormessage()).isEqualTo("Unknown category 'two'");
    }

    @Test
    public void shouldRespondToCorrectAnswer() throws Exception {
        generator.addQuestionSet(asList(new Question("one", "factone"), new Question("two", "facttwo")));
        gameHandler.questions(playerid, "one");

        AnswerStatus answerStatus = gameHandler.answer(playerid, Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);
    }

    @Test
    public void shouldLogAnswers() throws Exception {
        generator.addQuestionSet(asList(new Question("one", "factone"), new Question("two", "facttwo")));
        gameHandler.questions(playerid, "one");
        AnswerStatus answerStatus = gameHandler.answer(playerid, Arrays.asList("factone", "facttwo"));

        assertThat(answerStatus).isEqualTo(AnswerStatus.OK);

        assertThat(gameLogger.getLogEntries()).hasSize(1);
        AnswerLogEntryDTO logEntry = gameLogger.getLogEntries().get(0);
        assertThat(logEntry.getCategory()).isEqualTo("one");

        List<LogEntryDetailDTO> answers = gameLogger.getDetail(logEntry.getId());
        assertThat(answers).onProperty("answer").containsExactly("factone", "facttwo");
        assertThat(answers).onProperty("question").containsExactly("one", "two");
        assertThat(answers).onProperty("status").containsExactly("OK", "OK");
    }

    @Test
    public void shouldGiveCorrectStatus() throws Exception {
        generator.addQuestionSet(asList(new Question("one", "factone"), new Question("two", "facttwo")));
        gameHandler.questions(playerid, "one");
        gameHandler.answer(playerid, Arrays.asList("factone", "facttwo"));

        List<CategoryDTO> categoryDTOs = gameHandler.categoryStatus();
        assertThat(categoryDTOs).hasSize(1);
        CategoryDTO categoryDTO = categoryDTOs.get(0);

        assertThat(categoryDTO.getId()).isEqualTo("one");
        assertThat(categoryDTO.getName()).isEqualTo(generator.description());
        assertThat(categoryDTO.getAnsweredBy()).isEqualTo(playerName);
    }

    @Test
    public void shouldHandleNoQuestion() throws Exception {
        AnswerStatus answerStatus = gameHandler.answer("playerone", Arrays.asList("factone", "facttwo"));
        assertThat(answerStatus).isEqualTo(AnswerStatus.ERROR);
    }

    @Test
    public void shouldRegisterCorrectAnswers() throws Exception {
        String playerid2 = playerHandler.createPlayer("Player Two");

        Question question1 = new Question("one", "factone");
        Question question2 = new Question("two", "facttwo");

        generator.addQuestionSet(asList(question1, question2));
        gameHandler.questions(playerid, "one");
        gameHandler.answer(playerid, asList(question1.getCorrectAnswer(), question2.getCorrectAnswer()));

        generator.addQuestionSet(asList(question1, question2));
        gameHandler.questions(playerid2, "one");
        gameHandler.answer(playerid2, asList(question1.getCorrectAnswer(), question2.getCorrectAnswer()));

        List<CategoriesAnsweredDTO> answered = gameHandler.categoriesAnswered();

        assertThat(answered).onProperty("playerName")
            .containsOnly(playerName, "Player Two");
        assertThat(answered.get(0).getCategories()).containsExactly("one");
        assertThat(answered.get(1).getCategories()).containsExactly("one");
    }

    @Test
    public void shouldEditCategories() {
        gameHandler.editCategories(asList("FromRoman", "one"));
        assertThat(gameHandler.questions(playerid, "one").isOk()).isTrue();
        gameHandler.answer(playerid, Arrays.asList("a"));
        assertThat(gameHandler.categoryStatus("one").getAnsweredBy()).isEqualTo(playerName);

        gameHandler.editCategories(asList("ToRoman", "one"));
        assertThat(gameHandler.questions(playerid, "FromRoman").getErrormessage()).startsWith("Unknown category");
        assertThat(gameHandler.questions(playerid, "ToRoman").isOk()).isTrue();
        assertThat(gameHandler.questions(playerid, "one").isOk()).isTrue();
        assertThat(gameHandler.categoryStatus("one").getAnsweredBy()).isEqualTo(playerName);
    }


    @Before
    public void setup() {
        gameHandler.addQuestionCategory("one", generator);
        gameHandler.setGameLogger(gameLogger);
    }
}
