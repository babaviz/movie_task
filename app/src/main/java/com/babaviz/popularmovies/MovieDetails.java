package com.babaviz.popularmovies;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.babaviz.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MovieDetails extends AppCompatActivity {

    Movie movie=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //show back arrow button on top
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        if(movie==null){
            Toast.makeText(this,"Could not open movie",Toast.LENGTH_LONG).show();
            finish();
        }

        displayMovieData();

    }

    private void displayMovieData() {
        ((TextView)findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView)findViewById(R.id.rating)).setText("Rating:"+movie.getVote_average());
        ((TextView)findViewById(R.id.votes)).setText("Votes:"+movie.getVote_count());
        ((TextView)findViewById(R.id.popularity)).setText("Popularity:"+movie.getPopularity());

        Picasso.with(this)
                .load(AppController.base_image_url+movie.getPoster_path())
                .error(R.drawable.no_image_available)
                .placeholder(R.drawable.noimage)
                .into((ImageView) findViewById(R.id.poster));

        ((TextView)findViewById(R.id.overview)).setText(movie.getOverview());
    }


}
