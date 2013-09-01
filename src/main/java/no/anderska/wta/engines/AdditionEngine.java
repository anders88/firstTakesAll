package no.anderska.wta.engines;

import no.anderska.wta.game.Engine;
import no.anderska.wta.game.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AdditionEngine implements Engine {

    @Override
    public List<Question> generateQuestions(String playerid) {
        Random random = new Random();
        List<Question> questions = new ArrayList<>();
        for (int i=1;i<=numberOfQuestions;i++) {
            StringBuilder question = new StringBuilder();
            int numFactors = (maxFactors == 2) ? 2 : random.nextInt(maxFactors-1)+2;
            int answer = 0;
            for (int j=0;j<numFactors;j++) {
                if (j > 0) {
                    question.append("+");
                }
                int factor = random.nextInt(999) + 1;
                answer+=factor;
                question.append(factor);
            }
            questions.add(new Question(question.toString(),"" + answer));
        }
        return questions;
    }

    @Override
    public String description() {
        return "The answer is the sum. Question: '4+2+7', Correct answer '13'";
    }

    @Override
    public int points() {
        return 3;
    }



    private int numberOfQuestions;
    private final int maxFactors;

    public AdditionEngine(int numberOfQuestions, int maxFactors) {
        if (maxFactors < 2) {
            if (numberOfQuestions <= 0) {
                throw new IllegalArgumentException("Number of questions must be positive");
            }
            throw new IllegalArgumentException("Number of factors must be 2 or greater");
        }
        this.numberOfQuestions = numberOfQuestions;
        this.maxFactors = maxFactors;

		
	}


	public String getDescription() {
		return "The answer is the sum. Question: '4+2+7', Correct answer '13'";
	}



}
