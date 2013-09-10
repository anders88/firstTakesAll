package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;

public abstract class AbstractQuestionGenerator implements QuestionGenerator {

    protected final Random random = new Random();
    private final int numberOfQuestions;
    private final String description;
    private final int points;

    protected abstract Question createQuestion();

    public AbstractQuestionGenerator(int numberOfQuestions, int points, String description) {
        if (numberOfQuestions < 0) {
            throw new IllegalArgumentException("numberOfQuestions must be > 0, was " + numberOfQuestions);
        }
        this.numberOfQuestions = numberOfQuestions;
        this.description = description;
        this.points = points;
    }

    @Override
    public final List<Question> generateQuestions(String playerid) {
        List<Question> result = new ArrayList<>();
        for (int i=0;i<numberOfQuestions;i++) {
            result.add(createQuestion());
        }
        return result;
    }

    @Override
    public final String description() {
        return description;
    }

    @Override
    public final int points() {
        return points;
    }
}