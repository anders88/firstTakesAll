package no.anderska.wta.servlet;

import no.anderska.wta.dto.LogEntryDetailDTO;
import no.anderska.wta.logging.LogReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogReadeerServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private LogReaderServlet servlet = new LogReaderServlet();

    @Test
    public void shouldGiveDetailedLogMessage() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getPathInfo()).thenReturn("/detail");
        when(req.getParameter("id")).thenReturn("3");

        LogReader logReader = mock(LogReader.class);
        servlet.setLogReader(logReader);
        LogEntryDetailDTO logEntry1 = new LogEntryDetailDTO("a1", "a1", "q1");
        LogEntryDetailDTO logEntry2 = new LogEntryDetailDTO("a2", "w2", "q2");
        when(logReader.getDetail(3L)).thenReturn(Arrays.asList(logEntry1, logEntry2));
        StringWriter jsonSource = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(jsonSource));

        servlet.service(req, resp);

        JSONArray jsonArray = new JSONArray(jsonSource.toString());

        assertThat(jsonArray.length()).isEqualTo(2);

        JSONObject entry1 = jsonArray.getJSONObject(0);
        JSONObject entry2 = jsonArray.getJSONObject(1);

        assertThat(entry1.get("answer")).isEqualTo(logEntry1.getAnswer());
        assertThat(entry1.get("expected")).isEqualTo(logEntry1.getExpected());
        assertThat(entry1.get("question")).isEqualTo(logEntry1.getQuestion());
        assertThat(entry1.get("status")).isEqualTo(logEntry1.getStatus());

        assertThat(entry2.get("answer")).isEqualTo(logEntry2.getAnswer());
        assertThat(entry2.get("expected")).isEqualTo(logEntry2.getExpected());
        assertThat(entry2.get("question")).isEqualTo(logEntry2.getQuestion());
        assertThat(entry2.get("status")).isEqualTo(logEntry2.getStatus());


    }
}
