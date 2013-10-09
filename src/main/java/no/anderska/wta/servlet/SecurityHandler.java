package no.anderska.wta.servlet;

import javax.servlet.http.HttpSession;

class SecurityHandler {
    private static final String PASSWORD="secret";
    public SecurityHandler() {
    }

    boolean login(String password, HttpSession session) {
        if (!PASSWORD.equals(password)) {
            return false;
        }
        session.setAttribute("logged","true");
        return true;
    }

    boolean isLoggedIn(HttpSession session) {
        return session != null && "true".equals(session.getAttribute("logged"));
    }
}
