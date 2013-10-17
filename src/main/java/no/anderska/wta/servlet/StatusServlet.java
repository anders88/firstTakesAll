package no.anderska.wta.servlet;

import no.anderska.wta.SetupGame;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.PlayerDTO;
import org.json.JSONException;
import org.json.JSONObject;

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
        resp.setContentType("text/json");

        List<CategoryDTO> categories = statusGiver.categoryStatus();
        List<PlayerDTO> players = playerHandler.playerList();
        JSONObject result = new JSONObject();
        try {
            result.put("players", JsonUtil.toJson(players));
            result.put("categories", JsonUtil.toJson(categories));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        resp.getWriter().append(result.toString());
    }

    @Override
    public void init() throws ServletException {
        this.statusGiver = SetupGame.instance().getGameHandler();
        this.playerHandler = SetupGame.instance().getPlayerHandler();
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

}
