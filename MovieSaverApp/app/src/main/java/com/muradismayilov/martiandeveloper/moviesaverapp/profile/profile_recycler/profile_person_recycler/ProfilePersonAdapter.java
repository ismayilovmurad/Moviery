package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_person_recycler;

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

public class ProfilePersonAdapter extends RecyclerView.Adapter<ProfilePersonViewHolder> {

    private final Context mContext;

    private final List<String> actorNameList, actorProfileList;

    public ProfilePersonAdapter(Context mContext, List<String> actorNameList, List<String> actorProfileList) {
        this.mContext = mContext;

        this.actorNameList = actorNameList;
        this.actorProfileList = actorProfileList;
    }

    @NonNull
    @Override
    public ProfilePersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_person, viewGroup, false);

        return new ProfilePersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePersonViewHolder myViewHolder, final int i) {

        if (actorNameList != null) {
            if (actorNameList.size() != 0) {

                if (!actorNameList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.nameTV.setText(actorNameList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (actorProfileList != null) {
            if (actorProfileList.size() != 0) {

                if (!actorProfileList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.h632_profile_image_url) + actorProfileList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w185_profile_image_url) + actorProfileList.get(i);
                        }

                        Glide.with(mContext)
                                .load(formattedPoster)
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                .dontAnimate()
                                .dontTransform()
                                .placeholder(R.drawable.image_placeholder)
                                .priority(Priority.IMMEDIATE)
                                .into(myViewHolder.profileIV);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return actorNameList.size();
    }
}
