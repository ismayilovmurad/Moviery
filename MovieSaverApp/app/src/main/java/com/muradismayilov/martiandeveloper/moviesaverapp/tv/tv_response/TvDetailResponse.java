package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailCreatedBy;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailGenre;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailLastEpisodeToAir;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailNetworks;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailNextEpisodeToAir;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailProductionCompanies;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailSeasons;

import java.util.List;

public class TvDetailResponse {

    @SerializedName("backdrop_path")
    private final String backdrop_path;
    @SerializedName("created_by")
    private final List<TvDetailCreatedBy> created_by;
    @SerializedName("episode_run_time")
    private final List<Integer> episode_run_time;
    @SerializedName("first_air_date")
    private final String first_air_date;
    @SerializedName("genres")
    private final List<TvDetailGenre> genres;
    @SerializedName("last_air_date")
    private final String last_air_date;
    @SerializedName("last_episode_to_air")
    private final TvDetailLastEpisodeToAir last_episode_to_air;
    @SerializedName("next_episode_to_air")
    private final TvDetailNextEpisodeToAir next_episode_to_air;
    @SerializedName("networks")
    private final List<TvDetailNetworks> networks;
    @SerializedName("number_of_episodes")
    private final int number_of_episodes;
    @SerializedName("number_of_seasons")
    private final int number_of_seasons;
    @SerializedName("original_language")
    private final String original_language;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("poster_path")
    private final String poster_path;
    @SerializedName("production_companies")
    private final List<TvDetailProductionCompanies> production_companies;
    @SerializedName("seasons")
    private final List<TvDetailSeasons> seasons;
    @SerializedName("status")
    private final String status;
    @SerializedName("vote_average")
    private final float vote_average;
    @SerializedName("vote_count")
    private final int vote_count;

    public TvDetailResponse(String backdrop_path, List<TvDetailCreatedBy> created_by, List<Integer> episode_run_time, String first_air_date, List<TvDetailGenre> genres, String last_air_date, TvDetailLastEpisodeToAir last_episode_to_air, TvDetailNextEpisodeToAir next_episode_to_air, List<TvDetailNetworks> networks, int number_of_episodes, int number_of_seasons, String original_language, String overview, Double popularity, String poster_path, List<TvDetailProductionCompanies> production_companies, List<TvDetailSeasons> seasons, String status, float vote_average, int vote_count) {
        this.backdrop_path = backdrop_path;
        this.created_by = created_by;
        this.episode_run_time = episode_run_time;
        this.first_air_date = first_air_date;
        this.genres = genres;
        this.last_air_date = last_air_date;
        this.last_episode_to_air = last_episode_to_air;
        this.next_episode_to_air = next_episode_to_air;
        this.networks = networks;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.seasons = seasons;
        this.status = status;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        if (backdrop_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + backdrop_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w154_poster_image_url) + backdrop_path;
            }
        } else {
            return "N/A";
        }
    }

    public List<TvDetailCreatedBy> getCreated_by() {
        return created_by;
    }

    public String getEpisode_run_time() {
        return episode_run_time.get(0) + "m";
    }

    public String getFirst_air_date() {
        if (first_air_date != null) {
            return first_air_date;
        } else {
            return "N/A";
        }
    }

    public List<TvDetailGenre> getGenres() {
        return genres;
    }

    public String getLast_air_date() {
        if (last_air_date != null) {
            return last_air_date;
        } else {
            return "N/A";
        }
    }

    public TvDetailLastEpisodeToAir getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public TvDetailNextEpisodeToAir getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public List<TvDetailNetworks> getNetworks() {
        return networks;
    }

    public String getNumber_of_episodes() {
        return String.valueOf(number_of_episodes);
    }

    public String getNumber_of_seasons() {
        return String.valueOf(number_of_seasons);
    }

    public String getOriginal_language() {
        if (original_language != null) {
            return original_language;
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

    public String getPopularity() {
        return String.valueOf(popularity);
    }

    public String getPoster_path() {
        if (poster_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + poster_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w154_poster_image_url) + poster_path;
            }
        } else {
            return "N/A";
        }
    }

    public List<TvDetailProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public List<TvDetailSeasons> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        if (status != null) {
            return status;
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
