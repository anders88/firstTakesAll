package no.anderska.wta.servlet;

import org.dom4j.DocumentHelper;
import org.fest.assertions.Assertions;
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

public class LoginServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private final LoginServlet servlet = new LoginServlet();
    private StringWriter htmlDoc = new StringWriter();

    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlDoc));
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
}
