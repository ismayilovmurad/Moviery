package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityMovie;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityPerson;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity.ProfileActivityTv;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_movie_recycler.ProfileMovieAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_person_recycler.ProfilePersonAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_tv_recycler.ProfileTvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions")
public class ProfileFragment extends Fragment implements View.OnClickListener {

    // Layout
    @BindView(R.id.mainCL)
    ConstraintLayout mainCL;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.my_profile_no_internetCL)
    ConstraintLayout my_profile_no_internetCL;
    // Button
    @BindView(R.id.my_profile_refreshBTN)
    Button my_profile_refreshBTN;
    // ProgressBar
    @BindView(R.id.mainPB)
    ProgressBar mainPB;
    // RecyclerView
    @BindView(R.id.myMoviesRV)
    RecyclerView myMoviesRV;
    @BindView(R.id.myTvRV)
    RecyclerView myTvRV;
    @BindView(R.id.favoriteActorsRV)
    RecyclerView favoriteActorsRV;
    // Button
    @BindView(R.id.myMoviesExpandBTN)
    Button myMoviesExpandBTN;
    @BindView(R.id.myTvExpandBTN)
    Button myTvExpandBTN;
    @BindView(R.id.favoriteActorsExpandBTN)
    Button favoriteActorsExpandBTN;
    // RadioGroup
    @BindView(R.id.myMoviesRG)
    RadioGroup myMoviesRG;
    @BindView(R.id.myTvRG)
    RadioGroup myTvRG;
    // RadioButton
    @BindView(R.id.myMoviesWatchedRBTN)
    RadioButton myMoviesWatchedRBTN;
    @BindView(R.id.myTvWatchedRBTN)
    RadioButton myTvWatchedRBTN;
    // TextView
    @BindView(R.id.myMoviesTV)
    TextView myMoviesTV;
    @BindView(R.id.myTvTV)
    TextView myTvTV;
    @BindView(R.id.favoriteActorsTV)
    TextView favoriteActorsTV;


    // Strings
    // Error
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;

    // Variables
    // Firebase
    private FirebaseUser mUser;
    // String
    private String LOG;
    // List
    // Movie
    private List<String> watchedMovieTitleList, watchedMovieYearList,
            watchedMoviePosterList, watchedMovieIdList, watchedMovieOriginalTitleList;

    private List<String> watchLaterMovieTitleList, watchLaterMovieYearList,
            watchLaterMoviePosterList, watchLaterMovieIdList, watchLaterMovieOriginalTitleList;

    private List<String> favoriteMovieTitleList, favoriteMovieYearList,
            favoriteMoviePosterList, favoriteMovieIdList, favoriteMovieOriginalTitleList;

    // Tv
    private List<String> watchedTvTitleList, watchedTvYearList,
            watchedTvPosterList, watchedTvIdList, watchedTvOriginalTitleList;

    private List<String> watchLaterTvTitleList, watchLaterTvYearList,
            watchLaterTvPosterList, watchLaterTvIdList, watchLaterTvOriginalTitleList;

    private List<String> favoriteTvTitleList, favoriteTvYearList,
            favoriteTvPosterList, favoriteTvIdList, favoriteTvOriginalTitleList;

    // Actor
    private List<String> favoriteActorNameList, favoriteActorProfileList, favoriteActorIdList;

    private String movieType, tvType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ButterKnife.bind(this, view);
        initialFunctions();
    }

    private void initialFunctions() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTwo));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));

        declareVariables();
        setListeners();
        checkInternetConnectionForWatchedMovie();
        checkInternetConnectionForWatchedTv();
        checkInternetConnectionForFavoriteActor();
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // String
        LOG = "MartianDeveloper";
        movieType = "Watched";
        tvType = "Watched";

        // List
        // Movie
        watchedMovieTitleList = new ArrayList<>();
        watchedMovieYearList = new ArrayList<>();
        watchedMoviePosterList = new ArrayList<>();
        watchedMovieIdList = new ArrayList<>();
        watchedMovieOriginalTitleList = new ArrayList<>();

        watchLaterMovieTitleList = new ArrayList<>();
        watchLaterMovieYearList = new ArrayList<>();
        watchLaterMoviePosterList = new ArrayList<>();
        watchLaterMovieIdList = new ArrayList<>();
        watchLaterMovieOriginalTitleList = new ArrayList<>();

        favoriteMovieTitleList = new ArrayList<>();
        favoriteMovieYearList = new ArrayList<>();
        favoriteMoviePosterList = new ArrayList<>();
        favoriteMovieIdList = new ArrayList<>();
        favoriteMovieOriginalTitleList = new ArrayList<>();

        // Tv
        watchedTvTitleList = new ArrayList<>();
        watchedTvYearList = new ArrayList<>();
        watchedTvPosterList = new ArrayList<>();
        watchedTvIdList = new ArrayList<>();
        watchedTvOriginalTitleList = new ArrayList<>();

        watchLaterTvTitleList = new ArrayList<>();
        watchLaterTvYearList = new ArrayList<>();
        watchLaterTvPosterList = new ArrayList<>();
        watchLaterTvIdList = new ArrayList<>();
        watchLaterTvOriginalTitleList = new ArrayList<>();

        favoriteTvTitleList = new ArrayList<>();
        favoriteTvYearList = new ArrayList<>();
        favoriteTvPosterList = new ArrayList<>();
        favoriteTvIdList = new ArrayList<>();
        favoriteTvOriginalTitleList = new ArrayList<>();

        favoriteActorNameList = new ArrayList<>();
        favoriteActorProfileList = new ArrayList<>();
        favoriteActorIdList = new ArrayList<>();

        // Movie
        LinearLayoutManager myMoviesLinearLayoutManager = new LinearLayoutManager(FeedActivity.mContext);
        myMoviesLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myMoviesRV.setLayoutManager(myMoviesLinearLayoutManager);

        // Tv
        LinearLayoutManager myTvLinearLayoutManager = new LinearLayoutManager(FeedActivity.mContext);
        myTvLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myTvRV.setLayoutManager(myTvLinearLayoutManager);

        // Actor
        LinearLayoutManager favoriteActorLinearLayoutManager = new LinearLayoutManager(FeedActivity.mContext);
        favoriteActorLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        favoriteActorsRV.setLayoutManager(favoriteActorLinearLayoutManager);

        // Adapter
        // Movie
        RecyclerView.Adapter watchedMovieAdapter = new ProfileMovieAdapter(FeedActivity.mContext, watchedMovieTitleList, watchedMovieYearList, watchedMoviePosterList, watchedMovieOriginalTitleList);
        myMoviesRV.setAdapter(watchedMovieAdapter);

        // Tv
        RecyclerView.Adapter watchedTvAdapter = new ProfileTvAdapter(FeedActivity.mContext, watchedTvTitleList, watchedTvYearList, watchedTvPosterList, watchedTvOriginalTitleList);
        myTvRV.setAdapter(watchedTvAdapter);

        // Tv
        RecyclerView.Adapter favoriteActorAdapter = new ProfilePersonAdapter(FeedActivity.mContext, favoriteActorNameList, favoriteActorProfileList);
        favoriteActorsRV.setAdapter(favoriteActorAdapter);

    }

    // Movie
    private void checkInternetConnectionForWatchedMovie() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getWatchedMoviesFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForWatchLaterMovie() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getWatchLaterMoviesFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForFavoriteMovie() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getFavoriteMoviesFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    // Tv
    private void checkInternetConnectionForWatchedTv() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getWatchedTvFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForWatchLaterTv() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getWatchLaterTvFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForFavoriteTv() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getFavoriteTvFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    // Actor
    private void checkInternetConnectionForFavoriteActor() {
        try {
            //noinspection ConstantConditions
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                my_profile_no_internetCL.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getFavoriteActorFromDB();

            } else {
                my_profile_no_internetCL.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                hideProgress();
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void setListeners() {

        my_profile_refreshBTN.setOnClickListener(this);

        // Movie
        myMoviesExpandBTN.setOnClickListener(this);
        // Tv
        myTvExpandBTN.setOnClickListener(this);
        // Actor
        favoriteActorsExpandBTN.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(() -> {

            // Movie
            myMoviesWatchedRBTN.setChecked(true);

            // Tv
            myTvWatchedRBTN.setChecked(true);

            // Actor
            checkInternetConnectionForFavoriteActor();

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });

        myMoviesRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.myMoviesWatchedRBTN:
                    movieType = "Watched";
                    checkInternetConnectionForWatchedMovie();
                    break;
                case R.id.myMoviesWatchLaterRBTN:
                    movieType = "Watch Later";
                    checkInternetConnectionForWatchLaterMovie();
                    break;
                case R.id.myMoviesFavoriteRBTN:
                    movieType = "Favorites";
                    checkInternetConnectionForFavoriteMovie();
                    break;
            }
        });

        myTvRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.myTvWatchedRBTN:
                    tvType = "Watched";
                    checkInternetConnectionForWatchedTv();
                    break;
                case R.id.myTvWatchLaterRBTN:
                    tvType = "Watch Later";
                    checkInternetConnectionForWatchLaterTv();
                    break;
                case R.id.myTvFavoriteRBTN:
                    tvType = "Favorites";
                    checkInternetConnectionForFavoriteTv();
                    break;
            }
        });
    }

    // Movie
    private void getWatchedMoviesFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Movie")
                        .child("Watched");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        watchedMovieTitleList.clear();

                        watchedMovieYearList.clear();

                        watchedMoviePosterList.clear();

                        watchedMovieIdList.clear();

                        watchedMovieOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                watchedMovieTitleList.add(ds.child("title").getValue() + "");
                                watchedMovieYearList.add(ds.child("release_date").getValue() + "");
                                watchedMoviePosterList.add(ds.child("poster_path").getValue() + "");
                                watchedMovieIdList.add(ds.child("id").getValue() + "");
                                watchedMovieOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myMoviesTV.setVisibility(View.VISIBLE);
                                myMoviesExpandBTN.setVisibility(View.GONE);
                            } else {
                                myMoviesTV.setVisibility(View.GONE);
                                myMoviesExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(watchedMovieTitleList);
                        Collections.reverse(watchedMovieYearList);
                        Collections.reverse(watchedMoviePosterList);
                        Collections.reverse(watchedMovieIdList);
                        Collections.reverse(watchedMovieOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileMovieAdapter(FeedActivity.mContext, watchedMovieTitleList, watchedMovieYearList, watchedMoviePosterList, watchedMovieOriginalTitleList);
                        myMoviesRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void getWatchLaterMoviesFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Movie")
                        .child("Watch Later");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        watchLaterMovieTitleList.clear();

                        watchLaterMovieYearList.clear();

                        watchLaterMoviePosterList.clear();

                        watchLaterMovieIdList.clear();

                        watchLaterMovieOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                watchLaterMovieTitleList.add(ds.child("title").getValue() + "");
                                watchLaterMovieYearList.add(ds.child("release_date").getValue() + "");
                                watchLaterMoviePosterList.add(ds.child("poster_path").getValue() + "");
                                watchLaterMovieIdList.add(ds.child("id").getValue() + "");
                                watchLaterMovieOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myMoviesTV.setVisibility(View.VISIBLE);
                                myMoviesExpandBTN.setVisibility(View.GONE);
                            } else {
                                myMoviesTV.setVisibility(View.GONE);
                                myMoviesExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(watchLaterMovieTitleList);
                        Collections.reverse(watchLaterMovieYearList);
                        Collections.reverse(watchLaterMoviePosterList);
                        Collections.reverse(watchLaterMovieIdList);
                        Collections.reverse(watchLaterMovieOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileMovieAdapter(FeedActivity.mContext, watchLaterMovieTitleList, watchLaterMovieYearList, watchLaterMoviePosterList, watchLaterMovieOriginalTitleList);
                        myMoviesRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void getFavoriteMoviesFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Movie")
                        .child("Favorites");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        favoriteMovieTitleList.clear();

                        favoriteMovieYearList.clear();

                        favoriteMoviePosterList.clear();

                        favoriteMovieIdList.clear();

                        favoriteMovieOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                favoriteMovieTitleList.add(ds.child("title").getValue() + "");
                                favoriteMovieYearList.add(ds.child("release_date").getValue() + "");
                                favoriteMoviePosterList.add(ds.child("poster_path").getValue() + "");
                                favoriteMovieIdList.add(ds.child("id").getValue() + "");
                                favoriteMovieOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myMoviesTV.setVisibility(View.VISIBLE);
                                myMoviesExpandBTN.setVisibility(View.GONE);
                            } else {
                                myMoviesTV.setVisibility(View.GONE);
                                myMoviesExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(favoriteMovieTitleList);
                        Collections.reverse(favoriteMovieYearList);
                        Collections.reverse(favoriteMoviePosterList);
                        Collections.reverse(favoriteMovieIdList);
                        Collections.reverse(favoriteMovieOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileMovieAdapter(FeedActivity.mContext, favoriteMovieTitleList, favoriteMovieYearList, favoriteMoviePosterList, favoriteMovieOriginalTitleList);
                        myMoviesRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    // Tv
    private void getWatchedTvFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Tv")
                        .child("Watched");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        watchedTvTitleList.clear();

                        watchedTvYearList.clear();

                        watchedTvPosterList.clear();

                        watchedTvIdList.clear();

                        watchedTvOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                watchedTvTitleList.add(ds.child("title").getValue() + "");
                                watchedTvYearList.add(ds.child("release_date").getValue() + "");
                                watchedTvPosterList.add(ds.child("poster_path").getValue() + "");
                                watchedTvIdList.add(ds.child("id").getValue() + "");
                                watchedTvOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myTvTV.setVisibility(View.VISIBLE);
                                myTvExpandBTN.setVisibility(View.GONE);
                            } else {
                                myTvTV.setVisibility(View.GONE);
                                myTvExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(watchedTvTitleList);
                        Collections.reverse(watchedTvYearList);
                        Collections.reverse(watchedTvPosterList);
                        Collections.reverse(watchedTvIdList);
                        Collections.reverse(watchedTvOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileTvAdapter(FeedActivity.mContext, watchedTvTitleList, watchedTvYearList, watchedTvPosterList, watchedTvOriginalTitleList);
                        myTvRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void getWatchLaterTvFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Tv")
                        .child("Watch Later");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        watchLaterTvTitleList.clear();

                        watchLaterTvYearList.clear();

                        watchLaterTvPosterList.clear();

                        watchLaterTvIdList.clear();

                        watchLaterTvOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                watchLaterTvTitleList.add(ds.child("title").getValue() + "");
                                watchLaterTvYearList.add(ds.child("release_date").getValue() + "");
                                watchLaterTvPosterList.add(ds.child("poster_path").getValue() + "");
                                watchLaterTvIdList.add(ds.child("id").getValue() + "");
                                watchLaterTvOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myTvTV.setVisibility(View.VISIBLE);
                                myTvExpandBTN.setVisibility(View.GONE);
                            } else {
                                myTvTV.setVisibility(View.GONE);
                                myTvExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(watchLaterTvTitleList);
                        Collections.reverse(watchLaterTvYearList);
                        Collections.reverse(watchLaterTvPosterList);
                        Collections.reverse(watchLaterTvIdList);
                        Collections.reverse(watchLaterTvOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileTvAdapter(FeedActivity.mContext, watchLaterTvTitleList, watchLaterTvYearList, watchLaterTvPosterList, watchLaterTvOriginalTitleList);
                        myTvRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void getFavoriteTvFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Tv")
                        .child("Favorites");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        favoriteTvTitleList.clear();

                        favoriteTvYearList.clear();

                        favoriteTvPosterList.clear();

                        favoriteTvIdList.clear();

                        favoriteTvOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                favoriteTvTitleList.add(ds.child("title").getValue() + "");
                                favoriteTvYearList.add(ds.child("release_date").getValue() + "");
                                favoriteTvPosterList.add(ds.child("poster_path").getValue() + "");
                                favoriteTvIdList.add(ds.child("id").getValue() + "");
                                favoriteTvOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                myTvTV.setVisibility(View.VISIBLE);
                                myTvExpandBTN.setVisibility(View.GONE);
                            } else {
                                myTvTV.setVisibility(View.GONE);
                                myTvExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(favoriteTvTitleList);
                        Collections.reverse(favoriteTvYearList);
                        Collections.reverse(favoriteTvPosterList);
                        Collections.reverse(favoriteTvIdList);
                        Collections.reverse(favoriteTvOriginalTitleList);

                        RecyclerView.Adapter newAdapter = new ProfileTvAdapter(FeedActivity.mContext, favoriteTvTitleList, favoriteTvYearList, favoriteTvPosterList, favoriteTvOriginalTitleList);
                        myTvRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    // Actor
    private void getFavoriteActorFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Person")
                        .child("Favorites");

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        favoriteActorNameList.clear();

                        favoriteActorProfileList.clear();

                        favoriteActorIdList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                favoriteActorNameList.add(ds.child("name").getValue() + "");
                                favoriteActorProfileList.add(ds.child("profile_path").getValue() + "");
                                favoriteActorIdList.add(ds.child("id").getValue() + "");

                            }

                            if (dataSnapshot.getChildrenCount() == 0) {
                                favoriteActorsTV.setVisibility(View.VISIBLE);
                                favoriteActorsExpandBTN.setVisibility(View.GONE);
                            } else {
                                favoriteActorsTV.setVisibility(View.GONE);
                                favoriteActorsExpandBTN.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(favoriteActorNameList);
                        Collections.reverse(favoriteActorProfileList);
                        Collections.reverse(favoriteActorIdList);

                        RecyclerView.Adapter newAdapter = new ProfilePersonAdapter(FeedActivity.mContext, favoriteActorNameList, favoriteActorProfileList);
                        favoriteActorsRV.swapAdapter(newAdapter, true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        hideProgress();
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

            hideProgress();

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void showProgress() {
        mainCL.setAlpha(0.5f);
        mainPB.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mainCL.setAlpha(1.0f);
        mainPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.search);
        if (item != null) {
            item.setVisible(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_profile_refreshBTN:
                myMoviesWatchedRBTN.setChecked(true);
                checkInternetConnectionForWatchedMovie();
                break;
            case R.id.myMoviesExpandBTN:
                expandMovie();
                break;
            case R.id.favoriteActorsExpandBTN:
                expandPerson();
                break;
            case R.id.myTvExpandBTN:
                expandTv();
                break;
        }
    }

    private void expandMovie() {
        try {
            @SuppressWarnings("ConstantConditions") ConnectivityManager connectivityManager = (ConnectivityManager) (getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                Intent intent = new Intent(FeedActivity.mContext, ProfileActivityMovie.class);
                intent.putStringArrayListExtra("watchedMovieTitleList", (ArrayList<String>) watchedMovieTitleList);
                intent.putStringArrayListExtra("watchedMovieYearList", (ArrayList<String>) watchedMovieYearList);
                intent.putStringArrayListExtra("watchedMoviePosterList", (ArrayList<String>) watchedMoviePosterList);
                intent.putStringArrayListExtra("watchedMovieIdList", (ArrayList<String>) watchedMovieIdList);
                intent.putStringArrayListExtra("watchedMovieOriginalTitleList", (ArrayList<String>) watchedMovieOriginalTitleList);
                intent.putExtra("movieType", movieType);
                startActivity(intent);

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressWarnings("ConstantConditions")
    private void expandPerson() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) (getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                Intent intent = new Intent(FeedActivity.mContext, ProfileActivityPerson.class);
                intent.putStringArrayListExtra("personIdList", (ArrayList<String>) favoriteActorIdList);
                intent.putStringArrayListExtra("personNameList", (ArrayList<String>) favoriteActorNameList);
                intent.putStringArrayListExtra("personProfileList", (ArrayList<String>) favoriteActorProfileList);
                startActivity(intent);

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }

    }

    private void expandTv() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) (getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                Intent intent = new Intent(FeedActivity.mContext, ProfileActivityTv.class);
                intent.putStringArrayListExtra("watchedMovieTitleList", (ArrayList<String>) watchedTvTitleList);
                intent.putStringArrayListExtra("watchedMovieYearList", (ArrayList<String>) watchedTvYearList);
                intent.putStringArrayListExtra("watchedMoviePosterList", (ArrayList<String>) watchedTvPosterList);
                intent.putStringArrayListExtra("watchedMovieIdList", (ArrayList<String>) watchedTvIdList);
                intent.putStringArrayListExtra("watchedMovieOriginalTitleList", (ArrayList<String>) watchedTvOriginalTitleList);
                intent.putExtra("tvType", tvType);
                startActivity(intent);

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }

    }
}
