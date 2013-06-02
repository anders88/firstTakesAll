package no.anderska.wta;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class PlayerServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);

    @Test
    public void shouldAddPlayer() throws Exception {
        PlayerServlet servlet = new PlayerServlet();
        when(req.getParameter("gamerName")).thenReturn("Gamers");
        when(req.getMethod()).thenReturn("POST");
        
        PlayerHandler playerHandler = mock(PlayerHandler.class);
        servlet.setPlayerHandler(playerHandler);

        servlet.service(req, resp);
        
        verify(resp).setContentType("text/html");
        verify(playerHandler).createPlayer("Gamers");
        verify(resp).sendRedirect("/");
    }
}
