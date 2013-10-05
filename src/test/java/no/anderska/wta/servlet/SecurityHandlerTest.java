package no.anderska.wta.servlet;

import org.junit.Test;

import javax.servlet.http.HttpSession;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SecurityHandlerTest {

    private final SecurityHandler securityHandler = new SecurityHandler();
    private final HttpSession session = mock(HttpSession.class);

    @Test
    public void shouldLogon() throws Exception {
        boolean result = securityHandler.login("secret", session);

        assertThat(result).isTrue();

        verify(session).setAttribute("logged","true");
    }

    @Test
    public void shouldDenyLoginWithWrongPassword() throws Exception {
        boolean result = securityHandler.login("xxx", session);

        assertThat(result).isFalse();

        verify(session,never()).setAttribute(anyString(),anyString());
    }

    @Test
    public void shouldIndicateNotLoggedInWhenNotLoggedIn() throws Exception {
        assertThat(securityHandler.isLoggedIn(session)).isFalse();
    }

    @Test
    public void shouldIndicateLoggedInWhenLoggedIn() throws Exception {
        when(session.getAttribute("logged")).thenReturn("true");

        assertThat(securityHandler.isLoggedIn(session)).isTrue();
    }
}
