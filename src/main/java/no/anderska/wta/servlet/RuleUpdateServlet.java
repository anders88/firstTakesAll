package no.anderska.wta.servlet;

import no.anderska.wta.SetupGame;
import no.anderska.wta.game.AdminHandler;
import no.anderska.wta.game.GameLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RuleUpdateServlet extends HttpServlet {
    private AdminHandler adminHandler;
    private GameLogger gameLogger;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String errormessage = null;
        if ("resetAll".equals(action)) {
            errormessage = adminHandler.restartGame();
        } else if ("resetCategories".equals(action)) {
            errormessage = adminHandler.resetCategories();
        } else if ("categoryEdit".equals(action)) {
            errormessage = adminHandler.editCategories(Arrays.asList(req.getParameterValues("engines")));
        } else if ("looserBonus".equals(action)) {
            errormessage = adminHandler.toggleLoserBonus();
        } else if ("clearLog".equals(action)) {
            gameLogger.clear();
            errormessage = null;
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
        this.gameLogger = SetupGame.instance().getGameLogger();
    }



}
