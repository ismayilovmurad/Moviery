package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class TvImage implements Parcelable {

    @SerializedName("file_path")
    private final String file_path;

    public TvImage(String file_path) {
        this.file_path = file_path;
    }

    protected TvImage(Parcel in) {
        file_path = in.readString();
    }

    public static final Creator<TvImage> CREATOR = new Creator<TvImage>() {
        @Override
        public TvImage createFromParcel(Parcel in) {
            return new TvImage(in);
        }

        @Override
        public TvImage[] newArray(int size) {
            return new TvImage[size];
        }
    };

    public String getFile_path() {
        if (file_path != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return FeedActivity.mContext.getResources().getString(R.string.w780_backdrop_image_url) + file_path;
            } else {
                return FeedActivity.mContext.getResources().getString(R.string.w300_backdrop_image_url) + file_path;
            }
        } else {
            return "N/A";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(file_path);
    }
}

