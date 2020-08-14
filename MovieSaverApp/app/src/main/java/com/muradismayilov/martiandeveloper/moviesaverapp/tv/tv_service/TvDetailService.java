package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvDetailService {
    @GET("tv/{tv_id}")
    Call<TvDetailResponse> getTvDetail(@Path("tv_id") int tv_id, @Query("api_key") String apiKey);
}
