package no.anderska.wta.game;

import no.anderska.wta.Validate;

public class Question {
    private final String question;
    private final String fact;

    public Question(String question, String fact) {
        this.question = Validate.notNull(question, "question");
        this.fact = Validate.notNull(fact, "fact");
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return fact;
    }

    public boolean isCorrect(String answer) {
        return fact.equals(answer);
    }

    public String getFact() {
        return fact;
    }

    @Override
    public String toString() {
        return "Question<'" + question + "'|'" + fact + "'>";
    }
}
