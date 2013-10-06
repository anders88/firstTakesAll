package no.anderska.wta.logging;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.dto.AnswerLogEntryDTO;
import no.anderska.wta.dto.LogEntryDetailDTO;
import no.anderska.wta.game.GameLogger;
import no.anderska.wta.servlet.PlayerHandler;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameLogger implements GameLogger, LogReader {
    private List<LogEntry> entries = new ArrayList<>();
    private PlayerHandler playerHandler;

    @Override
    public void errorAnswer(String playerid, String messsage) {
        LogEntry error = LogEntry.error(playerid, messsage);
        synchronized (entries) {
            entries.add(error);
        }
    }

    @Override
    public void answer(String playerid, String category, List<String> answer, List<String> expected, List<String> questions, AnswerStatus answerStatus, int points) {
        LogEntry entry = LogEntry.answer(playerid,category,answer,expected,questions,answerStatus,points);
        synchronized (entries) {
            entries.add(entry);
        }
    }

    @Override
    public void error(String receivedMessage) {
    }

    @Override
    public void clear() {
        synchronized (entries) {
            entries.clear();
        }
    }

    @Override
    public List<AnswerLogEntryDTO> getLogEntries() {
        synchronized (entries) {
            List<AnswerLogEntryDTO> result = new ArrayList<>();
            for (LogEntry entry : entries) {
                result.add(entry.logEntry(playerHandler));
            }
            return result;
        }
    }

    @Override
    public List<LogEntryDetailDTO> getDetail(long id) {
        synchronized (entries) {
            for (LogEntry entry : entries) {
                if (entry.getId() == id) {
                    return entry.detail();
                }
            }
        }
        return null;
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }
}
