package no.anderska.wta.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.QuestionList;
import no.anderska.wta.SetupGame;
import no.anderska.wta.dto.PlayerAnswerDto;
import no.anderska.wta.game.GameLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GameServlet extends HttpServlet {


    private GameHandlerPlayerInterface gameHandlerPlayerInterface;
    private GameLogger gameLogger;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String content = readContent(req);
        Gson gson = new Gson();
        PlayerAnswerDto playerAnswerDto = null;
        try {
            playerAnswerDto = gson.fromJson(content, PlayerAnswerDto.class);
        } catch (JsonSyntaxException e) {
            answer(resp,AnswerStatus.ERROR);
            gameLogger.error(content);
            return;
        }

        AnswerStatus answer = gameHandlerPlayerInterface.answer(playerAnswerDto.getPlayerId(), playerAnswerDto.getAnswers());

        answer(resp, answer);
    }

    private String readContent(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        return sb.toString();
    }

    private PrintWriter answer(HttpServletResponse resp, AnswerStatus answer) throws IOException {
        return resp.getWriter().append("{\"status\" : \"" + answer + "\"}");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        String playerid = req.getParameter("playerid");
        String category = req.getParameter("category");

        if (category == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Missing parameter category");
            return;
        }

        if (playerid == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Missing parameter playerid");
            return;
        }

        resp.setContentType("text/json");

        QuestionList questionList = gameHandlerPlayerInterface.questions(playerid, category);

        if (questionList.isOk()) {
            resp.getWriter().append(new Gson().toJson(questionList.getQuestions()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,questionList.getErrormessage());
        }

    }




    @Override
    public void init() throws ServletException {
        this.gameHandlerPlayerInterface = SetupGame.instance().getGameHandler();
    }

    public void setGameHandlerPlayerInterface(GameHandlerPlayerInterface gameHandlerPlayerInterface) {
        this.gameHandlerPlayerInterface = gameHandlerPlayerInterface;
    }

    public void setGameLogger(GameLogger gameLogger) {
        this.gameLogger = gameLogger;
    }
}
