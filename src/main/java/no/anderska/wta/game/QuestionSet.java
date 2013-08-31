package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;

import java.util.List;

public class QuestionSet {
    private List<Question> questions;

    public QuestionSet(List<Question> questions) {
        this.questions = questions;
    }

    public AnswerStatus validateAnswer(List<String> answers) {
        return AnswerStatus.OK;
    }
}
