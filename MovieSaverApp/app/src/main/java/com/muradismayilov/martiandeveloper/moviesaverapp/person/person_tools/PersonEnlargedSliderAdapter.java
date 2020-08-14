package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_tools;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonImage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class PersonEnlargedSliderAdapter extends SliderViewAdapter<PersonEnlargedSliderAdapter.SliderAdapterVH> {

    private final Context mContext;
    private final ArrayList<PersonImage> personImageList;

    public PersonEnlargedSliderAdapter(Context mContext, ArrayList<PersonImage> personImageList) {
        this.mContext = mContext;
        this.personImageList = personImageList;
    }

    @Override
    public PersonEnlargedSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_slider_item_image_enlarged, null);
        return new PersonEnlargedSliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final PersonEnlargedSliderAdapter.SliderAdapterVH viewHolder, final int i) {

        if (personImageList != null) {
            if (personImageList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(personImageList.get(i).getFile_path())
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
        return personImageList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        final ImageView imageIV;

        SliderAdapterVH(View itemView) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.imageIV);
        }
    }
}
