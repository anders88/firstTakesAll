package no.anderska.wta;

public class QuestionStatus {
    private final int myId;
    private final QuestionCategoryEngine engine;
    private Long answeredBy = null;
    private Question question;

    public QuestionStatus(int myId,Question question,QuestionCategoryEngine engine) {
        this.myId = myId;
        this.question = question;
        this.engine = engine;
    }

    public int getMyId() {
        return myId;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionCategoryEngine getEngine() {
        return engine;
    }
    
    public boolean isAvailible() {
        return (answeredBy == null);
    }
}
