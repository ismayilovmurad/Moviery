package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_tv_recycler;

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

public class ProfileTvAdapter extends RecyclerView.Adapter<ProfileTvViewHolder> {

    private final Context mContext;

    private final List<String> tvTitleList, tvYearList,
            tvPosterList, tvOriginalTitleList;

    public ProfileTvAdapter(Context mContext, List<String> tvTitleList, List<String> tvYearList, List<String> tvPosterList, List<String> tvOriginalTitleList) {
        this.mContext = mContext;
        this.tvTitleList = tvTitleList;
        this.tvYearList = tvYearList;
        this.tvPosterList = tvPosterList;
        this.tvOriginalTitleList = tvOriginalTitleList;
    }

    @NonNull
    @Override
    public ProfileTvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_tv, viewGroup, false);

        return new ProfileTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTvViewHolder myViewHolder, final int i) {

        if (tvTitleList != null) {
            if (tvTitleList.size() != 0) {

                if (!tvTitleList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.titleTV.setText(tvTitleList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                } else {
                    if (tvOriginalTitleList != null) {
                        if (tvOriginalTitleList.size() != 0) {
                            try {
                                myViewHolder.titleTV.setText(tvOriginalTitleList.get(i));
                            } catch (Exception e) {
                                Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                            }
                        }
                    }
                }
            }
        }

        if (tvYearList != null) {
            if (tvYearList.size() != 0) {

                if (!tvYearList.get(i).equals("N/A")) {
                    try {
                        String[] dateList;
                        dateList = tvYearList.get(i).split("-");

                        myViewHolder.yearTV.setText(dateList[0]);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (tvPosterList != null) {
            if (tvPosterList.size() != 0) {

                if (!tvPosterList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + tvPosterList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w154_poster_image_url) + tvPosterList.get(i);
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
        return tvTitleList.size();
    }
}
