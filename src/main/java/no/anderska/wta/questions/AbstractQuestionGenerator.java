package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.List;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;

public abstract class AbstractQuestionGenerator implements QuestionGenerator {

    private final int numberOfQuestions;
    private final String description;
    private final int points;

    protected abstract Question createQuestion();


    public AbstractQuestionGenerator(int numberOfQuestions, String description, int points) {
        if (numberOfQuestions < 0) {
            throw new IllegalArgumentException("numberOfQuestions must be > 0, was " + numberOfQuestions);
        }
        this.numberOfQuestions = numberOfQuestions;
        this.description = description;
        this.points = points;
    }

    @Override
    public List<Question> generateQuestions(String playerid) {
        List<Question> result = new ArrayList<>();
        for (int i=0;i<numberOfQuestions;i++) {
            result.add(createQuestion());
        }
        return result;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int points() {
        return points;
    }
}