package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieImage;

import java.util.List;

public class MovieImageResponse {

    @SerializedName("backdrops")
    private final List<MovieImage> backdrops;

    public MovieImageResponse(List<MovieImage> backdrops) {
        this.backdrops = backdrops;
    }

    public List<MovieImage> getBackdrops() {
        return backdrops;
    }
}
