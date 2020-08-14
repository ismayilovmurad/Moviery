package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvImageService {
    @GET("tv/{tv_id}/images")
    Call<TvImageResponse> getTvImages(@Path("tv_id") int tv_id, @Query("api_key") String apiKey);
}
