package no.anderska.wta.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatusServletTest {



    @Test
    public void shouldGiveListOfCategories() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter htmlSource = new StringWriter();
        StatusServlet servlet = new StatusServlet();

        when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
        when(req.getMethod()).thenReturn("GET");
        StatusGiver statusGiver = mock(StatusGiver.class);
        when(statusGiver.catergoryStatus()).thenReturn(Arrays.asList(new CategoryDTO("1","one",1,null),new CategoryDTO("2","two",2,"Someone")));

        servlet.setStatusGiver(statusGiver);

        servlet.service(req, resp);

        Gson gson = new Gson();
        List<CategoryDTO> categories = gson.fromJson(htmlSource.toString(), new TypeToken<List<CategoryDTO>>() {}.getType());

        assertThat(categories).hasSize(2);

        assertThat(categories.get(0).getId()).isEqualTo("1");
        assertThat(categories.get(0).getAnsweredBy()).isNull();
        assertThat(categories.get(1).getId()).isEqualTo("2");
        assertThat(categories.get(1).getAnsweredBy()).isEqualTo("Someone");


    }


}
