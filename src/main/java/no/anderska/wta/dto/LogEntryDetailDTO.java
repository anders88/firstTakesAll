package no.anderska.wta.dto;

import java.util.List;

public class LogEntryDetailDTO {
    private final List<String> answer;
    private final List<String> expected;
    private final List<String> questions;

    public LogEntryDetailDTO(List<String> answer, List<String> expected, List<String> questions) {
        this.answer = answer;
        this.expected = expected;
        this.questions = questions;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public List<String> getExpected() {
        return expected;
    }

    public List<String> getQuestions() {
        return questions;
    }
}
