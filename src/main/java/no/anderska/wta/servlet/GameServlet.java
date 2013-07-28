package no.anderska.wta.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.dto.AnswerResponse;
import no.anderska.wta.dto.AnswerResponse.AnswerStatus;
import no.anderska.wta.dto.Question;
import no.anderska.wta.dto.QuestionCategory;

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
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {

        if ("/category".equals(req.getPathInfo())) {
            displayGivenCategory(req, resp);
        } else {
            Gson gson = new Gson();
            List<QuestionCategory> allCategories = questionChecker.allCategories();
            resp.getWriter().append(gson.toJson(allCategories));
        }
    }

    private void displayGivenCategory(HttpServletRequest req,
            HttpServletResponse resp) throws IOException {
        String givenId = req.getParameter("id");
        if (givenId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Missing parameter id");
            return;
        }
        Integer categoryId;
        try {
            categoryId = Integer.parseInt(givenId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Illegal category id");
            return;
        }
        Gson gson = new Gson();
        List<Question> questions = questionChecker.listCategory(categoryId);
        resp.getWriter().append(gson.toJson(questions));
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
