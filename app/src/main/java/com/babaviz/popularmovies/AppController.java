package com.babaviz.popularmovies;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    public static final String base_url="https://api.themoviedb.org/3/discover/movie";
    public static final String first_page_request_params="?api_key=%s&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=%d";
    public static final String base_image_url="https://image.tmdb.org/t/p/w300";
    public static AppController instance;

    private RequestQueue queue;
    private String TAG=AppController.class.getSimpleName();

    @Override
    public void onCreate(){
        super.onCreate();
        queue= Volley.newRequestQueue(getApplicationContext());
        instance=this;
    }

    public static AppController getInstance(){
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        queue.add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        queue.add(req);
    }

    public boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
