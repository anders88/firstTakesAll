package no.anderska.wta.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import no.anderska.wta.Question;
import no.anderska.wta.QuestionCategoryEngine;

public class AdditionEngine implements QuestionCategoryEngine {

	private static class SumQuestion {
		private List<Integer> factors;

		public SumQuestion(List<Integer> factors) {
			this.factors = factors;
		}
		
		public List<Integer> getFactors() {
			return factors;
		}
		
	}
	
	private Map<Integer, SumQuestion> questions = new HashMap<Integer, AdditionEngine.SumQuestion>();
	
	public AdditionEngine(int numberOfQuestions, int maxFactors) {
		Random random = new Random();
		if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Number of questions must be positive");
		}
		if (maxFactors < 2) {
			throw new IllegalArgumentException("Number of factors must be 2 or greater");
		}
		for (int i=1;i<=numberOfQuestions;i++) {
			List<Integer> factors = new ArrayList<>();
			
			int numFactors = (maxFactors == 2) ? 2 : random.nextInt(maxFactors-1)+2;
			for (int j=0;j<numFactors;j++) {
				factors.add(random.nextInt(999)+1);
			}
			questions.put(i, new SumQuestion(factors));
		}
		
	}

	@Override
	public boolean checkAnswer(String gamerId, int questionId, String answer) {
		SumQuestion sumQuestion = questions.get(questionId);
		if (sumQuestion == null) {
			return false;
		}
		int sum=0;
		for (Integer value : sumQuestion.getFactors()) {
			sum+=value;
		}
		String sumstr = "" + sum;
		return sumstr.equals(answer);
	}

	@Override
	public String getDescription() {
		return "The answer is the sum. Question: '4+2+7', Correct answer '13'";
	}

	@Override
	public List<Question> myQuestions() {
		List<Question> questionList = new ArrayList<>();
		for (Entry<Integer, SumQuestion> entry : questions.entrySet()) {
			StringBuilder text = new StringBuilder();
			List<Integer> factors = entry.getValue().getFactors();
			for (Integer value : factors) {
				if (!text.toString().isEmpty()) {
					text.append("+");
				}
				text.append(value);
			}
			questionList.add(new Question(entry.getKey(), text.toString(), 2));
		}
		return questionList;
	}

}
