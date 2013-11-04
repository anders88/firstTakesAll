package no.anderska.wta.questions;


class EchoQuestionGenerator extends AbstractWordQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the same as the question. Question: 'hello', Correct answer 'hello'";

    private EchoQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
	}

    public EchoQuestionGenerator() {
        this(5);
    }

    @Override
    protected String createAnswer(String question) {
        return question;
    }

}
