package no.anderska.wta.questions;

import java.util.LinkedList;
import java.util.List;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.QuestionSet;

public class DummyQuestionGenerator implements QuestionGenerator {

    private final List<Question> defaultQuestion;
    private final List<List<Question>> questionSets = new LinkedList<>();

    @Override
    public QuestionSet generateQuestionSet(String playerid, String categoryid) {
        if (!questionSets.isEmpty()) {
            return new QuestionSet(questionSets.remove(0), this, categoryid);
        } else if (defaultQuestion != null) {
            return new QuestionSet(defaultQuestion, this, categoryid);
        } else {
            throw new IllegalStateException("No questions in " + this);
        }
    }

    public DummyQuestionGenerator(List<Question> defaultQuestion) {
        this.defaultQuestion = defaultQuestion;
    }

    public DummyQuestionGenerator() {
        this.defaultQuestion = null;
    }

    @Override
    public String description() {
        return "Dummy category description";
    }

    @Override
    public int points() {
        return 110;
    }

    public void addQuestionSet(List<Question> questionSet) {
        questionSets.add(questionSet);
    }

}
