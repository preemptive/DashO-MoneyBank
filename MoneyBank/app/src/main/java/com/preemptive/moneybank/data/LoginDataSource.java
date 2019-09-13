package com.preemptive.moneybank.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.preemptive.moneybank.api.RequestQueueSingleton;
import com.preemptive.moneybank.data.model.LoggedInUser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final RequestQueueSingleton requestQueueSingleton;

    public LoginDataSource(RequestQueueSingleton requestQueueSingleton) {
        this.requestQueueSingleton = requestQueueSingleton;
    }

    private static final String loginUrl = "https://10.0.2.2:5001/api/login";

    public void login(String username, String password, final ResultCallback<LoggedInUser> callback) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        JSONObject body = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, loginUrl, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String tokenText = response.getString("token");
                            JWT jwt = new JWT(tokenText);
                            Claim customerNameClaim = jwt.getClaim("customerName");
                            LoggedInUser user = new LoggedInUser(jwt.getSubject(), ((Claim) customerNameClaim).asString(), tokenText);
                            callback.handleResult(new Result.Success<>(user));
                        } catch (JSONException exc) {
                            callback.handleResult(new Result.Error(new Exception("Error parsing response", exc)));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.handleResult(new Result.Error(new IOException("Error logging in", error.getCause())));
                    }
                });
        requestQueueSingleton.addToRequestQueue(request);
    }

    public void logout() {
    }
}
