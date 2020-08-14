package com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_model.FilterMovie;

import java.util.List;

public class FilterMovieResponse {

    @SerializedName("results")
    private final List<FilterMovie> results;

    public FilterMovieResponse(List<FilterMovie> results) {
        this.results = results;
    }

    public List<FilterMovie> getResults() {
        return results;
    }
}
