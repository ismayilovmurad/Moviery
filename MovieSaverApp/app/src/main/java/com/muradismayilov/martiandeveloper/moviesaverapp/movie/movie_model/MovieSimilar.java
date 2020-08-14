package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class MovieSimilar {

    @SerializedName("title")
    private final String title;
    @SerializedName("vote_average")
    private final float vote_average;
    @SerializedName("release_date")
    private final String release_date;
    @SerializedName("poster_path")
    private final String poster_path;

    public MovieSimilar(String title, float vote_average, String release_date, String poster_path) {
        this.title = title;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        } else {
            return "N/A";
        }
    }

    public String getVoteAverage() {
        return "\u2605 " + vote_average;
    }

    public String getRelease_date() {
        String[] dateList;

        if (release_date != null) {
            dateList = release_date.split("-");
            try {
                return dateList[0];
            } catch (Exception e) {
                return "N/A";
            }
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
