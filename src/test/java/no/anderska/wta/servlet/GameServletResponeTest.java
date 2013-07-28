package no.anderska.wta.servlet;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.dto.AnswerResponse;
import no.anderska.wta.dto.AnswerResponse.AnswerStatus;
import no.anderska.wta.servlet.GameServlet;
import no.anderska.wta.servlet.QuestionChecker;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class GameServletResponeTest {
    private GameServlet servlet = new GameServlet();
    private QuestionChecker questionChecker = mock(QuestionChecker.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private StringWriter jsonResponse = new StringWriter();

    @Test
    public void shouldCheckForCorrectAnswer() throws Exception {
        setupOkParameters();

        servlet.service(req, resp);
        
        verify(resp).setContentType("application/json");
        verify(questionChecker).checkAnswer("123","444","42");              
    }
    
    @Test
    public void shouldReplyOkWhenAnswerCorrect() throws Exception {
        setupOkParameters();
        when(questionChecker.checkAnswer(anyString(), anyString(), anyString())).thenReturn(AnswerResponse.create(AnswerStatus.OK));
        
        servlet.service(req, resp);
        
        assertThat(jsonResponse.toString()).isEqualTo("{\"answerStatus\":\"OK\"}");
        Gson gson = new Gson();
        AnswerResponse response = gson.fromJson(jsonResponse.toString(), AnswerResponse.class);
        assertThat(response).isEqualTo(AnswerResponse.create(AnswerStatus.OK));
    }
    
    @Test
    public void shouldReportErrorIfAnsweringWrong() throws Exception {
        setupOkParameters();
        when(questionChecker.checkAnswer(anyString(), anyString(), anyString())).thenReturn(AnswerResponse.create(AnswerStatus.WRONG));
        
        servlet.service(req, resp);

        Gson gson = new Gson();
        AnswerResponse response = gson.fromJson(jsonResponse.toString(), AnswerResponse.class);
        assertThat(response).isEqualTo(AnswerResponse.create(AnswerStatus.WRONG));
    }
    
    @Test
    public void shouldReportMissingParameter() throws Exception {
        when(req.getParameter("gamerId")).thenReturn("123");
        when(req.getParameter("questionId")).thenReturn("444");

        servlet.service(req, resp);

        verify(questionChecker,never()).checkAnswer(anyString(), anyString(), anyString());
        
        Gson gson = new Gson();
        AnswerResponse response = gson.fromJson(jsonResponse.toString(), AnswerResponse.class);
        assertThat(response).isEqualTo(AnswerResponse.create(AnswerStatus.MISSING_PARAMETER));
        
        assertThat(response.getDescription()).isEqualTo("answer is required");
    }

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(jsonResponse));
        servlet.setQuestionChecker(questionChecker);
        when(req.getMethod()).thenReturn("POST");
        
    }

    private void setupOkParameters() {
        when(req.getParameter("gamerId")).thenReturn("123");
        when(req.getParameter("questionId")).thenReturn("444");
        when(req.getParameter("answer")).thenReturn("42");
    }
}
