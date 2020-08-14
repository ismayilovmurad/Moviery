package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model;

import com.google.gson.annotations.SerializedName;

public class MovieDetailGenre {

    @SerializedName("name")
    private final String name;

    public MovieDetailGenre(String name) {
        this.name = name;
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }
}
