package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class TvDetailSeasons {

    @SerializedName("air_date")
    private final String air_date;
    @SerializedName("name")
    private final String name;
    @SerializedName("poster_path")
    private final String poster_path;

    public TvDetailSeasons(String air_date, String name, String poster_path) {
        this.air_date = air_date;
        this.name = name;
        this.poster_path = poster_path;
    }

    public String getAir_date() {
        if (air_date != null) {
            return air_date;
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
