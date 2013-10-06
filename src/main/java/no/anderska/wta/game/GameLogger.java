package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;

import java.util.List;

public interface GameLogger {
    public void errorAnswer(String playerid,String nesssage);
    public void answer(String playerid,String category,List<String> answer,List<String> expected,List<String> questions,AnswerStatus answerStatus,int points);
    public void error(String receivedMessage);
    public void clear();
}
