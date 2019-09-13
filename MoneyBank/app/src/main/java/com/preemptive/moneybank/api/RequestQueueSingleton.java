package com.preemptive.moneybank.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    private static final Object lock = new Object();

    private static RequestQueueSingleton INSTANCE;

    private final RequestQueue requestQueue;

    private RequestQueueSingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static RequestQueueSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestQueueSingleton(context);
                }
            }
        }
        return INSTANCE;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
