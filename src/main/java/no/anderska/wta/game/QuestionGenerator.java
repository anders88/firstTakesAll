package no.anderska.wta.game;


public interface QuestionGenerator {
    public String description();
    public QuestionSet generateQuestionSet(String playerid, String categoryid);
}
