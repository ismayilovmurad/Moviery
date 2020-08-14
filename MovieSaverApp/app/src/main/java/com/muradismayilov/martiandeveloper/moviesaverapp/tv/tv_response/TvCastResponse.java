package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvCast;

import java.util.List;

public class TvCastResponse {

    @SerializedName("cast")
    private final List<TvCast> cast;

    public TvCastResponse(List<TvCast> cast) {
        this.cast = cast;
    }

    public List<TvCast> getCast() {
        return cast;
    }
}
