package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvMain;

import java.util.List;

public class TvMainResponse {

    @SerializedName("results")
    private final List<TvMain> results;

    public TvMainResponse(List<TvMain> results) {
        this.results = results;
    }

    public List<TvMain> getResults() {
        return results;
    }
}
