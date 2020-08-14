package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieImageService {
    @GET("movie/{movie_id}/images")
    Call<MovieImageResponse> getMovieImages(@Path("movie_id") int movie_id, @Query("api_key") String apiKey);
}
