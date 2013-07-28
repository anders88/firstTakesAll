package no.anderska.wta;

import java.util.List;

import no.anderska.wta.dto.AnswerResponse;
import no.anderska.wta.dto.Question;
import no.anderska.wta.dto.QuestionCategory;
import no.anderska.wta.servlet.QuestionChecker;

public class GameEngine implements QuestionChecker {
    

    @Override
    public AnswerResponse checkAnswer(String gamerId, String questionId,
            String answer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<QuestionCategory> allCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Question> listCategory(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
