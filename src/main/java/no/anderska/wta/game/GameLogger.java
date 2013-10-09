package no.anderska.wta.game;

import java.util.List;

import no.anderska.wta.AnswerStatus;

public interface GameLogger {
    void answer(String playerid,String category,List<String> answer,List<String> expected,List<String> questions,AnswerStatus answerStatus,int points);
    void error(String receivedMessage);
    void clear();
}
