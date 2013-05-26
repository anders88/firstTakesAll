package no.anderska.wta;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class GameServletTest {
    private GameServlet servlet = new GameServlet();
    private QuestionChecker questionChecker = mock(QuestionChecker.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private StringWriter jsonResponse = new StringWriter();

    @Test
    public void shouldCheckForCorrectAnswer() throws Exception {
        servlet.service(req, resp);
        
        verify(resp).setContentType("application/json");
        verify(questionChecker).checkAnswer("123","444","42");              
    }
    
    @Test
    public void shouldReplyOkWhenAnswerCorrect() throws Exception {
        when(questionChecker.checkAnswer(anyString(), anyString(), anyString())).thenReturn(AnswerResponse.ok());
        
        servlet.service(req, resp);
        
        assertThat(jsonResponse.toString()).isEqualTo("{\"answerStatus\":\"OK\"}");
    }

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(jsonResponse));
        servlet.setQuestionChecker(questionChecker);
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("gamerId")).thenReturn("123");
        when(req.getParameter("questionId")).thenReturn("444");
        when(req.getParameter("answer")).thenReturn("42");
    }
}
