package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

public class ReverseStringQuestionGenerator extends AbstractQuestionGenerator {
    public ReverseStringQuestionGenerator() {
        super(20, 10, "Reverse the text. 'abc' => 'cba'");
    }

    @Override
    protected Question createQuestion() {
        String question = randomString(20);
        String fact = revrseText(question);
        return new Question(question,fact);
    }

    public String revrseText(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}
