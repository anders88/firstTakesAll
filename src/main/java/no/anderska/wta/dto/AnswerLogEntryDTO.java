package no.anderska.wta.dto;

import no.anderska.wta.AnswerStatus;

public class AnswerLogEntryDTO {


    private final long id;
    private final String playerId;
    private final String playerName;
    private final String time;
    private final AnswerStatus answerStatus;
    private final String message;
    private final int points;
    private final String category;

    public AnswerLogEntryDTO(long id, String playerId, String playerName, String time, String category, AnswerStatus answerStatus, String message, int points) {
        this.id = id;
        this.playerId = playerId;
        this.playerName = playerName;
        this.time = time;
        this.answerStatus = answerStatus;
        this.message = message;
        this.points = points;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTime() {
        return time;
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public String getMessage() {
        return message;
    }

    public int getPoints() {
        return points;
    }

    public String getCategory() {
        return category;
    }
}
