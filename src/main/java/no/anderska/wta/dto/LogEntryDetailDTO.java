package no.anderska.wta.dto;


public class LogEntryDetailDTO {
    private final String answer;
    private final String expected;
    private final String question;
    private final String status;

    public LogEntryDetailDTO(String answer, String expected, String question) {
        this.answer = answer;
        this.expected = expected;
        this.question = question;
        this.status = expected.equals(answer) ? "OK" : "WRONG";
    }

    public String getAnswer() {
        return answer;
    }

    public String getExpected() {
        return expected;
    }

    public String getQuestion() {
        return question;
    }

    public String getStatus() {
        return status;
    }
}
