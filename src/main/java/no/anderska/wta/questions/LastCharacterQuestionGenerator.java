package no.anderska.wta.questions;

public class LastCharacterQuestionGenerator extends AbstractWordQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the last character in the question. Question: 'hello', Correct answer 'o'";

    LastCharacterQuestionGenerator() {
        super(10, DESCRIPTION);
    }

    @Override
    protected String createAnswer(String question) {
        return question.substring(question.length()-1);
    }

}
