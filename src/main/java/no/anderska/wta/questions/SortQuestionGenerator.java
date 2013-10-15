package no.anderska.wta.questions;

import static no.anderska.wta.questions.AbstractQuestionGenerator.random;
import static no.anderska.wta.questions.AbstractQuestionGenerator.randomWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.game.QuestionSet;

public class SortQuestionGenerator implements QuestionGenerator {

    @Override
    public String description() {
        return "Sort the values. E.g. d, a, c, b => a, b, c, d";
    }

    @Override
    public int points() {
        return 25;
    }

    @Override
    public QuestionSet generateQuestionSet(String playerid, String categoryid) {
        List<String> question = generateQuestion();
        return new QuestionSet(question, generateAnswers(question), this, categoryid);
    }

    private List<String> generateQuestion() {
        return random.nextInt(10) == 0 ? generateStringList() : generateNumberList();
    }

    private List<String> generateNumberList() {
        List<String> question = new ArrayList<>();
        for (int i = 0; i < random.nextInt(100)+10; i++) {
            question.add(String.valueOf(random.nextInt(1000)));
        }
        return question;
    }

    private List<String> generateStringList() {
        List<String> question = new ArrayList<>();
        for (int i = 0; i < random.nextInt(100)+10; i++) {
            question.add(randomWord());
        }
        return question;
    }

    List<String> generateAnswers(List<String> question) {
        List<String> answer = new ArrayList<>(question);
        Collections.sort(answer);
        return answer;
    }


}
