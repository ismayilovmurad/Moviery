package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvSimilar;

import java.util.List;

public class TvSimilarResponse {

    @SerializedName("results")
    private final List<TvSimilar> results;

    public TvSimilarResponse(List<TvSimilar> results) {
        this.results = results;
    }

    public List<TvSimilar> getResults() {
        return results;
    }
}
