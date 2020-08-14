package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import com.google.gson.annotations.SerializedName;

public class TvDetailGenre {

    @SerializedName("name")
    private final String name;

    public TvDetailGenre(String name) {
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
