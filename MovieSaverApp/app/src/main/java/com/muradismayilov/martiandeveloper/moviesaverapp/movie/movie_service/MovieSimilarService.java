package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieSimilarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieSimilarService {
    @GET("movie/{movie_id}/similar")
    Call<MovieSimilarResponse> getMovieSimilar(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("page") int page);
}
