package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_cast_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieCast;

import java.util.List;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastViewHolder> {

    private final Context mContext;

    private final List<MovieCast> movieCastList;

    public MovieCastAdapter(Context mContext, List<MovieCast> movieCastList) {
        this.mContext = mContext;
        this.movieCastList = movieCastList;
    }

    @NonNull
    @Override
    public MovieCastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.movie_recycler_item_cast, viewGroup, false);

        return new MovieCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastViewHolder myViewHolder, final int i) {

        if (movieCastList != null) {
            if (movieCastList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(movieCastList.get(i).getProfile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.profileIV);

                    myViewHolder.nameTV.setText(movieCastList.get(i).getName());

                    String formattedCharacter = "(" + movieCastList.get(i).getCharacter() + ")";
                    myViewHolder.characterTV.setText(formattedCharacter);

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
        return movieCastList.size();
    }
}
