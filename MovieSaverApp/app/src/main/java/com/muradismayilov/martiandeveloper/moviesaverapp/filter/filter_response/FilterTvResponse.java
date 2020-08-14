package com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_model.FilterTv;

import java.util.List;

public class FilterTvResponse {

    @SerializedName("results")
    private final List<FilterTv> results;

    public FilterTvResponse(List<FilterTv> results) {
        this.results = results;
    }

    public List<FilterTv> getResults() {
        return results;
    }
}
