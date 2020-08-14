package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class MovieCast {

    @SerializedName("character")
    private final String character;
    @SerializedName("name")
    private final String name;
    @SerializedName("profile_path")
    private final String profile_path;

    public MovieCast(String character, String name, String profile_path) {
        this.character = character;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCharacter() {
        if (character != null) {
            return character;
        } else {
            return "N/A";
        }
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }

    public String getProfile_path() {
        if (profile_path != null) {
            return FeedActivity.mContext.getResources().getString(R.string.w185_profile_image_url) + profile_path;
        } else {
            return "N/A";
        }
    }
}
