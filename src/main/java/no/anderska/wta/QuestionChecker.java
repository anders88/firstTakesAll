package no.anderska.wta;

public interface QuestionChecker {

    AnswerResponse checkAnswer(String gamerId, String questionId, String answer);

}
