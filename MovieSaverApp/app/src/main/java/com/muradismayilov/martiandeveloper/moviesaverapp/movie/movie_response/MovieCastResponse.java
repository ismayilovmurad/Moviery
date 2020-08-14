package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieCast;

import java.util.List;

public class MovieCastResponse {

    @SerializedName("cast")
    private final List<MovieCast> cast;

    public MovieCastResponse(List<MovieCast> cast) {
        this.cast = cast;
    }

    public List<MovieCast> getResults() {
        return cast;
    }
}
