package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

public abstract class AbstractWordQuestionGenerator extends AbstractQuestionGenerator {

    public AbstractWordQuestionGenerator(int numberOfQuestions, int points, String description) {
        super(numberOfQuestions, description);
    }

    protected abstract String createAnswer(String question);

    @Override
    protected final Question createQuestion() {
        String question = createQuestionString();
        return new Question(question, createAnswer(question));
    }

    protected String createQuestionString() {
        return randomString(10);
    }

}