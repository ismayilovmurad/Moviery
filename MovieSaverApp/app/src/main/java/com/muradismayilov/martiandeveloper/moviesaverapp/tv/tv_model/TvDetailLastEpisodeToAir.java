package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class TvDetailLastEpisodeToAir {

    @SerializedName("air_date")
    private final String air_date;
    @SerializedName("episode_number")
    private final int episode_number;
    @SerializedName("name")
    private final String name;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("season_number")
    private final int season_number;
    @SerializedName("still_path")
    private final String still_path;
    @SerializedName("vote_average")
    private final float vote_average;
    @SerializedName("vote_count")
    private final int vote_count;

    public TvDetailLastEpisodeToAir(String air_date, int episode_number, String name, String overview, int season_number, String still_path, float vote_average, int vote_count) {
        this.air_date = air_date;
        this.episode_number = episode_number;
        this.name = name;
        this.overview = overview;
        this.season_number = season_number;
        this.still_path = still_path;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getAir_date() {
        if (air_date != null) {
            return air_date;
        } else {
            return "N/A";
        }
    }

    public String getEpisode_number() {
        return String.valueOf(episode_number);
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }

    public String getOverview() {
        if (overview != null) {
            return overview;
        } else {
            return "N/A";
        }
    }

    public String getSeason_number() {
        return String.valueOf(season_number);
    }

    public String getStill_path() {
        if (still_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + still_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w185_poster_image_url) + still_path;
            }
        } else {
            return "N/A";
        }
    }

    public String getVote_average() {
        return String.valueOf(vote_average);
    }

    public String getVote_count() {
        return String.valueOf(vote_count);
    }
}
