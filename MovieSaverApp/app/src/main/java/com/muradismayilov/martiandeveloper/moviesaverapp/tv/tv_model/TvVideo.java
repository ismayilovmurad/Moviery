package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import com.google.gson.annotations.SerializedName;

public class TvVideo {
    @SerializedName("key")
    private final String key;
    @SerializedName("name")
    private final String name;

    public TvVideo(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        if (key != null) {
            return key;
        } else {
            return "N/A";
        }
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }
}