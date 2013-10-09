package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

class RomanQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION = "Compute the Roman number of a given number. E.g. '3' = 'III' or '42'='XLII'";
    private final RomanNumberSolver solver = new RomanNumberSolver();

    private RomanQuestionGenerator(int maxNumber, int numberOfQuestions) {
        super(numberOfQuestions, 40, DESCRIPTION);
        if (maxNumber < 1 || maxNumber >= 4000) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
    }

    public RomanQuestionGenerator() {
        this(1000,30);
    }

    private final int maxNumber;

    @Override
    protected Question createQuestion() {
        int pickedNumber = random.nextInt(maxNumber) + 1;
        return new Question("" + pickedNumber, solver.romanNumber(pickedNumber));
    }
}
