package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvCastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvCastService {
    @GET("tv/{tv_id}/credits")
    Call<TvCastResponse> getTvCast(@Path("tv_id") int tv_id, @Query("api_key") String apiKey);
}
