package no.anderska.wta.logging;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.game.GameLogger;

import java.util.List;

public class MemoryGameLogger implements GameLogger {
    @Override
    public void errorAnswer(String playerid, String nesssage) {
    }

    @Override
    public void answer(String playerid, List<String> answer, List<String> expected, AnswerStatus answerStatus, int points) {
    }

    @Override
    public void error(String receivedMessage) {
    }
}
