package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_activity.MovieFullScreenImageActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieImage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MovieSliderAdapter extends SliderViewAdapter<MovieSliderAdapter.SliderAdapterVH> {

    private final Context mContext;
    private final List<MovieImage> movieImageList;

    public MovieSliderAdapter(Context mContext, List<MovieImage> movieImageList) {
        this.mContext = mContext;
        this.movieImageList = movieImageList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_slider_item_image, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final SliderAdapterVH viewHolder, final int i) {

        if (movieImageList != null) {
            if (movieImageList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(movieImageList.get(i).getFile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(viewHolder.imageIV);

                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    } else {
                        Log.d("MartianDeveloper", "Error: in the onBindViewHolder, MovieSimilarAdapter");
                    }
                }
            }

            viewHolder.imageIV.setOnClickListener(this::enlargeImageSlider);
        }
    }

    private void enlargeImageSlider(View view) {
        assert movieImageList != null;
        ArrayList<MovieImage> myList = new ArrayList<>(movieImageList);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myList", myList);

        Intent intent = new Intent(mContext, MovieFullScreenImageActivity.class);
        intent.putExtras(bundle);

        //noinspection unchecked
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) mContext,
                new Pair<>(view,
                        MovieFullScreenImageActivity.VIEW_NAME_HEADER_IMAGE));

        ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());

    }

    @Override
    public int getCount() {
        return movieImageList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        final ImageView imageIV;

        SliderAdapterVH(View itemView) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.imageIV);
        }
    }
}
