package no.anderska.wta.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.Question;

public class AdditionQuestionGenerator implements QuestionGenerator {

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
        return 10;
    }



    private int numberOfQuestions;
    private final int maxFactors;

    private AdditionQuestionGenerator(int numberOfQuestions, int maxFactors) {
        if (maxFactors < 2) {
            if (numberOfQuestions <= 0) {
                throw new IllegalArgumentException("Number of questions must be positive");
            }
            throw new IllegalArgumentException("Number of factors must be 2 or greater");
        }
        this.numberOfQuestions = numberOfQuestions;
        this.maxFactors = maxFactors;

		
	}

    public AdditionQuestionGenerator() {
        this(25,4);
    }

    public String getDescription() {
		return "The answer is the sum. Question: '4+2+7', Correct answer '13'";
	}



}
