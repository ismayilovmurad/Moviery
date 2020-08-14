package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchPerson;

import java.util.List;

public class SearchPersonResponse {

    @SerializedName("results")
    private final List<SearchPerson> results;

    public SearchPersonResponse(List<SearchPerson> results) {
        this.results = results;
    }

    public List<SearchPerson> getResults() {
        return results;
    }
}
