package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

class AdditionQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION = "The answer is the sum. Question: '4+2+7', Correct answer '13'";
    private final int maxFactors;

    private AdditionQuestionGenerator(int numberOfQuestions, int maxFactors) {
        super(numberOfQuestions, 10, DESCRIPTION);
        if (maxFactors < 2) {
            throw new IllegalArgumentException("Number of factors must be 2 or greater");
        }
        this.maxFactors = maxFactors;
	}

    public AdditionQuestionGenerator() {
        this(25,4);
    }

    @Override
    protected Question createQuestion() {
        StringBuilder question = new StringBuilder();
        int numFactors = (maxFactors == 2) ? 2 : random.nextInt(maxFactors-1)+2;
        int answer = 0;
        for (int j=0;j<numFactors;j++) {
            if (j > 0) {
                question.append("+");
            }
            int factor = random.nextInt(999) + 1;
            answer+=factor;
            question.append(factor);
        }
        return new Question(question.toString(),"" + answer);
    }

}
