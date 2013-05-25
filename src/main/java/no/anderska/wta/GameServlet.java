package no.anderska.wta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet {
    
    private QuestionChecker questionChecker;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        questionChecker.checkAnswer(req.getParameter("gamerId"), req.getParameter("questionId"), req.getParameter("answer"));
    }

    public void setQuestionChecker(QuestionChecker questionChecker) {
        this.questionChecker = questionChecker;
    }
}
