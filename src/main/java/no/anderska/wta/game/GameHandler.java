package no.anderska.wta.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.anderska.wta.AnswerStatus;
import no.anderska.wta.GameHandlerPlayerInterface;
import no.anderska.wta.PlayerHandlerMemory;
import no.anderska.wta.QuestionList;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoriesAnsweredDTO;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.questions.QuestionGeneratorFactory;
import no.anderska.wta.servlet.PlayerHandler;

public class GameHandler implements GameHandlerPlayerInterface, StatusGiver, AdminHandler {
    private final PlayerHandler playerHandler = new PlayerHandlerMemory();
    private Map<String,QuestionGenerator> activeGenerators = new HashMap<>();
    private Map<String,QuestionSet> askedQuestions = new HashMap<>();
    private final Map<String,String> takenCategories = new HashMap<>();
    private final Map<String,Set<String>> answered = new HashMap<>();
    private boolean looserBonus = false;
    private final Map<String,Set<String>> categoryPointAwarded = new HashMap<>();

    private final Integer lockHolder = new Integer(42);
    private GameLogger gameLogger;
    private QuestionGeneratorFactory questionFactory;

    public void setGameLogger(GameLogger gameLogger) {
        this.gameLogger = gameLogger;
    }

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
            int points = 0;
            if (pointAwarded != PointAwarded.NONE) {
                points = questionSet.getGenerator().points();
                if (pointAwarded == PointAwarded.HALF) {
                    points = points / 2;
                }

            }
            playerHandler.addPoints(playerid, points);
            gameLogger.answer(playerid,questionSet.getCategoryName(),answers,questionSet.expectedAnswers(),questionSet.questions(),AnswerStatus.OK,points);

        } else {
            gameLogger.answer(playerid,questionSet.getCategoryName(),answers,questionSet.expectedAnswers(),questionSet.questions(),answerStatus,0);
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
        QuestionGenerator generator = activeGenerators.get(categoryid);
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

    public void setActiveGenerators(Map<String, QuestionGenerator> generators) {
        this.activeGenerators = generators;
    }

    public void setAskedQuestions(Map<String, QuestionSet> askedQuestions) {
        this.askedQuestions = askedQuestions;
    }

    @Override
    public List<CategoryDTO> categoryStatus() {
        List<CategoryDTO> result = new ArrayList<>();
        for (String category : activeGenerators.keySet()) {
            result.add(categoryStatus(category));
        }
        return result;
    }

    CategoryDTO categoryStatus(String category) {
        QuestionGenerator generator = activeGenerators.get(category);
        String answeredById = takenCategories.get(category);
        String answeredBy = answeredById != null ? playerHandler.playerName(answeredById) : null;
        return new CategoryDTO(category, generator.description(), generator.points(), answeredBy);
    }

    @Override
    public String restartGame() {
        synchronized (lockHolder) {
            askedQuestions.clear();
            takenCategories.clear();
            playerHandler.clear();
            categoryPointAwarded.clear();
            gameLogger.clear();
        }

        return null;
    }

    @Override
    public String resetCategories() {
        synchronized (lockHolder) {
            takenCategories.clear();
            categoryPointAwarded.clear();
        }
        return null;
    }

    @Override
    public String editCategories(List<String> generatorNames) {
        synchronized (lockHolder) {
            for (String category : generatorNames) {
                if (!activeGenerators.containsKey(category)) {
                    addQuestionCategory(category, questionFactory.createGenerator(category));
                }
            }
            for (String entry : new ArrayList<>(activeGenerators.keySet())) {
                if (!generatorNames.contains(entry)) {
                    removeQuestionCategory(entry);
                }
            }
        }
        return "Generators updated";
    }

    public QuestionGenerator addQuestionCategory(String category, QuestionGenerator generator) {
        return activeGenerators.put(category, generator);
    }

    private void removeQuestionCategory(String entry) {
        activeGenerators.remove(entry);
        takenCategories.remove(entry);
        categoryPointAwarded.remove(entry);
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
    public String toggleLoserBonus() {

        synchronized (lockHolder) {
            looserBonus = !looserBonus;
            categoryPointAwarded.clear();
        }
        return "Looser bonus is now " + looserBonus;
    }

    public void setQuestionGeneratorFactory(QuestionGeneratorFactory questionFactory) {
        this.questionFactory = questionFactory;
        for (String category : questionFactory.getAllCategoryNames()) {
            addQuestionCategory(category, questionFactory.createGenerator(category));
        }
    }
}
