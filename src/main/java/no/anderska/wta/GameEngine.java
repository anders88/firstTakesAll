package no.anderska.wta;

import java.util.List;

import no.anderska.wta.dto.AnswerResponseDTO;
import no.anderska.wta.dto.QuestionDTO;
import no.anderska.wta.dto.QuestionCategoryDTO;
import no.anderska.wta.servlet.QuestionChecker;

public class GameEngine implements QuestionChecker {
    

    @Override
    public AnswerResponseDTO checkAnswer(String gamerId, String questionId,
            String answer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<QuestionCategoryDTO> allCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<QuestionDTO> listCategory(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
