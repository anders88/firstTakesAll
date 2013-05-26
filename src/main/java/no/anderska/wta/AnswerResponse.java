package no.anderska.wta;

public class AnswerResponse {
    private final AnswerStatus answerStatus;

    private static enum AnswerStatus {
        OK;
    }
    
    public static AnswerResponse ok() {
        return new AnswerResponse(AnswerStatus.OK);
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
}
