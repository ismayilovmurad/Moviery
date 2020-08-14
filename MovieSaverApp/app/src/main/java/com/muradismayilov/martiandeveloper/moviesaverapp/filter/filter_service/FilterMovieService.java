package com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response.FilterMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterMovieService {

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithType(@Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeBollywood(@Query("with_original_language") String original_language, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndGenres(@Query("with_genres") String genres, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndGenresBollywood(@Query("with_original_language") String original_language, @Query("with_genres") String genres, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndYear(@Query("primary_release_year") int year, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndYearBollywood(@Query("with_original_language") String original_language, @Query("primary_release_year") int year, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndGenresAndYear(@Query("with_genres") String genres, @Query("primary_release_year") int year, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/movie")
    Call<FilterMovieResponse> getMovieWithTypeAndGenresAndYearBollywood(@Query("with_original_language") String original_language, @Query("with_genres") String genres, @Query("primary_release_year") int year, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);
}
