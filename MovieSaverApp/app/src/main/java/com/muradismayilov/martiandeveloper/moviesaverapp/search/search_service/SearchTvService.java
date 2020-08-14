package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response.SearchTvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchTvService {

    @GET("search/tv")
    Call<SearchTvResponse> getTvWithRequest(@Query("api_key") String apiKey, @Query("query") String title, @Query("page") int page);
}
