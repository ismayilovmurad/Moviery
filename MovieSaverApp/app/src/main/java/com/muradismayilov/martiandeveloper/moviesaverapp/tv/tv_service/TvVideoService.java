package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvVideoService {
    @GET("tv/{tv_id}/videos")
    Call<TvVideoResponse> getTvVideos(@Path("tv_id") int movie_id, @Query("api_key") String apiKey);
}
