package no.anderska.wta.servlet;

import com.google.gson.Gson;
import no.anderska.wta.SetupGame;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.game.GameLogger;
import no.anderska.wta.logging.LogReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LogReaderServlet extends HttpServlet {
    private LogReader logReader;

    @Override
    public void init() throws ServletException {
        logReader = SetupGame.instance().getLogReader();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();

        List<AnswerLogEntryDTO> logEntries = logReader.getLogEntries();

        Gson gson = new Gson();
        writer.append(gson.toJson(logEntries));
    }
}
