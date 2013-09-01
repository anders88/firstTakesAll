package no.anderska.wta.servlet;

import com.google.gson.Gson;
import no.anderska.wta.SetupGame;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.GameStatusDTO;
import no.anderska.wta.dto.PlayerDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StatusServlet extends HttpServlet {
    private StatusGiver statusGiver;
    private PlayerHandler playerHandler;

    public void setStatusGiver(StatusGiver statusGiver) {
        this.statusGiver = statusGiver;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryDTO> categories = statusGiver.catergoryStatus();
        List<PlayerDTO> players = playerHandler.playerList();
        GameStatusDTO gameStatusDTO = new GameStatusDTO(players, categories);
        Gson gson = new Gson();

        resp.setContentType("text/json");
        resp.getWriter().append(gson.toJson(gameStatusDTO));
    }

    @Override
    public void init() throws ServletException {
        this.statusGiver = SetupGame.instance().getGameHandler();
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

}
