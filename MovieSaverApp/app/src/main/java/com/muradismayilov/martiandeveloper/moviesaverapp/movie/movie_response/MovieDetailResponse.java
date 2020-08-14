package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieDetailGenre;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieDetailProductionCompanies;

import java.text.DecimalFormat;
import java.util.List;

public class MovieDetailResponse {

    @SerializedName("backdrop_path")
    private final String backdrop_path;
    @SerializedName("budget")
    private final int budget;
    @SerializedName("genres")
    private final List<MovieDetailGenre> genres;
    @SerializedName("original_language")
    private final String original_language;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("poster_path")
    private final String poster_path;
    @SerializedName("production_companies")
    private final List<MovieDetailProductionCompanies> production_companies;
    @SerializedName("release_date")
    private final String release_date;
    @SerializedName("revenue")
    private final int revenue;
    @SerializedName("runtime")
    private final int runtime;
    @SerializedName("status")
    private final String status;
    @SerializedName("tagline")
    private final String tagline;
    @SerializedName("vote_average")
    private final float vote_average;
    @SerializedName("vote_count")
    private final int vote_count;

    public MovieDetailResponse(String backdrop_path, int budget, List<MovieDetailGenre> genres, String original_language, String overview, Double popularity, String poster_path, List<MovieDetailProductionCompanies> production_companies, String release_date, int revenue, int runtime, String status, String tagline, float vote_average, int vote_count) {
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.genres = genres;
        this.original_language = original_language;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        if (backdrop_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w780_backdrop_image_url) + backdrop_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w300_backdrop_image_url) + backdrop_path;
            }
        } else {
            return "N/A";
        }
    }

    public String getBudget() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "$" + formatter.format(budget);
    }

    public List<MovieDetailGenre> getGenres() {
        return genres;
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
        if (popularity != null) {
            return String.valueOf(popularity);
        } else {
            return "N/A";
        }
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

    public List<MovieDetailProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public String getRelease_date() {
        if (release_date != null) {
            return release_date;
        } else {
            return "N/A";
        }
    }

    public String getRevenue() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "$" + formatter.format(revenue);
    }

    public String getRuntime() {
        return runtime + "m";
    }

    public String getStatus() {
        if (status != null) {
            return status;
        } else {
            return "N/A";
        }
    }

    public String getTagline() {
        if (tagline != null) {
            return tagline;
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
