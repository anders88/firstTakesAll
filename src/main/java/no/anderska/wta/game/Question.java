package no.anderska.wta.game;

public class Question {
    private String question;
    private String fact;

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

    public boolean isCorrect(String answer) {
        return question.equals(answer);
    }
}
