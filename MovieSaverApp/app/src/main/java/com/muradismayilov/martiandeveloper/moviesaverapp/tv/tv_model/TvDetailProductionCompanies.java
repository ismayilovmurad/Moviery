package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class TvDetailProductionCompanies {

    @SerializedName("logo_path")
    private final String logo_path;
    @SerializedName("name")
    private final String name;
    @SerializedName("origin_country")
    private final String origin_country;

    public TvDetailProductionCompanies(String logo_path, String name, String origin_country) {
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
    }

    public String getLogo_path() {
        if (logo_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w154_logo_image_url) + logo_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w92_logo_image_url) + logo_path;
            }
        } else {
            return "N/A";
        }
    }

    public String getName() {
        if (name != null) {
            return name;
        } else {
            return "N/A";
        }
    }

    public String getOrigin_country() {
        if (origin_country != null) {
            return origin_country;
        } else {
            return "N/A";
        }
    }
}
