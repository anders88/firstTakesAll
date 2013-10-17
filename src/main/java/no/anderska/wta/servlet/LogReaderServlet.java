package no.anderska.wta.servlet;

import no.anderska.wta.SetupGame;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.dto.LogEntryDetailDTO;
import no.anderska.wta.logging.LogReader;
import org.json.JSONArray;
import org.json.JSONObject;

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

        if ("/detail".equals(req.getPathInfo())) {
            String idstr = req.getParameter("id");
            if (idstr == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Must supply numeric id");
                return;
            }

            try {
                long id = Long.parseLong(idstr);
                List<LogEntryDetailDTO> details = logReader.getDetail(id);
                if (details == null) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Did not find entry with id " + id);
                    return;
                }

                JSONArray json = new JSONArray();
                for (LogEntryDetailDTO detail : details) {
                    JSONObject jsonObject = new JSONObject(detail);
                    json.put(jsonObject);
                }
                writer.append(json.toString());

            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Must supply numeric id");
            }
        } else {
            List<AnswerLogEntryDTO> logEntries = logReader.getLogEntries();

            JSONArray json = new JSONArray();
            for (AnswerLogEntryDTO entry : logEntries) {
                JSONObject jsonObject = new JSONObject(entry);
                json.put(jsonObject);
            }

            writer.append(json.toString());
        }
    }

    public void setLogReader(LogReader logReader) {
        this.logReader = logReader;
    }
}
