package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonMain;

import java.util.List;

public class PersonMainResponse {

    @SerializedName("results")
    private final List<PersonMain> results;

    public PersonMainResponse(List<PersonMain> results) {
        this.results = results;
    }

    public List<PersonMain> getResults() {
        return results;
    }
}
