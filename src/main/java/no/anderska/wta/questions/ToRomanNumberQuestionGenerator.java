package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

class ToRomanNumberQuestionGenerator extends AbstractQuestionGenerator {
    private static final String DESCRIPTION = "Compute the Number of a given Roman number. E.g. 'III' = '3' or 'XLII'='42'";
    private final RomanNumberSolver solver = new RomanNumberSolver();

    private ToRomanNumberQuestionGenerator(int maxNumber, int numberOfQuestions) {
        super(numberOfQuestions, DESCRIPTION);
        if (maxNumber < 1 || maxNumber >= 4000) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
    }

    public ToRomanNumberQuestionGenerator() {
        this(1000,30);
    }

    @Override
    protected Question createQuestion() {
        int number = random.nextInt(maxNumber) + 1;
        return new Question(solver.romanNumber(number),"" + number);
    }

    private final int maxNumber;
}
