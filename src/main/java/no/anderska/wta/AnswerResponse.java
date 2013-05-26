package no.anderska.wta;

public class AnswerResponse {
    @SuppressWarnings("unused")
    private final String answerStatus;

    private static enum AnswerStatus {
        OK("OK");

        private final String textValue;
        private AnswerStatus(String textValue) {
            this.textValue = textValue;
        }
        
        public String getTextValue() {
            return textValue;
        }
    }
    
    public static AnswerResponse ok() {
        return new AnswerResponse(AnswerStatus.OK);
    }
    
    private AnswerResponse(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus.getTextValue();
        
    }
}
