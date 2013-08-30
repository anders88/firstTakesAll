package no.anderska.wta.dto;

import java.util.List;

public class PlayerAnswerDto {
    private String playerId;
    private List<String> answers;

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }


    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
