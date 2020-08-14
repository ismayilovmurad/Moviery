package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_networks_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailNetworks;

import java.util.List;

public class TvNetworksAdapter extends RecyclerView.Adapter<TvNetworksViewHolder> {

    private final Context mContext;

    private final List<TvDetailNetworks> tvNetworksList;

    public TvNetworksAdapter(Context mContext, List<TvDetailNetworks> tvNetworksList) {
        this.mContext = mContext;
        this.tvNetworksList = tvNetworksList;
    }

    @NonNull
    @Override
    public TvNetworksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_networks, viewGroup, false);

        return new TvNetworksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvNetworksViewHolder myViewHolder, final int i) {

        if (tvNetworksList != null) {
            if (tvNetworksList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvNetworksList.get(i).getLogo_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.logoIV);

                    String formattedTitle = tvNetworksList.get(i).getName() + " (" + tvNetworksList.get(i).getOrigin_country() + ")";

                    myViewHolder.networkNameTV.setText(formattedTitle);

                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
                    myViewHolder.mainLL.setAnimation(animation);
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
        return tvNetworksList.size();
    }
}
