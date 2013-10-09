package no.anderska.wta.logging;

import java.util.ArrayList;
import java.util.List;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.dto.LogEntryDetailDTO;
import no.anderska.wta.servlet.PlayerHandler;

import org.apache.commons.lang.mutable.MutableLong;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

class LogEntry {
    private static MutableLong givenId = new MutableLong(0);

    private List<String> givenAnswers;
    private List<String> expected;
    private List<String> questions;
    private final long id;
    private final DateTime time;
    private final String playerId;
    private final AnswerStatus answerStatus;
    private String message;
    private int points;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss.SSS");
    private String category;

    private LogEntry(String playerId, AnswerStatus answerStatus) {
        this.playerId = playerId;
        this.answerStatus = answerStatus;
        synchronized (givenId) {
            givenId.increment();
            this.id = givenId.longValue();
        }
        this.time = new DateTime();
    }

    static LogEntry answer(String playerid, String category,List<String> answer, List<String> expected, List<String> questions, AnswerStatus answerStatus, int points) {
        LogEntry entry = new LogEntry(playerid, answerStatus);
        entry.expected = expected;
        entry.givenAnswers = answer;
        entry.points = points;
        entry.questions = questions;
        entry.category = category;
        return entry;
    }

    AnswerLogEntryDTO logEntry(PlayerHandler playerHandler) {
        AnswerLogEntryDTO dto = new AnswerLogEntryDTO(
                id,
                playerId,
                playerHandler.playerName(playerId),
                dateTimeFormatter.print(time),
                category,
                answerStatus,
                message,
                points
        );
        return dto;
    }

    List<LogEntryDetailDTO> detail() {
        List<LogEntryDetailDTO> result = new ArrayList<>();

        for (int i=0;i<questions.size();i++) {
           String givenAnswer = i < givenAnswers.size() ? givenAnswers.get(i) : "";
           result.add(new LogEntryDetailDTO(givenAnswer,expected.get(i),questions.get(i)));
        }

        return result;
    }

    public long getId() {
        return id;
    }
}
