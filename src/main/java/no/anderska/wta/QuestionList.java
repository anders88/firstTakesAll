package no.anderska.wta;

import java.util.List;

public class QuestionList {
    private List<String> questions;
    private String errormessage;

    public QuestionList(List<String> questions) {
        this.questions = questions;
    }

    public  QuestionList(String errormessage) {
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
