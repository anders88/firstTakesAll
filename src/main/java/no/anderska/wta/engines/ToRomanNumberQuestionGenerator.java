package no.anderska.wta.engines;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToRomanNumberQuestionGenerator implements QuestionGenerator {
    private RomanNumberSolver solver = new RomanNumberSolver();

    private ToRomanNumberQuestionGenerator(int maxNumber, int numberOfQuestions) {
        if (maxNumber < 1 || maxNumber > 1000 || numberOfQuestions < 1) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
        this.numberOfQuestions = numberOfQuestions;
    }

    public ToRomanNumberQuestionGenerator() {
        this(1000,30);
    }

    private int maxNumber;
    private int numberOfQuestions;


    @Override
    public List<Question> generateQuestions(String playerid) {
        Random random = new Random();
        List<Question> questions = new ArrayList<>();

        for (int i=0;i<numberOfQuestions;i++) {
            int number = random.nextInt(maxNumber) + 1;
            questions.add(new Question(solver.romanNumber(number),"" + number));
        }

        return questions;
    }

    @Override
    public String description() {
        return "Compute the Number of a given Roman number (max " + maxNumber + "). E.g. 'III' = '3' or 'XLII'='42'";
    }

    @Override
    public int points() {
        return 40;
    }
}
