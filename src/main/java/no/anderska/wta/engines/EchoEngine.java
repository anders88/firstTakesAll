package no.anderska.wta.engines;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EchoEngine {
	
	private Map<Integer, String> questions = new HashMap<>();
	
	public EchoEngine(int numberOfQuestions) {
		if (numberOfQuestions <= 0) {
			throw new IllegalArgumentException("Number of questions must be positive");
		}
		for (int i=1;i<=numberOfQuestions;i++) {
			questions.put(i, UUID.randomUUID().toString());
		}
	}


	public String getDescription() {
		return "The answer is the same as the question. Question: 'hello', Correct answer 'hello'";
	}


}
