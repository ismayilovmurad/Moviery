package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_cast_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvCast;

import java.util.List;

public class TvCastAdapter extends RecyclerView.Adapter<TvCastViewHolder> {

    private final Context mContext;

    private final List<TvCast> tvCastList;

    public TvCastAdapter(Context mContext, List<TvCast> tvCastList) {
        this.mContext = mContext;
        this.tvCastList = tvCastList;
    }

    @NonNull
    @Override
    public TvCastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.tv_recycler_item_cast, viewGroup, false);

        return new TvCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvCastViewHolder myViewHolder, final int i) {

        if (tvCastList != null) {
            if (tvCastList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvCastList.get(i).getProfile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(myViewHolder.profileIV);

                    myViewHolder.nameTV.setText(tvCastList.get(i).getName());

                    String formattedCharacter = "(" + tvCastList.get(i).getCharacter() + ")";
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
        return tvCastList.size();
    }
}
