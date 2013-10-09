package no.anderska.wta.questions;

import java.util.UUID;

import no.anderska.wta.game.Question;

class EchoQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the same as the question. Question: 'hello', Correct answer 'hello'";

    private EchoQuestionGenerator(int numberOfQuestions) {
        super(numberOfQuestions, 2, DESCRIPTION);
	}

    public EchoQuestionGenerator() {
        this(5);
    }

    @Override
    protected Question createQuestion() {
        String rand = UUID.randomUUID().toString();
        return new Question(rand,rand);
    }

}
