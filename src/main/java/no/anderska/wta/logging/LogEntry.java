package no.anderska.wta.logging;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.servlet.PlayerHandler;
import org.apache.commons.lang.mutable.MutableLong;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class LogEntry {
    private static MutableLong givenId = new MutableLong(0);

    private List<String> answer;
    private List<String> expected;
    private final long id;
    private final DateTime time;
    private String playerId;
    private AnswerStatus answerStatus;
    private String message;
    private int points;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss.SSS");

    private LogEntry(String playerId, AnswerStatus answerStatus) {
        this.playerId = playerId;
        this.answerStatus = answerStatus;
        synchronized (givenId) {
            givenId.increment();
            this.id = givenId.longValue();
        }
        this.time = new DateTime();
    }

    public static LogEntry error(String playerid, String message) {
        LogEntry entry = new LogEntry(playerid,AnswerStatus.ERROR);
        entry.message = message;
        return entry;
    }

    public static LogEntry answer(String playerid, List<String> answer, List<String> expected, AnswerStatus answerStatus, int points) {
        LogEntry entry = new LogEntry(playerid, answerStatus);
        entry.expected = expected;
        entry.answer = answer;
        entry.points = points;
        return entry;
    }

    public AnswerLogEntryDTO logEntry(PlayerHandler playerHandler) {
        AnswerLogEntryDTO dto = new AnswerLogEntryDTO(
                id,
                playerId,
                playerHandler.playerName(playerId),
                dateTimeFormatter.print(time),
                answerStatus,
                message,
                points
        );
        return dto;
    }
}