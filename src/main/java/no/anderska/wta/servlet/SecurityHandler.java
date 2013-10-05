package no.anderska.wta.servlet;

import javax.servlet.http.HttpSession;

public class SecurityHandler {
    private static final String PASSWORD="secret";
    public SecurityHandler() {
    }

    public boolean login(String password, HttpSession session) {
        if (!PASSWORD.equals(password)) {
            return false;
        }
        session.setAttribute("logged","true");
        return true;
    }

    public boolean isLoggedIn(HttpSession session) {
        return session != null && "true".equals(session.getAttribute("logged"));
    }
}
