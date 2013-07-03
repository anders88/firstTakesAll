package no.anderska.wta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.dto.AnswerResponse;
import no.anderska.wta.dto.AnswerResponse.AnswerStatus;

import com.google.gson.Gson;

public class GameServlet extends HttpServlet {
    
    private QuestionChecker questionChecker;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        
        String gamerId = req.getParameter("gamerId");
        String questionId = req.getParameter("questionId");
        String answer = req.getParameter("answer");
        
        AnswerResponse answerResponse;
        if (nullOrEmpty(gamerId)) {
            answerResponse = parameterError("gamerId is required");
        } else if (nullOrEmpty(questionId)) {            
            answerResponse = parameterError("questionId is required");
        } else if (nullOrEmpty(answer)) {            
            answerResponse = parameterError("answer is required");
        } else {
            answerResponse = questionChecker.checkAnswer(gamerId, questionId, answer);
        }
        
        writeResponse(resp, answerResponse);
    }

    private void writeResponse(HttpServletResponse resp,
            AnswerResponse answerResponse) throws IOException {
        Gson gson = new Gson();
        String response = gson.toJson(answerResponse);
        resp.getWriter().append(response);
    }

    private AnswerResponse parameterError(String message) {
        return AnswerResponse.create(AnswerStatus.MISSING_PARAMETER).withDescription(message);
    }

    private boolean nullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public void setQuestionChecker(QuestionChecker questionChecker) {
        this.questionChecker = questionChecker;
    }
}
