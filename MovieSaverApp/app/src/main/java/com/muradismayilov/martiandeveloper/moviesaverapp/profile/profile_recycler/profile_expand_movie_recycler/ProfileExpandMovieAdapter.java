package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_expand_movie_recycler;

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
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityMovieDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileExpandMovieAdapter extends RecyclerView.Adapter<ProfileExpandMovieViewHolder> {

    private final Context mContext;

    private final List<String> movieTitleList, movieYearList,
            moviePosterList, movieIdList, movieOriginalTitleList;

    private final List<String> movieTitleListFull, movieYearListFull,
            moviePosterListFull, movieIdListFull, movieOriginalTitleListFull;

    private final String type;

    public ProfileExpandMovieAdapter(Context mContext, List<String> movieTitleList, List<String> movieYearList, List<String> moviePosterList, List<String> movieIdList, List<String> movieOriginalTitleList, String type) {

        this.mContext = mContext;
        this.movieTitleList = movieTitleList;
        this.movieYearList = movieYearList;
        this.moviePosterList = moviePosterList;
        this.movieIdList = movieIdList;
        this.movieOriginalTitleList = movieOriginalTitleList;

        movieTitleListFull = new ArrayList<>();
        movieTitleListFull.addAll(movieTitleList);

        movieYearListFull = new ArrayList<>();
        movieYearListFull.addAll(movieYearList);

        moviePosterListFull = new ArrayList<>();
        moviePosterListFull.addAll(moviePosterList);

        movieIdListFull = new ArrayList<>();
        movieIdListFull.addAll(movieIdList);

        movieOriginalTitleListFull = new ArrayList<>();
        movieOriginalTitleListFull.addAll(movieOriginalTitleList);

        this.type = type;
    }

    @NonNull
    @Override
    public ProfileExpandMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.profile_recycler_item_expand_movie, viewGroup, false);

        return new ProfileExpandMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileExpandMovieViewHolder myViewHolder, final int i) {

        if (i % 2 == 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            myViewHolder.mainCL.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            myViewHolder.mainCL.setLayoutParams(params);
        }

        if (movieTitleList != null) {
            if (movieTitleList.size() != 0) {

                if (!movieTitleList.get(i).equals("N/A")) {
                    try {
                        myViewHolder.titleTV.setText(movieTitleList.get(i));
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                } else {
                    if (movieOriginalTitleList.get(i) != null) {
                        try {
                            myViewHolder.titleTV.setText(movieOriginalTitleList.get(i));
                        } catch (Exception e) {
                            Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                        }
                    }
                }
            }
        }

        if (movieYearList != null) {
            if (movieYearList.size() != 0) {

                if (!movieYearList.get(i).equals("N/A")) {
                    try {
                        String[] dateList;
                        dateList = movieYearList.get(i).split("-");

                        myViewHolder.yearTV.setText(dateList[0]);
                    } catch (Exception e) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    }
                }
            }
        }

        if (moviePosterList != null) {
            if (moviePosterList.size() != 0) {

                if (!moviePosterList.get(i).equals("N/A")) {
                    try {
                        String formattedPoster;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w500_poster_image_url) + moviePosterList.get(i);
                        } else {
                            formattedPoster = FeedActivity.mContext.getResources().getString(R.string.w342_poster_image_url) + moviePosterList.get(i);
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
        return movieIdList.size();
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        movieTitleList.clear();
        movieYearList.clear();
        moviePosterList.clear();
        movieIdList.clear();
        movieOriginalTitleList.clear();

        int index = -1;

        if (charText.length() == 0) {
            movieTitleList.addAll(movieTitleListFull);
            movieYearList.addAll(movieYearListFull);
            moviePosterList.addAll(moviePosterListFull);
            movieIdList.addAll(movieIdListFull);
            movieOriginalTitleList.addAll(movieOriginalTitleListFull);
        } else {
            for (String s : movieTitleListFull) {

                index++;

                if (s.toLowerCase(Locale.getDefault()).contains(charText)) {
                    movieTitleList.add(s);
                    movieYearList.add(movieYearListFull.get(index));
                    moviePosterList.add(moviePosterListFull.get(index));
                    movieIdList.add(movieIdListFull.get(index));
                    movieOriginalTitleList.add(movieOriginalTitleListFull.get(index));
                }
            }
        }
        notifyDataSetChanged();
    }

    private void goToDetail(int pos, ProfileExpandMovieViewHolder myViewHolder) {
        try {
            Intent intent = new Intent(mContext, ProfileActivityMovieDetail.class);
            intent.putExtra("id", Integer.valueOf(movieIdList.get(pos)));
            intent.putExtra("originalTitle", movieOriginalTitleList.get(pos));
            intent.putExtra("type", type);

            @SuppressWarnings("unchecked")
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    FeedActivity.mActivity,
                    new Pair<>(myViewHolder.itemView.findViewById(R.id.posterIV),
                            ProfileActivityMovieDetail.VIEW_NAME_HEADER_IMAGE));

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
