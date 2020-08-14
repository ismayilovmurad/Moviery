package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonMovie;

import java.util.List;

public class PersonMovieResponse {

    @SerializedName("cast")
    private final List<PersonMovie> cast;

    public PersonMovieResponse(List<PersonMovie> cast) {
        this.cast = cast;
    }

    public List<PersonMovie> getCast() {
        return cast;
    }
}
