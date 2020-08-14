package com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response.FilterTvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilterTvService {

    @GET("discover/tv")
    Call<FilterTvResponse> getTvWithType(@Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/tv")
    Call<FilterTvResponse> getTvWithTypeBollywood(@Query("with_original_language") String original_language, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/tv")
    Call<FilterTvResponse> getTvWithTypeAndGenres(@Query("with_genres") String genres, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("discover/tv")
    Call<FilterTvResponse> getTvWithTypeAndGenresBollywood(@Query("with_original_language") String original_language, @Query("with_genres") String genres, @Query("sort_by") String type, @Query("api_key") String apiKey, @Query("page") int page);
}
