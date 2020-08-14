package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchMovie;

import java.util.List;

public class SearchMovieResponse {

    @SerializedName("results")
    private final List<SearchMovie> results;

    public SearchMovieResponse(List<SearchMovie> results) {
        this.results = results;
    }

    public List<SearchMovie> getResults() {
        return results;
    }
}
