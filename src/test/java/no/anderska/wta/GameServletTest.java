package no.anderska.wta;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class GameServletTest {
    @Test
    public void shouldCheckForCorrectAnswer() throws Exception {
        GameServlet servlet = new GameServlet();
        
        QuestionChecker questionChecker = mock(QuestionChecker.class);
        servlet.setQuestionChecker(questionChecker);
        
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("gamerId")).thenReturn("123");
        when(req.getParameter("questionId")).thenReturn("444");
        when(req.getParameter("answer")).thenReturn("42");
        
        HttpServletResponse resp = mock(HttpServletResponse.class);
        
        servlet.service(req, resp);
        
        verify(resp).setContentType("application/json");
        verify(questionChecker).checkAnswer("123","444","42");
        
    }
}
