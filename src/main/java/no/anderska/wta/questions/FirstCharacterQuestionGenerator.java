package no.anderska.wta.questions;


public class FirstCharacterQuestionGenerator extends AbstractWordQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the first character in the question. Question: 'hello', Correct answer 'h'";

    FirstCharacterQuestionGenerator() {
        super(10, DESCRIPTION);
    }

    @Override
    protected String createAnswer(String question) {
        return question.substring(0, 1);
    }

}
