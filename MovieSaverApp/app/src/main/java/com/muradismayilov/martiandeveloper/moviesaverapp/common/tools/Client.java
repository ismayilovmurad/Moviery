package com.muradismayilov.martiandeveloper.moviesaverapp.common.tools;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

import butterknife.BindString;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    // Strings
    @BindString(R.string.base_url)
    String BASE_URL;

    // Variables
    private Retrofit retrofit = null;

    public Client() {
        ButterKnife.bind(this, FeedActivity.mActivity);
    }

    public Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
