package com.dakiiii.hungerwarrior;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton sVolleySingleton;
    private static Context sContext;
    private RequestQueue eRequestQueue;

    private VolleySingleton(Context context) {
        sContext = context;
        eRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (sVolleySingleton == null) {
            sVolleySingleton = new VolleySingleton(context);
        }
        return sVolleySingleton;

    }

    public RequestQueue getRequestQueue() {
        if (eRequestQueue == null) {
            eRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return eRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
