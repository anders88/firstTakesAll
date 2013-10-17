package no.anderska.wta.servlet;

import no.anderska.wta.StatusGiver;
import no.anderska.wta.dto.CategoryDTO;
import no.anderska.wta.dto.PlayerDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatusServletTest {

    private final StatusServlet servlet = new StatusServlet();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final StringWriter htmlSource = new StringWriter();

    @Test
    public void shouldGiveListOfCategories() throws Exception {
        when(req.getMethod()).thenReturn("GET");

        StatusGiver statusGiver = mock(StatusGiver.class);
        when(statusGiver.categoryStatus()).thenReturn(Arrays.asList(new CategoryDTO("1", "one", 1, null), new CategoryDTO("2", "two", 2, "Someone")));
        servlet.setStatusGiver(statusGiver);

        PlayerHandler playerHandler = mock(PlayerHandler.class);
        when(playerHandler.playerList()).thenReturn(Arrays.asList(new PlayerDTO("Player one", 10), new PlayerDTO("Player two", 15)));
        servlet.setPlayerHandler(playerHandler);

        servlet.service(req, resp);

        JSONObject jsonObject = new JSONObject(htmlSource.toString());
        JSONArray categories = jsonObject.getJSONArray("categories");

        assertThat(categories.length()).isEqualTo(2);
        assertThat(categories.getJSONObject(0).getString("id")).isEqualTo("1");
        assertThat(categories.getJSONObject(0).isNull("answeredBy")).isTrue();
        assertThat(categories.getJSONObject(1).getString("id")).isEqualTo("2");
        assertThat(categories.getJSONObject(1).getString("answeredBy")).isEqualTo("Someone");


        JSONArray players = jsonObject.getJSONArray("players");
        assertThat(players.length()).isEqualTo(2);

    }


    @Before
    public void setup() throws IOException {
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
    }


}
