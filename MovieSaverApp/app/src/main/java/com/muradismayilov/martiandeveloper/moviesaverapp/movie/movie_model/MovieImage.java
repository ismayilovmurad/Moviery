package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

public class MovieImage implements Parcelable {

    @SerializedName("file_path")
    private final String file_path;

    public MovieImage(String file_path) {
        this.file_path = file_path;
    }

    protected MovieImage(Parcel in) {
        file_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(file_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieImage> CREATOR = new Creator<MovieImage>() {
        @Override
        public MovieImage createFromParcel(Parcel in) {
            return new MovieImage(in);
        }

        @Override
        public MovieImage[] newArray(int size) {
            return new MovieImage[size];
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
}
