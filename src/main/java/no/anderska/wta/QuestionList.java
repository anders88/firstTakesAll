package no.anderska.wta;

import java.util.List;

public class QuestionList {
    private List<String> questions;
    private String errormessage;

    public static QuestionList create(List<String> questions)  {
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
}
