package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_recycler.person_tv_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonTv;

import java.util.List;

public class PersonTvAdapter extends RecyclerView.Adapter<PersonTvViewHolder> {

    private final Context mContext;

    private final List<PersonTv> personTvList;

    public PersonTvAdapter(Context mContext, List<PersonTv> personTvList) {
        this.mContext = mContext;
        this.personTvList = personTvList;
    }

    @NonNull
    @Override
    public PersonTvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.person_recycler_item_tv, viewGroup, false);

        return new PersonTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonTvViewHolder myViewHolder, final int i) {

        if (personTvList != null) {
            if (personTvList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(personTvList.get(i).getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.posterIV);

                    myViewHolder.titleTV.setText(personTvList.get(i).getName());
                    myViewHolder.vote_averageTV.setText(personTvList.get(i).getVoteAverage());

                    String formattedReleaseDate = "(" + personTvList.get(i).getFirst_air_date() + ")";
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
        return personTvList.size();
    }
}
