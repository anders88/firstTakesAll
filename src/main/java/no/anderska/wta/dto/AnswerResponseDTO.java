package no.anderska.wta.dto;


public class AnswerResponseDTO {
    private final AnswerStatus answerStatus;
    private String description;

    public static enum AnswerStatus {
        OK,WRONG,MISSING_PARAMETER;
    }
    
    public static AnswerResponseDTO create(AnswerStatus answerStatus) {
        return new AnswerResponseDTO(answerStatus);
    }
    
    private AnswerResponseDTO(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AnswerResponseDTO)) {
            return false;
        }
        return this.answerStatus == ((AnswerResponseDTO) obj).answerStatus;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String getDescription() {
        return description;
    }
    
    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }
    
    public AnswerResponseDTO withDescription(String description) {
        this.description = description;
        return this;
    }
}
