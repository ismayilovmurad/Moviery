package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class PersonMain {

    @SerializedName("name")
    private final String name;
    @SerializedName("id")
    private final int id;
    @SerializedName("profile_path")
    private final String profile_path;

    public PersonMain(String name, int id, String profile_path) {
        this.name = name;
        this.id = id;
        this.profile_path = profile_path;
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }

    public int getId() {
        return id;
    }

    public String getProfile_path() {
        if (profile_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.h632_profile_image_url) + profile_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w185_profile_image_url) + profile_path;
            }
        } else {
            return "N/A";
        }
    }

    public String getProfile_pathForDB() {
        if (profile_path != null) {
            return profile_path;
        } else {
            return "N/A";
        }
    }
}
