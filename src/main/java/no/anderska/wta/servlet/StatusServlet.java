package no.anderska.wta.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.SetupGame;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.GameStatusDTO;
import no.anderska.wta.dto.PlayerDTO;
import no.anderska.wta.game.AdminHandler;

import com.google.gson.Gson;

public class StatusServlet extends HttpServlet {
    private StatusGiver statusGiver;
    private PlayerHandler playerHandler;
    private AdminHandler adminHandler;

    public void setStatusGiver(StatusGiver statusGiver) {
        this.statusGiver = statusGiver;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        Gson gson = new Gson();
        if ("/allStat".equals(req.getPathInfo())) {
            List<CategoriesAnsweredDTO> categoriesAnsweredDTOs = adminHandler.categoriesAnswered();
            resp.getWriter().append(gson.toJson(categoriesAnsweredDTOs));
            return;
        }
        List<CategoryDTO> categories = statusGiver.categoryStatus();
        List<PlayerDTO> players = playerHandler.playerList();
        GameStatusDTO gameStatusDTO = new GameStatusDTO(players, categories);

        resp.getWriter().append(gson.toJson(gameStatusDTO));
    }

    @Override
    public void init() throws ServletException {
        this.statusGiver = SetupGame.instance().getGameHandler();
        this.playerHandler = SetupGame.instance().getPlayerHandler();
        this.adminHandler = SetupGame.instance().getGameHandler();
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void setAdminHandler(AdminHandler adminHandler) {
        this.adminHandler = adminHandler;
    }
}
