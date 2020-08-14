package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvMainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvMainService {
    @GET("trending/tv/day")
    Call<TvMainResponse> getTrendingTvDay(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/popular")
    Call<TvMainResponse> getPopularTv(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvMainResponse> getTopRatedTv(@Query("api_key") String apiKey, @Query("page") int page);
}
