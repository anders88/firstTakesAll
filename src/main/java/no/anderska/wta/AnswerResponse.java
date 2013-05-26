package no.anderska.wta;


public class AnswerResponse {
    private final AnswerStatus answerStatus;
    private String description;

    public static enum AnswerStatus {
        OK,WRONG,MISSING_PARAMETER;
    }
    
    public static AnswerResponse create(AnswerStatus answerStatus) {
        return new AnswerResponse(answerStatus);
    }
    
    private AnswerResponse(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AnswerResponse)) {
            return false;
        }
        return this.answerStatus == ((AnswerResponse) obj).answerStatus;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String getDescription() {
        return description;
    }
    
    public AnswerResponse withDescription(String description) {
        this.description = description;
        return this;
    }
}
