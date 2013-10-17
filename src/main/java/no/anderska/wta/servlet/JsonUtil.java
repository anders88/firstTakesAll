package no.anderska.wta.servlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JsonUtil {
    public static JSONArray toJson(List<?> beans) {
        JSONArray jsonArray = new JSONArray();
        for (Object bean : beans) {
            jsonArray.put(new JSONObject(bean));
        }
        return jsonArray;
    }
}
