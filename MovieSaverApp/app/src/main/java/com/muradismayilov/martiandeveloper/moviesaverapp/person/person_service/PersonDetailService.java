package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service;


import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonDetailService {
    @GET("person/{person_id}")
    Call<PersonDetailResponse> getPersonDetail(@Path("person_id") int movie_id, @Query("api_key") String apiKey);
}
