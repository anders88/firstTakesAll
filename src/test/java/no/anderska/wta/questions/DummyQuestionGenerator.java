package no.anderska.wta.questions;

import java.util.LinkedList;
import java.util.List;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;

public class DummyQuestionGenerator implements QuestionGenerator {

    private final List<List<Question>> questionSets = new LinkedList<>();

    @Override
    public List<Question> generateQuestions(String playerid) {
        return questionSets.remove(0);
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public int points() {
        return 110;
    }

    public void addQuestionSet(List<Question> questionSet) {
        questionSets.add(questionSet);
    }

}
