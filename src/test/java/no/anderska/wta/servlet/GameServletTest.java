package no.anderska.wta.servlet;


import com.google.gson.Gson;
import no.anderska.wta.GameHandler;
import no.anderska.wta.dto.PlayerAnswerDto;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServletTest {

    @Test
    public void shouldHandleAnswer() throws Exception {
        PlayerAnswerDto playerAnswerDto = new PlayerAnswerDto();
        playerAnswerDto.setPlayerId("playerone");
        playerAnswerDto.setAnswers(Arrays.asList("one","two","three"));

        Gson gson = new Gson();

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getMethod()).thenReturn("POST");
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(gson.toJson(playerAnswerDto))));
        HttpServletResponse resp = mock(HttpServletResponse.class);

        GameServlet servlet = new GameServlet();

        GameHandler gameHandler = mock(GameHandler.class);
        servlet.setGameHandler(gameHandler);



        servlet.service(req,resp);

        Class<List<String>> listClass = (Class<List<String>>)(Class)List.class;
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(listClass);

        verify(gameHandler).answer(Matchers.eq("playerone"),captor.capture());

        List<String> givenAnswers = captor.getValue();
        assertThat(givenAnswers).hasSize(3).contains("one","two","three");
    }
}
