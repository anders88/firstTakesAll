package no.anderska.wta.engines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import no.anderska.wta.Question;
import no.anderska.wta.QuestionCategoryEngine;

public class EchoEngine implements QuestionCategoryEngine {
	
	private Map<Integer, String> questions = new HashMap<>();
	
	public EchoEngine(int numberOfQuestions) {
		if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Number of questions must be positive");
		}
		for (int i=1;i<=numberOfQuestions;i++) {
			questions.put(i, UUID.randomUUID().toString());
		}
	}

	@Override
	public boolean checkAnswer(String gamerId, int questionId, String answer) {
		String fact = questions.get(questionId);
		if (fact == null) {
			return false;
		}
		return fact.equals(answer);
	}

	@Override
	public String getDescription() {
		return "The answer is the same as the question. Question: 'hello', Correct answer 'hello'";
	}

	@Override
	public List<Question> myQuestions() {
		List<Question> questionList = new ArrayList<>();
		for (Entry<Integer, String> entry : questions.entrySet()) {
			questionList.add(new Question(entry.getKey(), entry.getValue(), 1));
		}
		return questionList;
	}

}
