package no.anderska.wta.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.PlayerHandlerMemory;
import no.anderska.wta.QuestionList;
import no.anderska.wta.SetupGame;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;

public class GameHandler implements GameHandlerPlayerInterface, StatusGiver, AdminHandler {
    private final PlayerHandler playerHandler = new PlayerHandlerMemory();
    private Map<String,QuestionGenerator> generators;
    private Map<String,QuestionSet> askedQuestions = new HashMap<>();
    private final Map<String,String> takenCategories = new HashMap<>();
    private final Map<String,Set<String>> answered = new HashMap<>();
    private boolean looserBonus = false;
    private final Map<String,Set<String>> categoryPointAwarded = new HashMap<>();

    private final Integer lockHolder = new Integer(42);

    private static enum PointAwarded {
        FULL,HALF,NONE;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
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
                int points = questionSet.getGenerator().points();
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
        QuestionGenerator generator = generators.get(categoryid);
        if (generator == null) {
            return QuestionList.error("Unknown category '" + categoryid + "'");
        }
        List<Question> questions = generator.generateQuestions(playerid);
        putQuestion(playerid, categoryid, generator, questions);
        return QuestionList.createQuestion(questions);
    }

    public void putQuestion(String playerid, String categoryid, QuestionGenerator generator, List<Question> questions) {
        synchronized (lockHolder) {
            askedQuestions.put(playerid,new QuestionSet(questions, generator, categoryid));
        }
    }

    public void setGenerators(Map<String, QuestionGenerator> generators) {
        this.generators = generators;
    }

    public void setAskedQuestions(Map<String, QuestionSet> askedQuestions) {
        this.askedQuestions = askedQuestions;
    }

    @Override
    public List<CategoryDTO> categoryStatus() {
        List<CategoryDTO> result = new ArrayList<>();
        for (Map.Entry<String, QuestionGenerator> entry : generators.entrySet()) {

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
    public String editCategories(String password, String[] generatorNames) {
        if (!checkPassword(password)) {
            return "Wrong password";
        }
        synchronized (lockHolder) {
            Map<String, QuestionGenerator> newGenerators = SetupGame.instance().createGenerators(new HashSet<>(Arrays.asList(generatorNames)));

            for (Map.Entry<String,QuestionGenerator> entry : newGenerators.entrySet()) {
                if (generators.containsKey(entry.getKey())) {
                    continue;
                }
                generators.put(entry.getKey(),entry.getValue());
            }

            Set<String> toRemove = new HashSet<>();

            for (Map.Entry<String,QuestionGenerator> entry : generators.entrySet()) {
                if (newGenerators.containsKey(entry.getKey())) {
                    continue;
                }
                toRemove.add(entry.getKey());
            }

            for (String remove : toRemove) {
                generators.remove(remove);
                takenCategories.remove(remove);
                categoryPointAwarded.remove(remove);
            }
        }

        return "Generators updated";
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
