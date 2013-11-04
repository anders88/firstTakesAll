package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

class ToRomanQuestionGenerator extends AbstractQuestionGenerator {

    private final RomanNumberSolver solver = new RomanNumberSolver();

    protected ToRomanQuestionGenerator(int maxNumber, int numberOfQuestions, String description) {
        super(numberOfQuestions, description);
        if (maxNumber < 1 || maxNumber >= 4000) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
    }

    public ToRomanQuestionGenerator() {
        this(1000,30, "Compute the Roman number of a given number. E.g. '3' = 'III' or '42'='XLII'");
    }

    private final int maxNumber;

    @Override
    protected Question createQuestion() {
        int pickedNumber = random.nextInt(maxNumber) + 1;
        return new Question("" + pickedNumber, solver.romanNumber(pickedNumber));
    }

    static class Small extends ToRomanQuestionGenerator {

        public Small() {
            super(39, 30, "Compute the Roman number of a number under 40. E.g. '3' = 'III' or '39'='XXXIX'");
        }
    }
}
