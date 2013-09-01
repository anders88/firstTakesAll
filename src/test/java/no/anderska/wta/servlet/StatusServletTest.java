package no.anderska.wta.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.GameStatusDTO;
import no.anderska.wta.dto.PlayerDTO;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        StatusServlet servlet = new StatusServlet();

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("GET");

        HttpServletResponse resp = mock(HttpServletResponse.class);
        StringWriter htmlSource = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));

        StatusGiver statusGiver = mock(StatusGiver.class);
        when(statusGiver.catergoryStatus()).thenReturn(Arrays.asList(new CategoryDTO("1","one",1,null),new CategoryDTO("2","two",2,"Someone")));
        servlet.setStatusGiver(statusGiver);

        PlayerHandler playerHandler = mock(PlayerHandler.class);
        when(playerHandler.playerList()).thenReturn(Arrays.asList(new PlayerDTO("Player one",10),new PlayerDTO("Player two",15)));
        servlet.setPlayerHandler(playerHandler);

        servlet.service(req, resp);

        Gson gson = new Gson();
        GameStatusDTO gameStatusDTO = gson.fromJson(htmlSource.toString(), GameStatusDTO.class);

        List<CategoryDTO> categories = gameStatusDTO.getCategories();

        assertThat(categories).hasSize(2);

        assertThat(categories.get(0).getId()).isEqualTo("1");
        assertThat(categories.get(0).getAnsweredBy()).isNull();
        assertThat(categories.get(1).getId()).isEqualTo("2");
        assertThat(categories.get(1).getAnsweredBy()).isEqualTo("Someone");

        List<PlayerDTO> players = gameStatusDTO.getPlayers();

        assertThat(players).hasSize(2);


    }


}
