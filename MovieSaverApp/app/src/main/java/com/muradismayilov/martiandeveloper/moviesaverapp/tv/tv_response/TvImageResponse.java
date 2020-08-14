package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvImage;

import java.util.List;

public class TvImageResponse {

    @SerializedName("backdrops")
    private final List<TvImage> backdrops;

    public TvImageResponse(List<TvImage> backdrops) {
        this.backdrops = backdrops;
    }

    public List<TvImage> getBackdrops() {
        return backdrops;
    }
}
