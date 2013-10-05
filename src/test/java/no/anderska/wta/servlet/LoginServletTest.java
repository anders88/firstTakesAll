package no.anderska.wta.servlet;

import org.dom4j.DocumentHelper;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private final LoginServlet servlet = new LoginServlet();
    private StringWriter htmlDoc = new StringWriter();
    private final HttpSession session = mock(HttpSession.class);
    private final SecurityHandler securityHandler = mock(SecurityHandler.class);

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlDoc));
        when(req.getSession()).thenReturn(session);
        servlet.setSecurityHandler(securityHandler);
    }

    @Test
    public void shouldDisplayLoginPage() throws Exception {
        when(req.getMethod()).thenReturn("GET");

        servlet.service(req,resp);

        verify(resp).setContentType("text/html");
        assertThat(htmlDoc.toString()) //
                .contains("<form method='POST' action='login'") //
                .contains("<intput type='password' placeholder='password' name='password' value=''") //
                .contains("<intput type='submit' name='loggInn' value='Logg inn'") //
        ;

        DocumentHelper.parseText(htmlDoc.toString());
    }

    @Test
    public void shouldHandleLogin() throws Exception {
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("password")).thenReturn("mypass");
        when(securityHandler.login(anyString(),any(HttpSession.class))).thenReturn(true);

        servlet.service(req, resp);

        verify(securityHandler).login("mypass", session);
        verify(resp).sendRedirect("admin/admin.html");
    }

    @Test
    public void shouldHandleBadLogin() throws Exception {
        when(req.getMethod()).thenReturn("POST");
        when(req.getParameter("password")).thenReturn("mypass");
        when(securityHandler.login(anyString(),any(HttpSession.class))).thenReturn(false);

        servlet.service(req, resp);

        assertThat(htmlDoc.toString()).contains("Wrong password");
        verify(resp,never()).sendRedirect(anyString());

    }
}
