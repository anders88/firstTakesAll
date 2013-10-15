package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

public abstract class AbstractWordQuestionGenerator extends AbstractQuestionGenerator {

    public AbstractWordQuestionGenerator(int numberOfQuestions, int points, String description) {
        super(numberOfQuestions, points, description);
    }

    protected abstract String createAnswer(String question);

    @Override
    protected final Question createQuestion() {
        String question = randomString(20);
        return new Question(question, createAnswer(question));
    }

}