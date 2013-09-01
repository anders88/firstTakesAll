package no.anderska.wta.servlet;

import com.google.gson.Gson;
import no.anderska.wta.SetupGame;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StatusServlet extends HttpServlet {
    private StatusGiver statusGiver;

    public void setStatusGiver(StatusGiver statusGiver) {
        this.statusGiver = statusGiver;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryDTO> categories = statusGiver.catergoryStatus();
        Gson gson = new Gson();

        resp.setContentType("text/json");
        resp.getWriter().append(gson.toJson(categories));
    }

    @Override
    public void init() throws ServletException {
        this.statusGiver = SetupGame.instance().getGameHandler();
    }
}
