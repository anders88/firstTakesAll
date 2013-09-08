package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;

public class ComputationEngine implements Engine {
    private final int numQuestions = 25;

    @Override
    public List<Question> generateQuestions(String playerid) {
        List<Question> result = new ArrayList<>();
        for (int i=0;i<numQuestions;i++) {
            String[] qanda = new CalculusGenerator().generateComputation();
            result.add(new Question(qanda[0],qanda[1]));
        }
        return result;
    }

    @Override
    public String description() {
        return "Calculate the answer of the calculus. Multiplication takes precedence over addition and substraction hence '2+4*2' = '10'";
    }

    @Override
    public int points() {
        return 25;
    }
}
