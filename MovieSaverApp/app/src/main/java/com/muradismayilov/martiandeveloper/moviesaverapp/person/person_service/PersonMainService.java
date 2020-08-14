package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonMainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonMainService {
    @GET("trending/person/day")
    Call<PersonMainResponse> getTrendingPersonDay(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("person/popular")
    Call<PersonMainResponse> getPopularPerson(@Query("api_key") String apiKey, @Query("page") int page);
}
