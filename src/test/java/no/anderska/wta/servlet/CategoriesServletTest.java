package no.anderska.wta.servlet;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.game.GameHandler;
import no.anderska.wta.questions.DummyQuestionGenerator;
import no.anderska.wta.questions.QuestionGeneratorFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

public class CategoriesServletTest {

    private final GameHandler gameHandler = new GameHandler();
    private final QuestionGeneratorFactory questionFactory = new QuestionGeneratorFactory();

    @Test
    public void shouldShowCategories() throws IOException, JSONException {
        questionFactory.put("One", DummyQuestionGenerator.class,5);
        questionFactory.put("Two", DummyQuestionGenerator.class,10);
        gameHandler.setQuestionGeneratorFactory(questionFactory);

        gameHandler.editCategories(Arrays.asList("Two"));

        CategoriesServlet servlet = new CategoriesServlet();
        servlet.setGameHandler(gameHandler);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter out = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(out));

        servlet.doGet(mock(HttpServletRequest.class), response);

        JSONArray resultArray = new JSONArray(out.toString());

        assertThat(resultArray.length()).isEqualTo(2);
        assertThat(resultArray.getJSONObject(0).getString("name")).isEqualTo("One");
        assertThat(resultArray.getJSONObject(0).getBoolean("active")).isFalse();
        assertThat(resultArray.getJSONObject(0).getString("description"))
            .isEqualTo(new DummyQuestionGenerator().description());
        assertThat(resultArray.getJSONObject(1).getString("name")).isEqualTo("Two");
        assertThat(resultArray.getJSONObject(1).getBoolean("active")).isTrue();
    }


}
