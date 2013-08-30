package no.anderska.wta.servlet;

import com.google.gson.Gson;
import no.anderska.wta.GameHandler;
import no.anderska.wta.dto.PlayerAnswerDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class GameServlet extends HttpServlet {


    private GameHandler gameHandler;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/*        resp.setContentType("application/json");
        String gamerId = req.getParameter("gamerId");
        String questionId = req.getParameter("questionId");
        String answer = req.getParameter("answer");        */


        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        String content = sb.toString();
        Gson gson = new Gson();
        PlayerAnswerDto playerAnswerDto = gson.fromJson(content, PlayerAnswerDto.class);

        gameHandler.answer(playerAnswerDto.getPlayerId(),playerAnswerDto.getAnswers());



    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {

    }




    @Override
    public void init() throws ServletException {
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }
}
