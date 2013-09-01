package no.anderska.wta.game;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.QuestionList;
import no.anderska.wta.servlet.PlayerHandler;

import java.util.*;

public class GameHandler implements GameHandlerPlayerInterface {
    private PlayerHandler playerHandler;
    private Map<String,Engine> engines;
    private Map<String,QuestionSet> askedQuestions = new HashMap<>();
    private Set<String> remainingCategories;

    @Override
    public AnswerStatus answer(String playerid, List<String> answers) {
        QuestionSet questionSet = askedQuestions.get(playerid);
        if (questionSet == null) {
            return AnswerStatus.ERROR;
        }
        AnswerStatus answerStatus = questionSet.validateAnswer(answers);
        if (answerStatus == AnswerStatus.OK) {
            boolean gotpoint = claimCategory(questionSet.getCategoryName());
            if (gotpoint) {
                playerHandler.addPoints(playerid,questionSet.getEngine().points());
            }
        }
        return answerStatus;
    }

    private synchronized boolean claimCategory(String categoryName) {
        return remainingCategories.remove(categoryName);
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

        askedQuestions.put(playerid,new QuestionSet(questions,engine, categoryid));

        return QuestionList.create(questionList);
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void setEngines(Map<String, Engine> engines) {
        this.engines = engines;
        this.remainingCategories = new HashSet<>(engines.keySet());
    }

    public void setAskedQuestions(Map<String, QuestionSet> askedQuestions) {
        this.askedQuestions = askedQuestions;
    }
}
