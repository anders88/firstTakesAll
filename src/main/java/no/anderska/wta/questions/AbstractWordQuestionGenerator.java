package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

public abstract class AbstractWordQuestionGenerator extends AbstractQuestionGenerator {

    public AbstractWordQuestionGenerator(int numberOfQuestions, int points, String description) {
        super(numberOfQuestions, points, description);
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

    protected static <T> T random(@SuppressWarnings("unchecked") T... options) {
        return options[random.nextInt(options.length)];
    }

    protected static String randomSentence() {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<random.nextInt(10)+1; i++) {
            if (result.length() > 0) {
                if (random.nextInt(50) == 0) {
                    result.append(" &");
                }
                result.append(" ");
            }
            result.append(randomWord());
        }
        return result.toString();
    }

    protected static String randomWord() {
        return randomString(random.nextInt(10) + 5);
    }

}