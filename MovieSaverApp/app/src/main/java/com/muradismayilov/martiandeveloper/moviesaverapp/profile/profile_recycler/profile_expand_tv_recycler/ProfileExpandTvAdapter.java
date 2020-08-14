package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_expand_tv_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityTvDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileExpandTvAdapter extends RecyclerView.Adapter<ProfileExpandTvViewHolder> {

    private final Context mContext;

    private final List<String> tvTitleList, tvYearList,
            tvPosterList, tvIdList, tvOriginalTitleList;

    private final List<String> tvTitleListFull, tvYearListFull,
            tvPosterListFull, tvIdListFull, tvOriginalTitleListFull;

    private final String type;

    public ProfileExpandTvAdapter(Context mContext, List<String> tvTitleList, List<String> tvYearList, List<String> tvPosterList, List<String> tvIdList, List<String> tvOriginalTitleList, String type) {

        this.mContext = mContext;
        this.tvTitleList = tvTitleList;
        this.tvYearList = tvYearList;
        this.tvPosterList = tvPosterList;
        this.tvIdList = tvIdList;
        this.tvOriginalTitleList = tvOriginalTitleList;

        tvTitleListFull = new ArrayList<>();
        tvTitleListFull.addAll(tvTitleList);

        tvYearListFull = new ArrayList<>();
        tvYearListFull.addAll(tvYearList);

        tvPosterListFull = new ArrayList<>();
        tvPosterListFull.addAll(tvPosterList);

        tvIdListFull = new ArrayList<>();
        tvIdListFull.addAll(tvIdList);

        tvOriginalTitleListFull = new ArrayList<>();
        tvOriginalTitleListFull.addAll(tvOriginalTitleList);

        this.type = type;
    }

    @NonNull
    @Override
    public ProfileExpandTvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_expand_tv, viewGroup, false);

        return new ProfileExpandTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileExpandTvViewHolder myViewHolder, final int i) {

        if (i % 2 == 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            myViewHolder.mainCL.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            myViewHolder.mainCL.setLayoutParams(params);
        }

        if (tvTitleList != null) {
            if (tvTitleList.size() != 0) {

                if (!tvTitleList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.titleTV.setText(tvTitleList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                } else {
                    if (tvOriginalTitleList.get(i) != null) {
                        try {
                            myViewHolder.titleTV.setText(tvOriginalTitleList.get(i));
                        } catch (Exception e) {
                            Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                        }
                    }
                }
            }
        }

        if (tvYearList != null) {
            if (tvYearList.size() != 0) {

                if (!tvYearList.get(i).equals("N/A")) {
                    try {
                        String[] dateList;
                        dateList = tvYearList.get(i).split("-");

                        myViewHolder.yearTV.setText(dateList[0]);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (tvPosterList != null) {
            if (tvPosterList.size() != 0) {

                if (!tvPosterList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w500_poster_image_url) + tvPosterList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + tvPosterList.get(i);
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
        return tvTitleList.size();
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        tvTitleList.clear();
        tvYearList.clear();
        tvPosterList.clear();
        tvIdList.clear();
        tvOriginalTitleList.clear();

        int index = -1;

        if (charText.length() == 0) {
            tvTitleList.addAll(tvTitleListFull);
            tvYearList.addAll(tvYearListFull);
            tvPosterList.addAll(tvPosterListFull);
            tvIdList.addAll(tvIdListFull);
            tvOriginalTitleList.addAll(tvOriginalTitleListFull);
        } else {
            for (String s : tvTitleListFull) {

                index++;

                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    tvTitleList.add(s);
                    tvYearList.add(tvYearListFull.get(index));
                    tvPosterList.add(tvPosterListFull.get(index));
                    tvIdList.add(tvIdListFull.get(index));
                    tvOriginalTitleList.add(tvOriginalTitleListFull.get(index));
                }
            }
        }
        notifyDataSetChanged();
    }

    private void goToDetail(int pos, ProfileExpandTvViewHolder myViewHolder) {
        try {
            Intent intent = new Intent(mContext, ProfileActivityTvDetail.class);
            intent.putExtra("id", Integer.valueOf(tvIdList.get(pos)));
            intent.putExtra("originalTitle", tvOriginalTitleList.get(pos));
            intent.putExtra("type", type);

            @SuppressWarnings("unchecked")
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    FeedActivity.mActivity,
                    new Pair<>(myViewHolder.itemView.findViewById(R.id.posterIV),
                            ProfileActivityTvDetail.VIEW_NAME_HEADER_IMAGE));

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