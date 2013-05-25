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
        
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("POST");
        HttpServletResponse resp = mock(HttpServletResponse.class);
        
        servlet.service(req, resp);
        
        verify(resp).setContentType("application/json");
        
    }
}
