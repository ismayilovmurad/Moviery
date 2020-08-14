package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvImage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class TvEnlargedSliderAdapter extends SliderViewAdapter<TvEnlargedSliderAdapter.SliderAdapterVH> {

    private final Context mContext;
    private final ArrayList<TvImage> tvImageList;

    public TvEnlargedSliderAdapter(Context mContext, ArrayList<TvImage> tvImageList) {
        this.mContext = mContext;
        this.tvImageList = tvImageList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_slider_item_image_enlarged, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final TvEnlargedSliderAdapter.SliderAdapterVH viewHolder, final int i) {

        if (tvImageList != null) {
            if (tvImageList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(tvImageList.get(i).getFile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
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
        }
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

