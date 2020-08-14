package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_recycler.profile_expand_tv_recycler.ProfileExpandTvAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_tools.ProfileGridSpacingItemDecoration;

import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivityTv extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // UI Components
    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // RecyclerView
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    // Layout
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;

    // Strings
    // Error
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    // Notification
    @BindString(R.string.banner_ad_for_my_profile_expand)
    String banner_ad_for_my_profile_expand;

    // Variables
    // List
    private List<String> tvTitleList, tvYearList, tvPosterList, tvIdList, tvOriginalTitleList;
    // Adapter
    private RecyclerView.Adapter mAdapter;
    // Firebase
    private FirebaseUser mUser;
    // String
    private String tvType, premium, LOG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.profile_activity_tv);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTwo));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));

        declareVariables();
        setToolbar();
        setRecyclerView();
        setListeners();
        checkInternetConnectionForTv();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForAds();
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // String
        tvType = getIntent().getStringExtra("tvType");
        premium = "";
        LOG = "MartianDeveloper";

        // List
        tvTitleList = new ArrayList<>();
        tvYearList = new ArrayList<>();
        tvPosterList = new ArrayList<>();
        tvIdList = new ArrayList<>();
        tvOriginalTitleList = new ArrayList<>();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ProfileActivityTv.this.setTitle(tvType);
    }

    private void setRecyclerView() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mRecyclerView.addItemDecoration(new ProfileGridSpacingItemDecoration(2, 32, false));
        }

        // LayoutManager
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Adapter
        mAdapter = new ProfileExpandTvAdapter(this, tvTitleList, tvYearList, tvPosterList, tvIdList, tvOriginalTitleList, tvType);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> {

            checkInternetConnectionForRefresh();

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });
    }

    private void checkInternetConnectionForTv() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                swipeRefreshLayout.setVisibility(View.VISIBLE);

                getTvFromDB(tvType);

            } else {
                swipeRefreshLayout.setVisibility(View.INVISIBLE);

                Toast.makeText(ProfileActivityTv.this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ProfileActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void getTvFromDB(String type) {

        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("Tv")
                        .child(type);

                mDatabaseReference.orderByChild("time").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        tvTitleList.clear();

                        tvYearList.clear();

                        tvPosterList.clear();

                        tvIdList.clear();

                        tvOriginalTitleList.clear();

                        try {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                tvTitleList.add(ds.child("title").getValue() + "");
                                tvYearList.add(ds.child("release_date").getValue() + "");
                                tvPosterList.add(ds.child("poster_path").getValue() + "");
                                tvIdList.add(ds.child("id").getValue() + "");
                                tvOriginalTitleList.add(ds.child("original_title").getValue() + "");

                            }

                        } catch (Exception e) {
                            Log.d("MartianDeveloper", errorSTR + e.getLocalizedMessage());
                        }

                        Collections.reverse(tvTitleList);
                        Collections.reverse(tvYearList);
                        Collections.reverse(tvPosterList);
                        Collections.reverse(tvIdList);
                        Collections.reverse(tvOriginalTitleList);

                        mAdapter = new ProfileExpandTvAdapter(ProfileActivityTv.this, tvTitleList, tvYearList, tvPosterList, tvIdList, tvOriginalTitleList, tvType);
                        mRecyclerView.swapAdapter(mAdapter, true);

                        ProfileActivityTv.this.setTitle(tvType + " (" + dataSnapshot.getChildrenCount() + ")");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("MartianDeveloper", errorSTR + databaseError.getMessage());
                    }
                });
            }


        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d("MartianDeveloper", errorSTR + e.getLocalizedMessage());
            } else {
                Log.d("MartianDeveloper", "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void checkInternetConnectionForRefresh() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                refreshRecyclerView();

            } else {
                Toast.makeText(ProfileActivityTv.this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ProfileActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshRecyclerView() {
        mAdapter = new ProfileExpandTvAdapter(this, tvTitleList, tvYearList, tvPosterList, tvIdList, tvOriginalTitleList, tvType);
        mRecyclerView.swapAdapter(mAdapter, true);
    }

    private void checkForAds() {
        try {
            if (mUser != null) {
                DatabaseReference mDatabaseReference = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Users")
                        .child(mUser.getUid())
                        .child("User Information");

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            premium = String.valueOf(dataSnapshot.child("premium").getValue());
                            if (premium.equals("false")) {
                                setAds();
                            }
                        } catch (Exception e) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(LOG, errorSTR + databaseError.getMessage());
                    }
                });
            }

        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void setAds() {
        MobileAds.initialize(this);
        AdView adView = new AdView(this);
        adView.setAdUnitId(banner_ad_for_my_profile_expand);
        adViewPlaceholderFL.addView(adView);

        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(bannerAdRequest);
    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileUtils.deleteQuietly(getApplicationContext().getCacheDir());
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, "Error: " + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Error: While Clearing Cache on FeedActivity");
            }
        }

        try {
            if (getApplicationContext().getExternalCacheDir() != null) {
                getApplicationContext().getExternalCacheDir().delete();
            }
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, "Error: " + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Error: While Clearing Cache on FeedActivity");
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        ((ProfileExpandTvAdapter) mAdapter).filter(s);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_profile_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);

        return true;
    }
}