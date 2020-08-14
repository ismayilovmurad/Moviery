package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_seasons_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailSeasons;

import java.util.List;

public class TvSeasonsAdapter extends RecyclerView.Adapter<TvSeasonsViewHolder> {

    private final Context mContext;

    private final List<TvDetailSeasons> tvSeasonsList;

    public TvSeasonsAdapter(Context mContext, List<TvDetailSeasons> tvSeasonsList) {
        this.mContext = mContext;
        this.tvSeasonsList = tvSeasonsList;
    }

    @NonNull
    @Override
    public TvSeasonsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_seasons, viewGroup, false);

        return new TvSeasonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvSeasonsViewHolder myViewHolder, final int i) {

        if (tvSeasonsList != null) {
            if (tvSeasonsList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(tvSeasonsList.get(i).getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.posterIV);

                    myViewHolder.nameTV.setText(tvSeasonsList.get(i).getName());

                    String formattedDate = "(" + tvSeasonsList.get(i).getAir_date() + ")";
                    myViewHolder.dateTV.setText(formattedDate);

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
        return tvSeasonsList.size();
    }
}
