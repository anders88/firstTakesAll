package no.anderska.wta;

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

import org.dom4j.DocumentHelper;
import org.junit.Before;
import org.junit.Test;

public class PlayerServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private PlayerServlet servlet = new PlayerServlet();
    private PlayerHandler playerHandler = mock(PlayerHandler.class);
    private StringWriter htmlDoc = new StringWriter();

    @Test
    public void shouldDisplayCreatePlayerPage() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        
        
        servlet.service(req, resp);

        assertThat(htmlDoc.toString()) //
            .contains("<form method='POST' action='createGamer.html'") //
            .contains("<input type='text' name='gamerName' value=''") //
            .contains("<input type='submit' name='createGamer' value='Create Gamer'") //
            ;

        DocumentHelper.parseText(htmlDoc.toString());
    }
    
    @Test
    public void shouldAddPlayer() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("Gamers");
        when(req.getMethod()).thenReturn("POST");
        
        servlet.service(req, resp);
        
        verify(resp).setContentType("text/html");
        verify(playerHandler).createPlayer("Gamers");
        verify(resp).sendRedirect("/");
    }

    @Test
    public void shouldNotAllowIllegalCharactersInName() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("Gam<&>ers");
        when(req.getMethod()).thenReturn("POST");
        
        servlet.service(req, resp);
        
        verify(playerHandler,never()).createPlayer(anyString());

        assertThat(htmlDoc.toString()) //
        .contains("<input type='text' name='gamerName' value='Gam&lt;&amp;&gt;ers'") //
        .contains("<p style='color: red;'>Name can only contain letters</p>") //
        ;

        DocumentHelper.parseText(htmlDoc.toString());

    }
    
    @Test
    public void shouldNotAllowEmptyName() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("");
        when(req.getMethod()).thenReturn("POST");
        
        servlet.service(req, resp);
        
        verify(playerHandler,never()).createPlayer(anyString());

        assertThat(htmlDoc.toString()) //
        .contains("<input type='text' name='gamerName' value=''") //
        .contains("<p style='color: red;'>Empty name is not allowed</p>") //
        ;

        DocumentHelper.parseText(htmlDoc.toString());
        
    }

    @Before
    public void setup() throws IOException {
        servlet.setPlayerHandler(playerHandler);
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlDoc));
    }
    
    
}
