package no.anderska.wta.game;


public interface QuestionGenerator {
    public String description();
    public int points();
    public QuestionSet generateQuestionSet(String playerid, String categoryid);
}
