package no.anderska.wta.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

public abstract class AbstractJSONArrayServlet extends HttpServlet {

    protected abstract JSONArray getJSONArrayResult(HttpServletRequest req) throws JSONException;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("text/json");
            resp.getWriter().append(getJSONArrayResult(req).toString());
            resp.getWriter().close();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}