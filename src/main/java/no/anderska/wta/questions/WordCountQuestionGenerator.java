package no.anderska.wta.questions;


public class WordCountQuestionGenerator extends AbstractWordQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the number of characters in the question. Question: 'hello', Correct answer '5'";

    WordCountQuestionGenerator() {
        super(10, 5, DESCRIPTION);
    }

    @Override
    protected String createAnswer(String question) {
        return String.valueOf(question.length());
    }

}
