package com.preemptive.moneybank;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BearerTokenAuthJsonObjectRequest extends JsonObjectRequest {

    private final String token;
    public BearerTokenAuthJsonObjectRequest(int method, String url, JSONObject body,
                                            Response.Listener<JSONObject> listener,
                                            Response.ErrorListener errorListener,
                                            String token) {
        super(method, url, body, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> newMap = new HashMap<>(super.getHeaders());
        newMap.put("Authorization", "Bearer " + token);
        return newMap;
    }
}
