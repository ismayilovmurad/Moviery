package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieMain;

import java.util.List;

public class MovieMainResponse {

    @SerializedName("results")
    private final List<MovieMain> results;

    public MovieMainResponse(List<MovieMain> results) {
        this.results = results;
    }

    public List<MovieMain> getResults() {
        return results;
    }
}
