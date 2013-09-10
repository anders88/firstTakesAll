package no.anderska.wta.engines;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.List;

public class EquationEngine implements QuestionGenerator {

    private int numQuestions = 15;

    @Override
    public List<Question> generateQuestions(String playerid) {
        List<Question> result = new ArrayList<>();
        for (int i=0;i<numQuestions;i++) {
            String[] qanda = new CalculusGenerator().generateEquation();
            result.add(new Question(qanda[0],qanda[1]));
        }
        return result;
    }

    @Override
    public String description() {
        return "Solve for X. Multiplication takes precedence over addition and substraction hence '2+X*2 eq 10' = '4'";
    }

    @Override
    public int points() {
        return 45;
    }
}
