package no.anderska.wta;

import java.util.ArrayList;
import java.util.List;

import no.anderska.wta.game.QuestionSet;

public class QuestionList {
    private List<String> questions;
    private String errormessage;

    private static QuestionList create(List<String> questions)  {
        return new QuestionList(questions);
    }

    public static QuestionList error(String errormessage) {
        return new QuestionList(errormessage);
    }

    private QuestionList(List<String> questions) {
        this.questions = questions;
    }

    private QuestionList(String errormessage) {
        this.errormessage = errormessage;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public boolean isOk() {
        return errormessage == null;
    }

    public static QuestionList createQuestion(QuestionSet questionsSet) {
        List<String> questionList = new ArrayList<>();
        for (String question : questionsSet.questions()) {
            questionList.add(question);
        }
        return create(questionList);
    }
}
