package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvVideo;

import java.util.List;

public class TvVideoResponse {

    @SerializedName("results")
    private final List<TvVideo> results;

    public TvVideoResponse(List<TvVideo> results) {
        this.results = results;
    }

    public List<TvVideo> getResults() {
        return results;
    }
}
