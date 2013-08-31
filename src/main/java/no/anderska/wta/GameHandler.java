package no.anderska.wta;

import java.util.List;

public interface GameHandler {

    public AnswerStatus answer(String playerid,List<String> answers);

    public QuestionList questions(String playerid,String categoryid);
}
