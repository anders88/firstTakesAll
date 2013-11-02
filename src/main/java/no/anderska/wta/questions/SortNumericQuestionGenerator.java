package no.anderska.wta.questions;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.QuestionSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static no.anderska.wta.questions.AbstractQuestionGenerator.random;

public class SortNumericQuestionGenerator implements QuestionGenerator {

    @Override
    public String description() {
        return "Sort the values as numbers. E.g. 10, 9, 8 => 8, 9, 10";
    }

    @Override
    public QuestionSet generateQuestionSet(String playerid, String categoryid) {
        List<Integer> question = generateQuestion();
        return new QuestionSet(question, generateAnswers(question), this, categoryid);
    }

    private List<Integer> generateQuestion() {
        List<Integer> question = new ArrayList<>();
        for (int i = 0; i < random.nextInt(100)+10; i++) {
            question.add(random.nextInt(1000));
        }
        return question;
    }

    List<Integer> generateAnswers(List<Integer> question) {
        List<Integer> answer = new ArrayList<>(question);
        Collections.sort(answer);
        return answer;
    }


}
