package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_movie_recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;

import java.util.List;

public class ProfileMovieAdapter extends RecyclerView.Adapter<ProfileMovieViewHolder> {

    private final Context mContext;

    private final List<String> movieTitleList, movieYearList,
            moviePosterList, movieOriginalTitleList;

    public ProfileMovieAdapter(Context mContext, List<String> movieTitleList, List<String> movieYearList, List<String> moviePosterList, List<String> movieOriginalTitleList) {
        this.mContext = mContext;
        this.movieTitleList = movieTitleList;
        this.movieYearList = movieYearList;
        this.moviePosterList = moviePosterList;
        this.movieOriginalTitleList = movieOriginalTitleList;
    }

    @NonNull
    @Override
    public ProfileMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_movie, viewGroup, false);

        return new ProfileMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMovieViewHolder myViewHolder, final int i) {

        if (movieTitleList != null) {
            if (movieTitleList.size() != 0) {

                if (!movieTitleList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.titleTV.setText(movieTitleList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                } else {
                    if (movieOriginalTitleList != null) {
                        if (movieOriginalTitleList.size() != 0) {
                            try {
                                myViewHolder.titleTV.setText(movieOriginalTitleList.get(i));
                            } catch (Exception e) {
                                Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                            }
                        }
                    }
                }
            }
        }

        if (movieYearList != null) {
            if (movieYearList.size() != 0) {

                if (!movieYearList.get(i).equals("N/A")) {
                    try {
                        String[] dateList;
                        dateList = movieYearList.get(i).split("-");

                        myViewHolder.yearTV.setText(dateList[0]);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (moviePosterList != null) {
            if (moviePosterList.size() != 0) {

                if (!moviePosterList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + moviePosterList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w154_poster_image_url) + moviePosterList.get(i);
                        }

                        Log.d("MartianDeveloper", formattedPoster);

                        Glide.with(mContext)
                                .load(formattedPoster)
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .dontAnimate()
                                .dontTransform()
                                .placeholder(R.drawable.image_placeholder)
                                .priority(Priority.IMMEDIATE)
                                .into(myViewHolder.posterIV);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieTitleList.size();
    }
}
