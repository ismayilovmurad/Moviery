package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_activity.TvFullScreenImageActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvImage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TvSliderAdapter extends SliderViewAdapter<TvSliderAdapter.SliderAdapterVH> {

    private final Context mContext;
    private final List<TvImage> tvImageList;

    public TvSliderAdapter(Context mContext, List<TvImage> tvImageList) {
        this.mContext = mContext;
        this.tvImageList = tvImageList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_slider_item_image, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final TvSliderAdapter.SliderAdapterVH viewHolder, final int i) {

        if (tvImageList != null) {
            if (tvImageList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvImageList.get(i).getFile_path())
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .fitCenter()
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.IMMEDIATE)
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
        assert tvImageList != null;
        ArrayList<TvImage> myList = new ArrayList<>(tvImageList);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myList", myList);

        Intent intent = new Intent(mContext, TvFullScreenImageActivity.class);
        intent.putExtras(bundle);

        //noinspection unchecked
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) mContext,
                new Pair<>(view,
                        TvFullScreenImageActivity.VIEW_NAME_HEADER_IMAGE));

        ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());

    }

    @Override
    public int getCount() {
        return tvImageList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        final ImageView imageIV;

        SliderAdapterVH(View itemView) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.imageIV);
        }
    }
}
