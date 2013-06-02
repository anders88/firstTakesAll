package no.anderska.wta;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerServlet extends HttpServlet {

    private PlayerHandler playerHandler;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer
            .append("<form method='POST' action='createGamer.html'>") //
            .append("<input type='text' name='gamerName' value=''/>") //
            .append("<input type='submit' name='createGamer' value=''/>") //
            .append("</form>") //
        ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        playerHandler.createPlayer(req.getParameter("gamerName"));
        resp.sendRedirect("/");
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
        
    }
}
