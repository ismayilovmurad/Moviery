package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class PersonTv {

    @SerializedName("first_air_date")
    private final String first_air_date;
    @SerializedName("name")
    private final String name;
    @SerializedName("vote_average")
    private final float vote_average;
    @SerializedName("poster_path")
    private final String poster_path;

    public PersonTv(String first_air_date, String name, float vote_average, String poster_path) {
        this.first_air_date = first_air_date;
        this.name = name;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        String[] dateList;

        if (first_air_date != null) {
            dateList = first_air_date.split("-");
            try {
                return dateList[0];
            } catch (Exception e) {
                return "N/A";
            }
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

    public String getVoteAverage() {
        return "\u2605 " + vote_average;
    }

    public String getPoster_path() {
        if (poster_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + poster_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w185_poster_image_url) + poster_path;
            }
        } else {
            return "N/A";
        }
    }
}
