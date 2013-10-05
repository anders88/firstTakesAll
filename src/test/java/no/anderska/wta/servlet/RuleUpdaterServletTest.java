package no.anderska.wta.servlet;

import no.anderska.wta.game.AdminHandler;
import org.dom4j.DocumentHelper;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RuleUpdaterServletTest {
    private final RuleUpdateServlet servlet = new RuleUpdateServlet();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final StringWriter htmlSource = new StringWriter();
    private final AdminHandler adminHandler = mock(AdminHandler.class);


    @Test
    public void shouldRestartGame() throws Exception {
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("password")).thenReturn("secret");
        when(req.getParameter("action")).thenReturn("resetAll");

        servlet.service(req, resp);

        verify(resp).setContentType("text/html");
        verify(adminHandler).restartGame();
        assertThat(htmlSource.toString()).contains("Action performed");

        DocumentHelper.parseText(htmlSource.toString());

    }


    @Test
    public void sholdGiveIndicationOnWrongPassword() throws Exception {
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("action")).thenReturn("resetAll");

        when(adminHandler.restartGame()).thenReturn("Wrong password");

        servlet.service(req, resp);

        assertThat(htmlSource.toString()).contains("Wrong password");

        DocumentHelper.parseText(htmlSource.toString());

    }

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
        servlet.setAdminHandler(adminHandler);
    }


}
