package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_tools;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_activity.PersonFullScreenImageActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonImage;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonSliderAdapter extends SliderViewAdapter<PersonSliderAdapter.SliderAdapterVH> {

    private final Context mContext;
    private final List<PersonImage> personImageList;

    public PersonSliderAdapter(Context mContext, List<PersonImage> personImageList) {
        this.mContext = mContext;
        this.personImageList = personImageList;
    }

    @Override
    public PersonSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_slider_item_image, null);
        return new PersonSliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(final PersonSliderAdapter.SliderAdapterVH viewHolder, final int i) {

        if (personImageList != null) {
            if (personImageList.size() != 0) {
                try {
                    Glide.with(mContext)
                            .load(personImageList.get(i).getFile_path())
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .fitCenter()
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.LOW)
                            .into(viewHolder.imageIV);

                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    } else {
                        Log.d("MartianDeveloper", "Error: in the onBindViewHolder, PersonSliderAdapter");
                    }
                }
            }

            viewHolder.imageIV.setOnClickListener(this::enlargeImageSlider);
        }
    }

    private void enlargeImageSlider(View view) {
        assert personImageList != null;
        ArrayList<PersonImage> myList = new ArrayList<>(personImageList);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myList", myList);

        Intent intent = new Intent(mContext, PersonFullScreenImageActivity.class);
        intent.putExtras(bundle);

        //noinspection unchecked
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) mContext,
                new Pair<>(view,
                        PersonFullScreenImageActivity.VIEW_NAME_HEADER_IMAGE));

        ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());

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
