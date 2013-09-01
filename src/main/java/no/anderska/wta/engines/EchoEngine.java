package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.*;

public class EchoEngine implements Engine {

    private int numberOfQuestions;

    public EchoEngine(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
        if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Number of questions must be positive");
		}
	}


    @Override
    public List<Question> generateQuestions(String playerid) {

        List<Question> questions = new ArrayList<>(numberOfQuestions);
        for (int i=0;i<numberOfQuestions;i++) {
            String rand = UUID.randomUUID().toString();
            questions.add(new Question(rand,rand));
        }
        return questions;
    }

    @Override
    public String description() {
        return "The answer is the same as the question. Question: 'hello', Correct answer 'hello'";
    }

    @Override
    public int points() {
        return 1;
    }
}
