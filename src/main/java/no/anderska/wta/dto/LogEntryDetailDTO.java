package no.anderska.wta.dto;

import java.util.List;

public class LogEntryDetailDTO {
    private String answer;
    private String expected;
    private String question;
    private String status;

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
