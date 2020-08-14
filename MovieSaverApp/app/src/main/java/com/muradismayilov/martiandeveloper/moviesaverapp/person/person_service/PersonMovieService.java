package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonMovieService {
    @GET("person/{person_id}/movie_credits")
    Call<PersonMovieResponse> getPersonMovie(@Path("person_id") int movie_id, @Query("api_key") String apiKey);
}
