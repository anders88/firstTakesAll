package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RomanNumberEngine implements Engine {

    private RomanNumberSolver solver = new RomanNumberSolver();

    public RomanNumberEngine(int maxNumber, int numberOfQuestions) {
        if (maxNumber < 1 || maxNumber > 1000 || numberOfQuestions < 1) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.maxNumber = maxNumber;
        this.numberOfQuestions = numberOfQuestions;
    }

    private int maxNumber;
    private int numberOfQuestions;


    @Override
    public List<Question> generateQuestions(String playerid) {

        List<Question> result = new ArrayList<>();
        Random random = new Random();

        for (int i=0;i<numberOfQuestions;i++) {
            int pickedNumber = random.nextInt(maxNumber) + 1;
            result.add(new Question("" + pickedNumber,solver.romanNumber(pickedNumber)));
        }

        return result;
    }

    @Override
    public String description() {
        return "Compute the Roman number of a given number (max " + maxNumber + "). E.g. '3' = 'III' or '42'='XLII'";
    }

    @Override
    public int points() {
        return 4;
    }




}
