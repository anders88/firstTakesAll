package no.anderska.wta.servlet;


import no.anderska.wta.game.*;
import no.anderska.wta.questions.DummyQuestionGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GameServletTest {

    private final GameServlet servlet = new GameServlet();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final StringWriter htmlSource = new StringWriter();
    private final GameHandler gameHandler = new GameHandler();
    private final Question q1 = new Question("q1", "one");
    private final Question q2 = new Question("q2", "two");
    private final Question q3 = new Question("q3", "three");
    private final DummyQuestionGenerator questionGenerator = new DummyQuestionGenerator();
    private final GameLogger gameLogger = mock(GameLogger.class);

    @Test
    public void shouldHandleAnswer() throws Exception {
        String playerid = gameHandler.getPlayerHandler().createPlayer("Some name");
        gameHandler.putQuestion(playerid, new QuestionSet(asList(q1, q2, q3), questionGenerator, "Some id"));

        List<String> answerList = asList(q1.getCorrectAnswer(), q2.getCorrectAnswer(), q3.getCorrectAnswer());


        when(req.getMethod()).thenReturn("POST");
        JSONObject answer = new JSONObject();
        answer.put("playerId",playerid);
        answer.put("answers", new JSONArray(answerList));


        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(answer.toString())));

        servlet.service(req, resp);

        assertThat(htmlSource.toString()).isEqualTo("{\"status\" : \"OK\"}");
        assertThat(gameHandler.getPlayerHandler().getPoints(playerid))
                .isEqualTo(questionGenerator.points());
    }

    @Test
    public void shouldHandleIllegalJson() throws Exception {
        when(req.getMethod()).thenReturn("POST");
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader("xxx")));

        servlet.service(req, resp);

        assertThat(htmlSource.toString()).isEqualTo("{\"status\" : \"ERROR\"}");
        verify(gameLogger).error("xxx");
    }

    @Test
    public void shouldGiveQuestions() throws Exception {
        Map<String, QuestionGenerator> generators = new HashMap<>();
        generators.put("catid", questionGenerator);
        gameHandler.setActiveGenerators(generators);

        String playerid = gameHandler.getPlayerHandler().createPlayer("Some name");

        questionGenerator.addQuestionSet(Arrays.asList(q1, q2, q3));

        when(req.getMethod()).thenReturn("GET");
        when(req.getParameter("playerid")).thenReturn(playerid);
        when(req.getParameter("category")).thenReturn("catid");

        servlet.service(req, resp);

        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(resp).setContentType("text/json");


        JSONArray answers = new JSONArray(htmlSource.toString());

        assertThat(answers.length()).isEqualTo(3);
        assertThat(answers.get(0)).isEqualTo(q1.getQuestion());
        assertThat(answers.get(1)).isEqualTo(q2.getQuestion());
        assertThat(answers.get(2)).isEqualTo(q3.getQuestion());

    }

    @Test
    public void shouldGiveErrorOnMissingParameters() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getParameter("playerid")).thenReturn("playerone");

        servlet.service(req, resp);

        verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter category");
    }

    @Test
    public void shouldGiveErrorWhenHandlerSaysSo() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getParameter("playerid")).thenReturn("playerone");
        when(req.getParameter("category")).thenReturn("catid");

        servlet.service(req, resp);

        verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown player 'playerone'");
    }

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
        servlet.setGameHandlerPlayerInterface(gameHandler);
        servlet.setGameLogger(gameLogger);
        gameHandler.setGameLogger(gameLogger);
    }
}
