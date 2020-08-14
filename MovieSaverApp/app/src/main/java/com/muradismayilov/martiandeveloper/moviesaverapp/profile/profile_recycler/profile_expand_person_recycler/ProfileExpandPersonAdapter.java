package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_expand_person_recycler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityPersonDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileExpandPersonAdapter extends RecyclerView.Adapter<ProfileExpandPersonViewHolder> {

    private final Context mContext;

    private final List<String> personIdList, personNameList, personProfileList;

    private final List<String> personIdListFull, personNameListFull, personProfileListFull;

    public ProfileExpandPersonAdapter(Context mContext, List<String> personIdList, List<String> personNameList, List<String> personProfileList) {

        this.mContext = mContext;
        this.personIdList = personIdList;
        this.personNameList = personNameList;
        this.personProfileList = personProfileList;

        personIdListFull = new ArrayList<>();
        personIdListFull.addAll(personIdList);

        personNameListFull = new ArrayList<>();
        personNameListFull.addAll(personNameList);

        personProfileListFull = new ArrayList<>();
        personProfileListFull.addAll(personProfileList);
    }

    @NonNull
    @Override
    public ProfileExpandPersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_expand_person, viewGroup, false);

        return new ProfileExpandPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileExpandPersonViewHolder myViewHolder, final int i) {

        if (i % 2 == 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            myViewHolder.mainCL.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            myViewHolder.mainCL.setLayoutParams(params);
        }

        if (personNameList != null) {
            if (personNameList.size() != 0) {

                if (!personNameList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.titleTV.setText(personNameList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (personProfileList != null) {
            if (personProfileList.size() != 0) {

                if (!personProfileList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w500_poster_image_url) + personProfileList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + personProfileList.get(i);
                        }


                        Glide.with(mContext)
                                .load(formattedPoster)
                                .skipMemoryCache(false)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .fitCenter()
                                .centerCrop()
                                .placeholder(R.drawable.image_placeholder)
                                .priority(Priority.IMMEDIATE)
                                .into(myViewHolder.posterIV);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        myViewHolder.setItemClickListener((v, pos) -> goToDetail(pos, myViewHolder));
    }

    @Override
    public int getItemCount() {
        return personIdList.size();
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        personIdList.clear();
        personNameList.clear();
        personProfileList.clear();

        int index = -1;

        if (charText.length() == 0) {
            personIdList.addAll(personIdListFull);
            personNameList.addAll(personNameListFull);
            personProfileList.addAll(personProfileListFull);
        } else {
            for (String s : personNameListFull) {

                index++;

                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    personNameList.add(s);
                    personIdList.add(personIdListFull.get(index));
                    personProfileList.add(personProfileListFull.get(index));
                }
            }
        }
        notifyDataSetChanged();
    }

    private void goToDetail(int pos, ProfileExpandPersonViewHolder myViewHolder) {
        try {
            Intent intent = new Intent(mContext, ProfileActivityPersonDetail.class);
            intent.putExtra("id", Integer.valueOf(personIdList.get(pos)));
            intent.putExtra("name", personNameList.get(pos));

            @SuppressWarnings("unchecked")
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    FeedActivity.mActivity,
                    new Pair<>(myViewHolder.itemView.findViewById(R.id.posterIV),
                            ProfileActivityPersonDetail.VIEW_NAME_HEADER_IMAGE));

            ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());

        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d("MartianDeveloper", "Error" + e.getLocalizedMessage());
            } else {
                Log.d("MartianDeveloper", "I do not know!");
            }
            Toast.makeText(mContext, mContext.getResources().getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
        }
    }
}
