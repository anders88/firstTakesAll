package no.anderska.wta.servlet;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.dto.QuestionDTO;
import no.anderska.wta.dto.QuestionCategoryDTO;
import no.anderska.wta.servlet.GameServlet;
import no.anderska.wta.servlet.QuestionChecker;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameServletDisplayTest {
    private GameServlet servlet = new GameServlet();
    private QuestionChecker questionChecker = mock(QuestionChecker.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private StringWriter jsonResponse = new StringWriter();

    @Test
    public void shouldDisplayQuestionCategories() throws Exception {
    	when(req.getPathInfo()).thenReturn("/");
        when(questionChecker.allCategories()).thenReturn(Arrays.asList(QuestionCategoryDTO.create(1, "one"),QuestionCategoryDTO.create(2, "two")));

        servlet.service(req, resp);
        
        Gson gson = new Gson();
        
        List<QuestionCategoryDTO> categories = gson.fromJson(jsonResponse.toString(), new TypeToken<List<QuestionCategoryDTO>>() {}.getType());
        
        assertThat(categories).hasSize(2);
        
        assertThat(categories.get(0).getDescription()).isEqualTo("one");
    }
    
    @Test
    public void shouldDisplayQuestionsForACategory() throws Exception {
        when(req.getPathInfo()).thenReturn("/category");
        when(req.getParameter("id")).thenReturn("2");
        
        QuestionDTO expected = QuestionDTO.factory().withId(1).withText("What is the meaning of life?").withPoint(42).withAnswered(false).create();
        
        when(questionChecker.listCategory(anyInt())).thenReturn(Arrays.asList(expected));
        
        servlet.service(req, resp);
        
        verify(questionChecker).listCategory(2);
        
        
        Gson gson = new Gson();
        
        List<QuestionDTO> questions = gson.fromJson(jsonResponse.toString(), new TypeToken<List<QuestionDTO>>() {}.getType());
        
        assertThat(questions).hasSize(1);
        
        QuestionDTO actual = questions.get(0);
        
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getText()).isEqualTo(expected.getText());
        assertThat(actual.getPoint()).isEqualTo(expected.getPoint());
        assertThat(actual.isAnswered()).isEqualTo(expected.isAnswered());
    }
    
    @Test
    public void shouldGiveErrorWhenMissingCategoryId() throws Exception {
        when(req.getPathInfo()).thenReturn("/category");
        
        servlet.service(req, resp);
        
        verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST,"Missing parameter id");
        verify(questionChecker,never()).listCategory(anyInt());
    }
    
    @Test
    public void shouldGiveErrorWhenIllegalCategoryId() throws Exception {
        when(req.getPathInfo()).thenReturn("/category");
        when(req.getParameter("id")).thenReturn("x");
        
        servlet.service(req, resp);
        
        verify(resp).sendError(HttpServletResponse.SC_BAD_REQUEST,"Illegal category id");
        verify(questionChecker,never()).listCategory(anyInt());
    }
    
    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(jsonResponse));
        servlet.setQuestionChecker(questionChecker);
        when(req.getMethod()).thenReturn("GET");
        
    }

}
