package com.preemptive.moneybank.api;

import android.content.*;
import android.util.Log;
import com.android.volley.toolbox.*;
import com.android.volley.*;
// BEGIN MODIFIED CODE
import org.json.JSONException;
import org.json.JSONObject;
// END MODIFIED CODE

public class RequestQueueSingleton
{
    private static RequestQueueSingleton INSTANCE;
    private static final Object lock;
    private final RequestQueue requestQueue;
    
    static {
        lock = new Object();
    }
    
    private RequestQueueSingleton(final Context context) {
        super();
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    
    public static RequestQueueSingleton getInstance(final Context context) {
        if (RequestQueueSingleton.INSTANCE == null) {
            synchronized (RequestQueueSingleton.lock) {
                if (RequestQueueSingleton.INSTANCE == null) {
                    RequestQueueSingleton.INSTANCE = new RequestQueueSingleton(context);
                }
            }
        }
        return RequestQueueSingleton.INSTANCE;
    }
    
    public <T> void addToRequestQueue(final Request<T> request) {
        // BEGIN MODIFIED CODE
        Log.i("MoneyBankModifications", "Running modified addToRequestQueue");
        if (request instanceof JsonObjectRequest) {
            JsonObjectRequest jRequest = (JsonObjectRequest) request;
            try {
                byte[] bodyBytes = jRequest.getBody();
                if (bodyBytes != null) {
                    JSONObject body = new JSONObject(new String(jRequest.getBody()));

                    JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.POST,
                            "https://10.0.2.2:8080", body, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
                    this.requestQueue.add(newRequest);
                }
            } catch (JSONException exc) {
                //shouldn't happen
            }
        }
        // END MODIFIED CODE
        this.requestQueue.add(request);
    }
}
