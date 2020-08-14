package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service;

import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonImageService {
    @GET("person/{person_id}/images")
    Call<PersonImageResponse> getPersonImages(@Path("person_id") int movie_id, @Query("api_key") String apiKey);
}
