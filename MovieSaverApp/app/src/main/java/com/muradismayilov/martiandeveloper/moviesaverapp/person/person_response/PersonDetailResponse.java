package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class PersonDetailResponse {

    @SerializedName("birthday")
    private final String birthday;
    @SerializedName("known_for_department")
    private final String known_for_department;
    @SerializedName("biography")
    private final String biography;
    @SerializedName("popularity")
    private final float popularity;
    @SerializedName("profile_path")
    private final String profile_path;
    @SerializedName("place_of_birth")
    private final String place_of_birth;

    public PersonDetailResponse(String birthday, String known_for_department, String biography, float popularity, String profile_path, String place_of_birth) {
        this.birthday = birthday;
        this.known_for_department = known_for_department;
        this.biography = biography;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.place_of_birth = place_of_birth;
    }

    public String getBirthday() {
        if (birthday != null) {
            return birthday;
        } else {
            return "N/A";
        }
    }

    public String getKnown_for_department() {
        if (known_for_department != null) {
            return known_for_department;
        } else {
            return "N/A";
        }
    }

    public String getBiography() {
        if (biography != null) {
            return biography;
        } else {
            return "N/A";
        }
    }

    public String getPopularity() {
        return String.valueOf(popularity);
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

    public String getPlaceOfBirth() {
        if (place_of_birth != null) {
            return place_of_birth;
        } else {
            return "N/A";
        }
    }
}
