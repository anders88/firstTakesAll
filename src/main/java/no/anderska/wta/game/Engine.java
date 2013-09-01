package no.anderska.wta.game;

import java.util.List;

public interface Engine {
    public List<Question> generateQuestions(String playerid);
    public String description();
    public int points();
}
