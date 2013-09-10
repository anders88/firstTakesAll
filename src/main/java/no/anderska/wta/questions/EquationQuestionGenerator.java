package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

public class EquationQuestionGenerator extends AbstractQuestionGenerator {

    private static final String DESCRIPTION = "Solve for X. Multiplication takes precedence over addition and substraction hence '2+X*2 eq 10' = '4'";

    public EquationQuestionGenerator() {
        super(15, 45, DESCRIPTION);
    }

    @Override
    protected Question createQuestion() {
        String[] qanda = new CalculusGenerator().generateEquation();
        Question question = new Question(qanda[0],qanda[1]);
        return question;
    }
}
