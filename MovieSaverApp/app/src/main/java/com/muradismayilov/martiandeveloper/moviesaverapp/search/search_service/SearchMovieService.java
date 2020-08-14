package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response.SearchMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchMovieService {

    @GET("search/movie")
    Call<SearchMovieResponse> getMovieWithRequest(@Query("api_key") String apiKey, @Query("query") String title, @Query("page") int page);
}
