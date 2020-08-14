package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_activity.PersonDetailActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_activity.SearchActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchPerson;

import java.util.List;

public class SearchPersonAdapter extends RecyclerView.Adapter<SearchPersonViewHolder> {

    private final Context mContext;
    private final List<SearchPerson> searchPersonList;

    private final FirebaseAnalytics mFirebaseAnalytics;

    public SearchPersonAdapter(Context mContext, List<SearchPerson> searchPersonList) {
        this.mContext = mContext;
        this.searchPersonList = searchPersonList;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
    }

    @NonNull
    @Override
    public SearchPersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.search_recycler_item_person, viewGroup, false);

        return new SearchPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchPersonViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {

        if (i % 2 == 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END;
            myViewHolder.mainCL.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.START;
            myViewHolder.mainCL.setLayoutParams(params);
        }

        if (searchPersonList != null) {
            if (searchPersonList.size() != 0) {
                try {

                    Glide.with(mContext)
                            .load(searchPersonList.get(i).getProfile_path())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(R.drawable.image_placeholder)
                            .priority(Priority.IMMEDIATE)
                            .into(myViewHolder.posterIV);

                    myViewHolder.titleTV.setText(searchPersonList.get(i).getName());

                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
                    myViewHolder.mainCL.setAnimation(animation);

                    myViewHolder.mainCL.setOnClickListener(v -> {
                        Bundle params = new Bundle();
                        params.putInt("ButtonID", v.getId());
                        String buttonName = "search_personItem";

                        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                        assert connectivityManager != null;
                        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                            goToDetail(i, myViewHolder);

                        } else {
                            try {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                if (e.getLocalizedMessage() != null) {
                                    Log.d("MartianDeveloper", e.getLocalizedMessage());
                                } else {
                                    Log.d("MartianDeveloper", "I do not know!");
                                }
                            }
                        }

                        mFirebaseAnalytics.logEvent(buttonName, params);
                    });
                } catch (Exception e) {
                    if (e.getLocalizedMessage() != null) {
                        Log.d("MartianDeveloper", "Error: " + e.getLocalizedMessage());
                    } else {
                        Log.d("MartianDeveloper", "Error: in the onBindViewHolder, MovieProductionAdapter");
                    }
                }
            }
        }
    }

    private void goToDetail(int i, SearchPersonViewHolder myViewHolder) {
        try {
            Intent intent = new Intent(mContext, PersonDetailActivity.class);
            intent.putExtra("id", searchPersonList.get(i).getId());
            intent.putExtra("name", searchPersonList.get(i).getName());
            intent.putExtra("profilePath", searchPersonList.get(i).getProfile_pathForDB());

            @SuppressWarnings("unchecked")
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    SearchActivity.mActivity,
                    new Pair<>(myViewHolder.itemView.findViewById(R.id.posterIV),
                            PersonDetailActivity.VIEW_NAME_HEADER_IMAGE));

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

    @Override
    public int getItemCount() {
        return searchPersonList.size();
    }
}
