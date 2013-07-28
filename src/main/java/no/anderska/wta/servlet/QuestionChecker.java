package no.anderska.wta.servlet;

import java.util.List;

import no.anderska.wta.dto.AnswerResponseDTO;
import no.anderska.wta.dto.QuestionDTO;
import no.anderska.wta.dto.QuestionCategoryDTO;

public interface QuestionChecker {

    AnswerResponseDTO checkAnswer(String gamerId, String questionId, String answer);

    List<QuestionCategoryDTO> allCategories();

    List<QuestionDTO> listCategory(int categoryId);
}
