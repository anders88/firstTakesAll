package no.anderska.wta;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import no.anderska.wta.dto.AnswerResponseDTO;
import no.anderska.wta.dto.QuestionDTO;
import no.anderska.wta.dto.QuestionCategoryDTO;
import no.anderska.wta.servlet.PlayerHandler;
import no.anderska.wta.servlet.QuestionChecker;

public class GameEngine implements QuestionChecker {

    private final PlayerHandler playerHandler;
    private final Map<Integer,QuestionCategoryEngine> engines;
    

    public GameEngine(List<QuestionCategoryEngine> categories, PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
        this.engines = initializeEngines(categories); 
    }

    private Map<Integer, QuestionCategoryEngine> initializeEngines(
            List<QuestionCategoryEngine> categories) {
        Map<Integer, QuestionCategoryEngine> result = new Hashtable<>();
        int num = 0;
        for (QuestionCategoryEngine questionCategoryEngine : categories) {
            result.put(++num, questionCategoryEngine);
        }
        return result;
    }

    @Override
    public AnswerResponseDTO checkAnswer(String gamerId, String questionId, String answer) {
        return null;
    }

    @Override
    public List<QuestionCategoryDTO> allCategories() {
        List<QuestionCategoryDTO> result = new ArrayList<>();
        for (Entry<Integer, QuestionCategoryEngine> entry : engines.entrySet()) {
            result.add(QuestionCategoryDTO.create(entry.getKey(),entry.getValue().getDescription()));
        }
        return result;
    }

    @Override
    public List<QuestionDTO> listCategory(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
