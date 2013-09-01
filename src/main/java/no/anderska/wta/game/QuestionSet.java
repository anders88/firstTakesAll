package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;

import java.util.List;

public class QuestionSet {
    private List<Question> questions;

    public QuestionSet(List<Question> questions) {
        this.questions = questions;
    }

    public AnswerStatus validateAnswer(List<String> answers) {
        if (answers == null) {
            return AnswerStatus.ERROR;
        }
        if (questions.size() != answers.size()) {
            return AnswerStatus.WRONG;
        }
        for (int i=0;i<answers.size();i++) {
            if (!questions.get(i).isCorrect(answers.get(i))) {
                return AnswerStatus.WRONG;
            }
        }
        return AnswerStatus.OK;
    }
}
