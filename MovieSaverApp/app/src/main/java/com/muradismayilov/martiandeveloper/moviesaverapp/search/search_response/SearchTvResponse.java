package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchTv;

import java.util.List;

public class SearchTvResponse {

    @SerializedName("results")
    private final List<SearchTv> results;

    public SearchTvResponse(List<SearchTv> results) {
        this.results = results;
    }

    public List<SearchTv> getResults() {
        return results;
    }
}
