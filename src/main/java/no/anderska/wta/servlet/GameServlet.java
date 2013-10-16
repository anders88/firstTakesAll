package no.anderska.wta.servlet;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.QuestionList;
import no.anderska.wta.SetupGame;
import no.anderska.wta.game.GameLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameServlet extends HttpServlet {


    private GameHandlerPlayerInterface gameHandlerPlayerInterface;
    private GameLogger gameLogger;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String content = readContent(req);
        try {
            JSONObject jsonObject = new JSONObject(content);
            List<String> answers = asList(jsonObject.getJSONArray("answers"));
            String playerId = jsonObject.getString("playerId");
            AnswerStatus answer = gameHandlerPlayerInterface.answer(playerId, answers);
            answer(resp, answer);
        } catch (JSONException e) {
            answer(resp, AnswerStatus.ERROR);
            gameLogger.error(content);
        }

    }

    private List<String> asList(JSONArray array) throws JSONException {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            res.add(array.getString(i));
        }
        return res;
    }

    private String readContent(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }

    private void answer(HttpServletResponse resp, AnswerStatus answer) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.append("{\"status\" : \"");
        writer.append(answer.toString());
        writer.append("\"}");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String playerid = req.getParameter("playerid");
        String category = req.getParameter("category");

        if (category == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter category");
            return;
        }

        if (playerid == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter playerid");
            return;
        }

        resp.setContentType("text/json");

        QuestionList questionList = gameHandlerPlayerInterface.questions(playerid, category);

        if (questionList.isOk()) {
            List<String> questions = questionList.getQuestions();
            resp.getWriter().append(new JSONArray(questions).toString());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, questionList.getErrormessage());
        }

    }


    @Override
    public void init() throws ServletException {
        this.gameHandlerPlayerInterface = SetupGame.instance().getGameHandler();
        this.gameLogger = SetupGame.instance().getGameLogger();
    }

    public void setGameHandlerPlayerInterface(GameHandlerPlayerInterface gameHandlerPlayerInterface) {
        this.gameHandlerPlayerInterface = gameHandlerPlayerInterface;
    }

    public void setGameLogger(GameLogger gameLogger) {
        this.gameLogger = gameLogger;
    }
}
