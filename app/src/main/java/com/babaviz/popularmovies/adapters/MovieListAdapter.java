package com.babaviz.popularmovies.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.babaviz.popularmovies.AppController;
import com.babaviz.popularmovies.MovieDetails;
import com.babaviz.popularmovies.R;
import com.babaviz.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

/*
ayoola.ajebeku@andela.com
I want you to complete this task and upload on GitHub and send me the GitHub link. I will be expecting it in the morning.
Tomorrow morning.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    List<Movie> data;
    public MovieListAdapter(List<Movie> data){
        this.data=data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );

        return new MovieViewHolder(view);
    }

    public void setData(List<Movie> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addMovie(Movie movie){
        data.add(movie);
        notifyItemInserted(data.size()-1);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final Movie movie=data.get(position);
        holder.title_view.setText(movie.getTitle());

        //use picasso library to load cover images
        Picasso.with(holder.title_view.getContext())
                .load(AppController.base_image_url+movie.getPoster_path())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.no_image_available)
                .into(holder.cover);

        //add listner to the movie item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(holder.itemView.getContext(), MovieDetails.class);
                i.putExtra("movie",movie);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title_view;
        ImageView cover;
        public MovieViewHolder(View itemView) {
            super(itemView);
            title_view=itemView.findViewById(R.id.movie_title);
            cover=itemView.findViewById(R.id.moviecover);
        }
    }
}
