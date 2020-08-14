package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_recycler.person_movie_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonMovie;

import java.util.List;

public class PersonMovieAdapter extends RecyclerView.Adapter<PersonMovieViewHolder> {

    private final Context mContext;

    private final List<PersonMovie> personMovieList;

    public PersonMovieAdapter(Context mContext, List<PersonMovie> personMovieList) {
        this.mContext = mContext;
        this.personMovieList = personMovieList;
    }

    @NonNull
    @Override
    public PersonMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.person_recycler_item_movie, viewGroup, false);

        return new PersonMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonMovieViewHolder myViewHolder, final int i) {

        if (personMovieList != null) {
            if (personMovieList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(personMovieList.get(i).getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.posterIV);

                    myViewHolder.titleTV.setText(personMovieList.get(i).getTitle());
                    myViewHolder.vote_averageTV.setText(personMovieList.get(i).getVoteAverage());

                    String formattedReleaseDate = "(" + personMovieList.get(i).getRelease_date() + ")";
                    myViewHolder.releaseDateTV.setText(formattedReleaseDate);

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
        return personMovieList.size();
    }
}
