package no.anderska.wta;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.anderska.wta.dto.QuestionCategory;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class GameServletDisplayTest {
    private GameServlet servlet = new GameServlet();
    private QuestionChecker questionChecker = mock(QuestionChecker.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private StringWriter jsonResponse = new StringWriter();

    @Test
    public void shouldDisplayQuestionCategories() throws Exception {
        when(questionChecker.allCategories()).thenReturn(Arrays.asList(QuestionCategory.create(1L, "one"),QuestionCategory.create(2L, "two")));

        servlet.service(req, resp);
        
        Gson gson = new Gson();
        gson.fromJson(jsonResponse.toString(), List.class);
        
        
    }
    
    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(jsonResponse));
        servlet.setQuestionChecker(questionChecker);
        when(req.getMethod()).thenReturn("GET");
        
    }

}
