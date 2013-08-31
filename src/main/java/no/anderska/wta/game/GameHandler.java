package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.QuestionList;
import no.anderska.wta.servlet.PlayerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameHandler implements GameHandlerPlayerInterface {
    private PlayerHandler playerHandler;
    private Map<String,Engine> engines;

    @Override
    public AnswerStatus answer(String playerid, List<String> answers) {
        return null;
    }

    @Override
    public QuestionList questions(String playerid, String categoryid) {
        if (!playerHandler.playerPlaying(playerid)) {
            return QuestionList.error("Unknown player '" + playerid + "'");
        }
        Engine engine = engines.get(categoryid);
        if (engine == null) {
            return QuestionList.error("Unknown category '" + categoryid + "'");
        }
        List<Question> questions = engine.generateQuestions(playerid);
        List<String> questionList = new ArrayList<>();

        for (Question question : questions) {
            questionList.add(question.getQuestion());
        }

        return QuestionList.create(questionList);
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void setEngines(Map<String, Engine> engines) {
        this.engines = engines;
    }
}
