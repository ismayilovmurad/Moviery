package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_similar_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieSimilar;

import java.util.List;

public class MovieSimilarAdapter extends RecyclerView.Adapter<MovieSimilarViewHolder> {

    private final Context mContext;

    private final List<MovieSimilar> movieSimilarList;

    public MovieSimilarAdapter(Context mContext, List<MovieSimilar> movieSimilarList) {
        this.mContext = mContext;
        this.movieSimilarList = movieSimilarList;
    }

    @NonNull
    @Override
    public MovieSimilarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.movie_recycler_item_similar, viewGroup, false);

        return new MovieSimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSimilarViewHolder myViewHolder, final int i) {

        if (movieSimilarList != null) {
            if (movieSimilarList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(movieSimilarList.get(i).getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.posterIV);

                    myViewHolder.titleTV.setText(movieSimilarList.get(i).getTitle());
                    myViewHolder.rateTV.setText(movieSimilarList.get(i).getVoteAverage());
                    String formattedYear = "(" + movieSimilarList.get(i).getRelease_date() + ")";
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
        return movieSimilarList.size();
    }
}
