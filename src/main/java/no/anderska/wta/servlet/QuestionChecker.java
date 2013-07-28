package no.anderska.wta.servlet;

import java.util.List;

import no.anderska.wta.dto.AnswerResponse;
import no.anderska.wta.dto.Question;
import no.anderska.wta.dto.QuestionCategory;

public interface QuestionChecker {

    AnswerResponse checkAnswer(String gamerId, String questionId, String answer);

    List<QuestionCategory> allCategories();

    List<Question> listCategory(int categoryId);
}
