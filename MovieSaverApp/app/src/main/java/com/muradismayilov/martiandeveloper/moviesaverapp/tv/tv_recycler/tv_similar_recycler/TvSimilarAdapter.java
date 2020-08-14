package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_similar_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvSimilar;

import java.util.List;

public class TvSimilarAdapter extends RecyclerView.Adapter<TvSimilarViewHolder> {

    private final Context mContext;

    private final List<TvSimilar> tvSimilarList;

    public TvSimilarAdapter(Context mContext, List<TvSimilar> tvSimilarList) {
        this.mContext = mContext;
        this.tvSimilarList = tvSimilarList;
    }

    @NonNull
    @Override
    public TvSimilarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_similar, viewGroup, false);

        return new TvSimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvSimilarViewHolder myViewHolder, final int i) {

        if (tvSimilarList != null) {
            if (tvSimilarList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvSimilarList.get(i).getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.posterIV);

                    myViewHolder.titleTV.setText(tvSimilarList.get(i).getName());
                    myViewHolder.rateTV.setText(tvSimilarList.get(i).getVoteAverage());
                    String formattedYear = "(" + tvSimilarList.get(i).getFirst_air_date() + ")";
                    myViewHolder.yearTV.setText(formattedYear);

                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
                    myViewHolder.mainCL.setAnimation(animation);
                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    } else {
                        Log.d("MartianDeveloper", "Error: in the onBindViewHolder, MovieProductionAdapter");
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return tvSimilarList.size();
    }
}
