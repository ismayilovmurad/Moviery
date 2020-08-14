package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieSimilar;

import java.util.List;

public class MovieSimilarResponse {

    @SerializedName("results")
    private final List<MovieSimilar> results;

    public MovieSimilarResponse(List<MovieSimilar> results) {
        this.results = results;
    }

    public List<MovieSimilar> getResults() {
        return results;
    }
}
