package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_created_by_recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailCreatedBy;

import java.util.List;

public class TvCreatedByAdapter extends RecyclerView.Adapter<TvCreatedByViewHolder> {

    private final Context mContext;

    private final List<TvDetailCreatedBy> tvCreatedByList;

    public TvCreatedByAdapter(Context mContext, List<TvDetailCreatedBy> tvCreatedByList) {
        this.mContext = mContext;
        this.tvCreatedByList = tvCreatedByList;
    }

    @NonNull
    @Override
    public TvCreatedByViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_created_by, viewGroup, false);

        return new TvCreatedByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvCreatedByViewHolder myViewHolder, final int i) {

        if (tvCreatedByList != null) {
            if (tvCreatedByList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvCreatedByList.get(i).getProfile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.profileIV);

                    myViewHolder.nameTV.setText(tvCreatedByList.get(i).getName());

                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
                    myViewHolder.mainCL.setAnimation(animation);
                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    } else {
                        Log.d("MartianDeveloper", "Error: in the onBindViewHolder, MovieCastAdapter");
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return tvCreatedByList.size();
    }
}
