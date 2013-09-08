package no.anderska.wta.game;

import no.anderska.wta.*;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;

import java.util.*;

public class GameHandler implements GameHandlerPlayerInterface, StatusGiver, AdminHandler {
    private PlayerHandler playerHandler;
    private Map<String,Engine> engines;
    private Map<String,QuestionSet> askedQuestions = new HashMap<>();
    private Map<String,String> takenCategories = new HashMap<>();
    private Map<String,Set<String>> answered = new HashMap<>();
    private boolean looserBonus = false;
    private Map<String,Set<String>> categoryPointAwarded = new HashMap<>();

    private Integer lockHolder = new Integer(42);

    private static enum PointAwarded {
        FULL,HALF,NONE;
    }

    @Override
    public AnswerStatus answer(String playerid, List<String> answers) {
        QuestionSet questionSet = askedQuestions.get(playerid);
        if (questionSet == null) {
            return AnswerStatus.ERROR;
        }
        AnswerStatus answerStatus = questionSet.validateAnswer(answers);
        if (answerStatus == AnswerStatus.OK) {
            PointAwarded pointAwarded = claimCategory(questionSet.getCategoryName(),playerid);
            if (pointAwarded != PointAwarded.NONE) {
                int points = questionSet.getEngine().points();
                if (pointAwarded == PointAwarded.HALF) {
                    points = points / 2;
                }
                playerHandler.addPoints(playerid, points);
            }
        }
        return answerStatus;
    }

    private PointAwarded claimCategory(String categoryName,String playerid) {
        synchronized (lockHolder) {
            Set<String> catAnswered = answered.get(playerid);
            if (catAnswered == null) {
                catAnswered = new HashSet<>();
                answered.put(playerid,catAnswered);
            }
            catAnswered.add(categoryName);

            if (takenCategories.containsKey(categoryName)) {
                if (!looserBonus || takenCategories.get(categoryName).equals(playerid)) {
                    return PointAwarded.NONE;
                }
                Set<String> playersAnswered = categoryPointAwarded.get(categoryName);
                if (playersAnswered == null) {
                    playersAnswered = new HashSet<>();
                    categoryPointAwarded.put(categoryName,playersAnswered);
                }
                if (playersAnswered.contains(playerid)) {
                    return PointAwarded.NONE;
                }
                playersAnswered.add(playerid);
                return PointAwarded.HALF;
            } else {
                takenCategories.put(categoryName,playerid);
                return PointAwarded.FULL;
            }
        }
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

        synchronized (lockHolder) {
            askedQuestions.put(playerid,new QuestionSet(questions,engine, categoryid));
        }

        return QuestionList.create(questionList);
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public void setEngines(Map<String, Engine> engines) {
        this.engines = engines;
    }

    public void setAskedQuestions(Map<String, QuestionSet> askedQuestions) {
        this.askedQuestions = askedQuestions;
    }

    @Override
    public List<CategoryDTO> catergoryStatus() {
        List<CategoryDTO> result = new ArrayList<>();
        for (Map.Entry<String, Engine> entry : engines.entrySet()) {

            String answeredById = takenCategories.get(entry.getKey());
            String answeredBy = answeredById != null ? playerHandler.playerName(answeredById) : null;
            CategoryDTO categoryDTO = new CategoryDTO(entry.getKey(), entry.getValue().description(), entry.getValue().points(), answeredBy);
            result.add(categoryDTO);
        }
        return result;
    }

    @Override
    public String restartGame(String password) {
        if (!checkPassword(password)) {
            return "Wrong password";
        }
        synchronized (lockHolder) {
            askedQuestions.clear();
            takenCategories.clear();
            playerHandler.clear();
            categoryPointAwarded.clear();
        }

        return null;
    }

    private boolean checkPassword(String password) {
        return "secret".equals(password);
    }

    @Override
    public String resetCategories(String password) {
        if (!checkPassword(password)) {
            return "Wrong password";
        }
        synchronized (lockHolder) {
            takenCategories.clear();
            categoryPointAwarded.clear();
        }
        return null;
    }

    @Override
    public String editCategories(String password, String[] engineNames) {
        if (!checkPassword(password)) {
            return "Wrong password";
        }
        synchronized (lockHolder) {
            Map<String, Engine> newEngines = SetupGame.instance().createEngines(new HashSet<>(Arrays.asList(engineNames)));

            for (Map.Entry<String,Engine> entry : newEngines.entrySet()) {
                if (engines.containsKey(entry.getKey())) {
                    continue;
                }
                engines.put(entry.getKey(),entry.getValue());
            }

            Set<String> toRemove = new HashSet<>();

            for (Map.Entry<String,Engine> entry : engines.entrySet()) {
                if (newEngines.containsKey(entry.getKey())) {
                    continue;
                }
                toRemove.add(entry.getKey());
            }

            for (String remove : toRemove) {
                engines.remove(remove);
                takenCategories.remove(remove);
                categoryPointAwarded.remove(remove);
            }
        }

        return "Engines updated";
    }

    @Override
    public List<CategoriesAnsweredDTO> categoriesAnswered() {
        List<CategoriesAnsweredDTO> result = new ArrayList<>();
        for (Map.Entry<String,Set<String>> entry : answered.entrySet()) {
            String playerName = playerHandler.playerName(entry.getKey());
            ArrayList<String> categories = new ArrayList<>(entry.getValue());

            result.add(new CategoriesAnsweredDTO(playerName, categories));
        }
        return result;
    }

    @Override
    public String toggleLoserBonus(String password) {
        if (!checkPassword(password)) {
            return "Wrong password";
        }

        synchronized (lockHolder) {
            looserBonus = !looserBonus;
            categoryPointAwarded.clear();
        }
        return "Looser bonus is now " + looserBonus;
    }
}
