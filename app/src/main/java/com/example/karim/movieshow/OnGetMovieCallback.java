package com.example.karim.movieshow;

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
