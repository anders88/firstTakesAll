package no.anderska.wta.questions;

import no.anderska.wta.game.Question;
import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.QuestionSet;

import java.util.ArrayList;
import java.util.List;

class ComputationQuestionGenerator implements QuestionGenerator {
    private final static int NUM_QUESTIONS = 25;

    @Override
    public QuestionSet generateQuestionSet(String playerid, String categoryid) {
        List<Question> result = new ArrayList<>();
        for (int i = 0; i < NUM_QUESTIONS; i++) {
            String[] qanda = new CalculusGenerator().generateComputation();
            result.add(new Question(qanda[0], qanda[1]));
        }
        return new QuestionSet(result, this, categoryid);
    }

    @Override
    public String description() {
        return "Calculate the answer of the calculus. Multiplication takes precedence over addition and substraction hence '2+4*2' = '10'";
    }

}
