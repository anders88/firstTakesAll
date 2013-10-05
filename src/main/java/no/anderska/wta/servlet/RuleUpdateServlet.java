package no.anderska.wta.servlet;

import no.anderska.wta.SetupGame;
import no.anderska.wta.game.AdminHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RuleUpdateServlet extends HttpServlet {
    private AdminHandler adminHandler;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String errormessage = null;
        String password = req.getParameter("password");
        if ("resetAll".equals(action)) {
            errormessage = adminHandler.restartGame(password);
        } else if ("resetCategories".equals(action)) {
            errormessage = adminHandler.resetCategories(password);
        } else if ("categoryEdit".equals(action)) {
            errormessage = adminHandler.editCategories(password, Arrays.asList(req.getParameterValues("engines")));
        } else if ("looserBonus".equals(action)) {
            errormessage = adminHandler.toggleLoserBonus(password);
        } else {
            errormessage = "Unknown action";
        }
        resp.setContentType("text/html");
        writeResponse(resp.getWriter(),errormessage != null ? errormessage : "Action performed");
    }

    private void writeResponse(PrintWriter writer, String message) {
        writer.append("<html><body><p>");
        writer.append(message);
        writer.append("</p><p><a href='admin.html'>Back</a></p></body></html>");
    }

    public void setAdminHandler(AdminHandler adminHandler) {
        this.adminHandler = adminHandler;
    }

    @Override
    public void init() throws ServletException {
        this.adminHandler = SetupGame.instance().getGameHandler();
    }



}
