package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonImage;

import java.util.List;

public class PersonImageResponse {

    @SerializedName("profiles")
    private final List<PersonImage> profiles;

    public PersonImageResponse(List<PersonImage> profiles) {
        this.profiles = profiles;
    }

    public List<PersonImage> getProfiles() {
        return profiles;
    }
}
