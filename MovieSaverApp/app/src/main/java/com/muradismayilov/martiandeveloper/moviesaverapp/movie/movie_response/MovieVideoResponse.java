package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieVideo;

import java.util.List;

public class MovieVideoResponse {

    @SerializedName("results")
    private final List<MovieVideo> results;

    public MovieVideoResponse(List<MovieVideo> results) {
        this.results = results;
    }

    public List<MovieVideo> getResults() {
        return results;
    }
}
