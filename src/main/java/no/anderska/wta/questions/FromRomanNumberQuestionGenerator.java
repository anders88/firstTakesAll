package no.anderska.wta.questions;

import no.anderska.wta.Validate;
import no.anderska.wta.game.Question;

class FromRomanNumberQuestionGenerator extends AbstractQuestionGenerator {

    private final RomanNumberSolver solver = new RomanNumberSolver();

    protected FromRomanNumberQuestionGenerator(int maxNumber, int numberOfQuestions, String description) {
        super(numberOfQuestions, description);
        this.maxNumber = Validate.numberInRange(maxNumber, "maxNumber", 1, 3999);
    }

    public FromRomanNumberQuestionGenerator() {
        this(3999, 30, "Compute the value of a given Roman number. E.g. 'III' = '3' or 'XLII'='42'");
    }

    @Override
    protected Question createQuestion() {
        int number = random.nextInt(maxNumber) + 1;
        return new Question(solver.romanNumber(number),"" + number);
    }

    private final int maxNumber;

    static class Small extends FromRomanNumberQuestionGenerator {
        public Small() {
            super(39, 30, "Compute the value of a Roman number under 40. E.g. 'III' = '3' or 'XXXIX'='39'");
        }
    }
}
