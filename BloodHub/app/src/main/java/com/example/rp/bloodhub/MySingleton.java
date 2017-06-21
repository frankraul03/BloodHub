package com.example.rp.bloodhub;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rp on 5/31/17.
 */

public class MySingleton {
    //
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    //2

    private MySingleton (Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();

    }

    //1
    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    //3
    public static synchronized  MySingleton getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    //4
    public <T>void addToRequestque(Request<T> request)
    {

        requestQueue.add(request);
    }

}
