package no.anderska.wta.servlet;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class AdminFilterTest {
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final AdminFilter adminFilter = new AdminFilter();
    private final FilterChain filterChain = mock(FilterChain.class);
    private final SecurityHandler securityHandler = mock(SecurityHandler.class);
    private final HttpSession session = mock(HttpSession.class);


    @Before
    public void setUp() throws Exception {
        when(req.getSession()).thenReturn(session);
        adminFilter.setSecurityHandler(securityHandler);
    }

    @Test
    public void shouldAdminWhenLoggedIn() throws Exception {
        when(securityHandler.isLoggedIn(session)).thenReturn(true);

        adminFilter.doFilter(req, resp, filterChain);


        verify(filterChain).doFilter(req, resp);
    }

    @Test
    public void shouldDenyAccessWhenNotLoggedIn() throws Exception {
        when(securityHandler.isLoggedIn(session)).thenReturn(false);

        adminFilter.doFilter(req, resp, filterChain);

        verify(filterChain, never()).doFilter(req, resp);
        verify(resp).sendRedirect("../login");
    }
}
