package no.anderska.wta.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import no.anderska.wta.SetupGame;
import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.QuestionGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesServlet extends AbstractJSONArrayServlet {

    private GameHandler gameHandler;

    @Override
    protected JSONArray getJSONArrayResult(HttpServletRequest req) throws JSONException {
        JSONArray result = new JSONArray();

        for (String category : gameHandler.getQuestionFactory().getAllCategoryNames()) {
            QuestionGenerator generator = gameHandler.getQuestionFactory().createGenerator(category);
            result.put(createJSONCategory(category, generator));
        }

        return result;
    }

    private JSONObject createJSONCategory(String category, QuestionGenerator generator) throws JSONException {
        JSONObject categoryJSON = new JSONObject();
        categoryJSON.put("name", category);
        categoryJSON.put("description", generator.description());
        categoryJSON.put("active", gameHandler.hasCategory(category));
        return categoryJSON;
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void init() throws ServletException {
        setGameHandler(SetupGame.instance().getGameHandler());
    }
}
