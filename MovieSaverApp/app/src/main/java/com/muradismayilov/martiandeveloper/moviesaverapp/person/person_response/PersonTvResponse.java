package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonTv;

import java.util.List;

public class PersonTvResponse {

    @SerializedName("cast")
    private final List<PersonTv> cast;

    public PersonTvResponse(List<PersonTv> cast) {
        this.cast = cast;
    }

    public List<PersonTv> getCast() {
        return cast;
    }
}
