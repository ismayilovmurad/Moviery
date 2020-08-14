package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_production_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailProductionCompanies;

import java.util.List;

public class TvProductionAdapter extends RecyclerView.Adapter<TvProductionViewHolder> {

    private final Context mContext;

    private final List<TvDetailProductionCompanies> tvProductionCompaniesList;

    public TvProductionAdapter(Context mContext, List<TvDetailProductionCompanies> tvProductionCompaniesList) {
        this.mContext = mContext;
        this.tvProductionCompaniesList = tvProductionCompaniesList;
    }

    @NonNull
    @Override
    public TvProductionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_production, viewGroup, false);

        return new TvProductionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvProductionViewHolder myViewHolder, final int i) {

        if (tvProductionCompaniesList != null) {
            if (tvProductionCompaniesList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvProductionCompaniesList.get(i).getLogo_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.logoIV);

                    String formattedTitle = tvProductionCompaniesList.get(i).getName() + " (" + tvProductionCompaniesList.get(i).getOrigin_country() + ")";

                    myViewHolder.productionNameTV.setText(formattedTitle);

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
        return tvProductionCompaniesList.size();
    }
}
