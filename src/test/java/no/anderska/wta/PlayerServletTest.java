package no.anderska.wta;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentHelper;
import org.junit.Test;

public class PlayerServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private PlayerServlet servlet = new PlayerServlet();

    @Test
    public void shouldDisplayCreatePlayerPage() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        
        StringWriter htmlDoc = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlDoc));
        
        servlet.service(req, resp);

        assertThat(htmlDoc.toString()) //
            .contains("<form method='POST' action='createGamer.html'") //
            .contains("<input type='text' name='gamerName' value=''") //
            .contains("<input type='submit' name='createGamer' value=''") //
            ;

        DocumentHelper.parseText(htmlDoc.toString());
    }
    
    @Test
    public void shouldAddPlayer() throws Exception {
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
