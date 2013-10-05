package no.anderska.wta.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private SecurityHandler securityHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        writer
                .append("<form method='POST' action='login'>") //
                .append("<intput type='password' placeholder='password' name='password' value=''/>") //
                .append("<intput type='submit' name='loggInn' value='Logg inn'/>") //
                .append("</form>") //
        ;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = securityHandler.login(req.getParameter("password"), req.getSession());
        if (result) {
            resp.sendRedirect("admin/admin.html");
        } else {
            resp.setContentType("text/html");
            resp.getWriter().append("<html><body>Wrong password</body></html>");
        }
    }

    @Override
    public void init() throws ServletException {
        this.securityHandler = new SecurityHandler();
    }

    public void setSecurityHandler(SecurityHandler securityHandler) {
        this.securityHandler = securityHandler;
    }
}
