package com.babaviz.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.babaviz.popularmovies.ServerResponse.ServerResponse;
import com.babaviz.popularmovies.adapters.MovieListAdapter;
import com.babaviz.popularmovies.models.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gturedi.views.StatefulLayout;

public class MainActivity extends AppCompatActivity {

    MovieListAdapter adapter;
    RecyclerView movieContainer;
    StatefulLayout stateful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        movieContainer=findViewById(R.id.movieList);
        stateful=findViewById(R.id.stateful);
        adapter=new MovieListAdapter(new ArrayList<Movie>());//initialize my movie adapter

        //set layout for the movies
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        movieContainer.setLayoutManager(layoutManager);
        movieContainer.setAdapter(adapter);


        loadMovies(1);//call my method to load the videos on application start
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }

        if(id==R.id.action_refresh){
            loadMovies(1);
            return true;
        }

        return false;
    }

    private void loadMovies(final int page){
        stateful.showLoading();//show loading

        if(!AppController.instance.isConnected()){//check if device is connected
           stateful.showOffline(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   loadMovies(page);
               }
           });
           return;
        }

        //send request to api using volley library
        final Gson gson= new GsonBuilder().create();
        String url=String.format(AppController.base_url+AppController.first_page_request_params,getString(R.string.api_key),page);
        StringRequest request=new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ServerResponse serverResponse=null;
                        try{
                            //map the api response to my predefined class model
                            serverResponse=gson.fromJson(response,ServerResponse.class);
                            adapter.setData(serverResponse.getResults());
                            stateful.showContent();

                        }catch (Exception ex){
                            ex.printStackTrace();
                            stateful.showError("Error occured while fetching data, please try again later",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            loadMovies(page);
                                        }
                                    });
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        stateful.showError("Error occured while fetching data, please try again later",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        loadMovies(page);
                                    }
                                });
                    }
                }
        );

        AppController.instance.addToRequestQueue(request);
    }
}
