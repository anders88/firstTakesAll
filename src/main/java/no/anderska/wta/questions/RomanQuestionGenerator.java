package no.anderska.wta.questions;

import java.util.Random;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;

public class RomanQuestionGenerator extends AbstractQuestionGenerator implements QuestionGenerator {

    private final RomanNumberSolver solver = new RomanNumberSolver();

    private RomanQuestionGenerator(int maxNumber, int numberOfQuestions) {
        super(numberOfQuestions,
                "Compute the Roman number of a given number (max " + maxNumber + "). E.g. '3' = 'III' or '42'='XLII'",
                40);
        if (maxNumber < 1 || maxNumber >= 4000) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
    }

    public RomanQuestionGenerator() {
        this(1000,30);
    }

    private final int maxNumber;
    private final Random random = new Random();

    @Override
    protected Question createQuestion() {
        int pickedNumber = random.nextInt(maxNumber) + 1;
        return new Question("" + pickedNumber, solver.romanNumber(pickedNumber));
    }


}
