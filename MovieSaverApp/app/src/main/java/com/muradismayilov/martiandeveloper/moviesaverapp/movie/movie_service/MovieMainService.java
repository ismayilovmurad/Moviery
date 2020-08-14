package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieMainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieMainService {
    @GET("trending/movie/day")
    Call<MovieMainResponse> getTrendingMoviesDay(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<MovieMainResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieMainResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieMainResponse> getUpComingMovies(@Query("api_key") String apiKey, @Query("page") int page, @Query("region") String region);
}
