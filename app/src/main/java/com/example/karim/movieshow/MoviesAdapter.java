package com.example.karim.movieshow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private List<Genre> allGenres;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private Context context;
    private OnMoviesClickCallback callback;

    public MoviesAdapter(List<Movie> movies, List<Genre> allGenres,Context context) {
        this.movies = movies;
        this.allGenres=allGenres;
        this.context = context;
    }

    public MoviesAdapter(List<Movie> movies,List<Genre> allGenres, OnMoviesClickCallback callback) {
        this.callback = callback;
        this.movies = movies;
        this.allGenres=allGenres;
    }
    public void appendMovies(List<Movie> moviesToAppend) {
        movies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }
    public void clearMovies() {
        movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
//        final MoviesAdapter.MovieViewHolder viewHolder = new MoviesAdapter.MovieViewHolder(view) ;
//        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, MovieActivity.class);
//                intent.putExtra(MovieActivity.MOVIE_ID, movies.get(viewHolder.getAdapterPosition()).getId());
//                context.startActivity(intent);
//
//            }
//        });
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
       holder.bind(movies.get(position));
//        Movie movie=movies.get(position);
//        holder.releaseDate.setText(movie.getReleaseDate().split("-")[0]);
//        holder.title.setText(movie.getTitle());
//        holder.rating.setText(String.valueOf(movie.getRating()));
//        holder.genres.setText("");
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;

        Movie movie;

        public CardView view_container;
        public MovieViewHolder(View itemView) {

            super(itemView);
            view_container = itemView.findViewById(R.id.bankcardId);
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            genres = itemView.findViewById(R.id.item_movie_genre);
            poster = itemView.findViewById(R.id.item_movie_poster);

            /////////////////////////
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(movie);
                }
            });
        }

        public void bind(Movie movie) {
            this.movie = movie;
            ///////////////////////////
            releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            title.setText(movie.getTitle());
            rating.setText(String.valueOf(movie.getRating()));
            genres.setText(getGenres(movie.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.load_spinner))
                    .into(poster);
        }
        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : allGenres) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }

            return TextUtils.join(", ", movieGenres);
        }

    }

}
