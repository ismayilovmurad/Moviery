package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_production_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieDetailProductionCompanies;

import java.util.List;

public class MovieProductionAdapter extends RecyclerView.Adapter<MovieProductionViewHolder> {

    private final Context mContext;

    private final List<MovieDetailProductionCompanies> movieProductionCompaniesList;

    public MovieProductionAdapter(Context mContext, List<MovieDetailProductionCompanies> movieProductionCompaniesList) {
        this.mContext = mContext;
        this.movieProductionCompaniesList = movieProductionCompaniesList;
    }

    @NonNull
    @Override
    public MovieProductionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.movie_recycler_item_production, viewGroup, false);

        return new MovieProductionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieProductionViewHolder myViewHolder, final int i) {

        if (movieProductionCompaniesList != null) {
            if (movieProductionCompaniesList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(movieProductionCompaniesList.get(i).getLogo_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.logoIV);

                    String formattedTitle = movieProductionCompaniesList.get(i).getName() + " (" + movieProductionCompaniesList.get(i).getOrigin_country() + ")";

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
        return movieProductionCompaniesList.size();
    }
}
