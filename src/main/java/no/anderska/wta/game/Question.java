package no.anderska.wta.game;

public class Question {
    private final String question;
    private final String fact;

    public Question(String question, String fact) {
        if (question == null || fact == null) {
            throw new IllegalArgumentException("Question and fact must be non null");
        }
        this.question = question;
        this.fact = fact;
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
