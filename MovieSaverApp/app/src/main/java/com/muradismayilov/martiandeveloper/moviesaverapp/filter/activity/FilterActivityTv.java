package com.muradismayilov.martiandeveloper.moviesaverapp.filter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.tools.Client;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_model.FilterTv;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_recycler.FilterTvAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response.FilterTvResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_service.FilterTvService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools.MovieGridSpacingItemDecoration;

import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivityTv extends AppCompatActivity {

    // UI Components
    // RecyclerView
    @BindView(R.id.filterRV)
    RecyclerView filterRV;
    // Toolbar
    @BindView(R.id.filterToolbar)
    Toolbar filterToolbar;
    // Layout
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    // ProgressBar
    @BindView(R.id.filterMainPB)
    ProgressBar filterMainPB;
    @BindView(R.id.filterMorePB)
    ProgressBar filterMorePB;
    // SwipeRefreshLayout
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;
    // TextView
    @BindView(R.id.filterTypeTV)
    TextView filterTypeTV;
    @BindView(R.id.filterGenresTV)
    TextView filterGenresTV;

    // Strings
    @BindString(R.string.filter_result)
    String filter_result;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;
    @BindString(R.string.banner_ad_for_filter)
    String banner_ad_for_filter;
    @BindString(R.string.popularity)
    String popularity;
    @BindString(R.string.highest_rated)
    String highest_rated;
    @BindString(R.string.none)
    String none;
    @BindString(R.string.interstitial_ad_for_more)
    String interstitial_ad_for_more;

    // Variables
    // String
    private String genres, type, LOG, premium;
    // StringBuilder
    private StringBuilder formattedGenres, uiGenres;
    // List
    private List<FilterTv> filterTvList;
    // Adapter
    private RecyclerView.Adapter filterAdapter;
    // Service
    private FilterTvService filterTvService;
    // Integer
    private int filterTvPage;
    // Firebase
    private FirebaseUser mUser;
    // InterstitialAd
    private InterstitialAd interstitialAd;
    // AdRequest
    private AdRequest interstitialAdRequest;
    // Boolean
    private boolean isBollywood;
    // Activity
    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.filter_activity_tv);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTwo));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));

        filterMainPB.setVisibility(View.INVISIBLE);
        filterMorePB.setVisibility(View.INVISIBLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            filterRV.addItemDecoration(new MovieGridSpacingItemDecoration(2, 32, false));
        }

        setToolbar();
        declareVariables();
        setGenres();
        setListeners();
        checkForWhichResult();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForAds();
    }

    private void setToolbar() {
        setSupportActionBar(filterToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        FilterActivityTv.this.setTitle(filter_result);
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        // String
        genres = getIntent().getStringExtra("genres");
        type = getIntent().getStringExtra("type");
        LOG = "MartianDeveloper";
        premium = "";
        // StringBuilder
        formattedGenres = new StringBuilder();
        uiGenres = new StringBuilder();
        // Client & Service
        Client filterTvClient = new Client();
        filterTvService = filterTvClient.getClient().create(FilterTvService.class);
        // Integer
        filterTvPage = 1;
        // List
        filterTvList = new ArrayList<>();
        // LayoutManager
        RecyclerView.LayoutManager filterLayoutManager = new GridLayoutManager(this, 2);
        filterRV.setLayoutManager(filterLayoutManager);
        // Adapter
        filterAdapter = new FilterTvAdapter(this, filterTvList);
        filterRV.setAdapter(filterAdapter);
        // Boolean
        String bollywood = getIntent().getStringExtra("bollywood");
        if(bollywood != null){
            isBollywood = bollywood.equals("true");
        }
        // Activity
        mActivity = FilterActivityTv.this;
    }

    private void setGenres() {
        // Formatted Genres
        if (!genres.equals("none")) {
            String[] holder = genres.split(" ");

            for (String s : holder) {
                formattedGenres.append(s).append(",");
            }
        }

        // UI Genres
        if (!genres.equals("none")) {
            String[] holder = genres.split(" ");

            for (String s : holder) {

                if (s.equals("10759")) {
                    uiGenres.append("Action-Adventure, ");
                }

                if (s.equals("16")) {
                    uiGenres.append("Animation, ");
                }

                if (s.equals("80")) {
                    uiGenres.append("Crime, ");
                }

                if (s.equals("18")) {
                    uiGenres.append("Drama, ");
                }

                if (s.equals("9648")) {
                    uiGenres.append("Mystery, ");
                }

                if (s.equals("35")) {
                    uiGenres.append("Comedy, ");
                }

                if (s.equals("99")) {
                    uiGenres.append("Documentary, ");
                }

                if (s.equals("10751")) {
                    uiGenres.append("Family, ");
                }

                if (s.equals("37")) {
                    uiGenres.append("Western, ");
                }

                if (s.equals("10762")) {
                    uiGenres.append("Kids, ");
                }

                if (s.equals("10763")) {
                    uiGenres.append("News, ");
                }

                if (s.equals("10764")) {
                    uiGenres.append("Reality, ");
                }

                if (s.equals("10765")) {
                    uiGenres.append("Sci-Fi Fantasy, ");
                }

                if (s.equals("10766")) {
                    uiGenres.append("Soap, ");
                }

                if (s.equals("10767")) {
                    uiGenres.append("Talk, ");
                }

                if (s.equals("10768")) {
                    uiGenres.append("War-Politics, ");
                }
            }
            uiGenres.delete(uiGenres.length() - 2, uiGenres.length());
        }
    }

    private void setListeners() {
        filterRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    checkForWhichMoreResult();

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {

            checkForWhichRefresh();

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });
    }

    private void checkForWhichResult() {

        // Only Popularity or HighestRated
        if (genres.equals("none")) {
            filterGenresTV.setText(none);
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithPopularity();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none")) {
            filterGenresTV.setText(uiGenres.toString());
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithPopularityAndGenres();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenres();
                }
            }
        }
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
        adView.setAdUnitId(banner_ad_for_filter);
        adViewPlaceholderFL.addView(adView);

        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(bannerAdRequest);

        interstitialAd = new InterstitialAd(FilterActivityTv.this);
        interstitialAd.setAdUnitId(interstitial_ad_for_more);
        interstitialAdRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialAdRequest);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                interstitialAd.loadAd(interstitialAdRequest);
            }
        });
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

    private void checkForWhichRefresh() {

        // Only Popularity or HighestRated
        if (genres.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithPopularity();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithPopularityAndGenres();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenres();
                }
            }
        }
    }

    private void checkForWhichMoreResult() {

        // Only Popularity or HighestRated
        if (genres.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreTvFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForMoreTvFilterResult_WithPopularity();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreTvFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForMoreTvFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreTvFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForMoreTvFilterResult_WithPopularityAndGenres();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreTvFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForMoreTvFilterResult_WithHighestRatedAndGenres();
                }
            }
        }
    }

    // CheckInternet
    // Popularity
    private void checkInternetConnectionForTvFilterResult_WithPopularity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithPopularity();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithPopularityAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithPopularityAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithPopularityBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithPopularityBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithPopularityAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithPopularityAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    // Highest Rated
    private void checkInternetConnectionForTvFilterResult_WithHighestRated() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithHighestRated();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithHighestRatedAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithHighestRatedBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithHighestRatedBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForTvFilterResult_WithHighestRatedAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvFilterResult_WithHighestRatedAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    // LoadJSON
    // Popularity
    private void loadJSONForTvFilterResult_WithPopularity() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithType("popularity.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithPopularityAndGenres() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenres(formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithPopularityBollywood() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeBollywood("hi", "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithPopularityAndGenresBollywood() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // HighestRated
    private void loadJSONForTvFilterResult_WithHighestRated() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithType("vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithHighestRatedAndGenres() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenres(formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithHighestRatedBollywood() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeBollywood("hi", "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForTvFilterResult_WithHighestRatedAndGenresBollywood() {
        showProgress();
        filterTvPage = 1;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);

        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {
                try {

                    if (response.body() != null) {
                        filterTvList = response.body().getResults();
                    }

                    if (filterTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterTvAdapter(FilterActivityTv.this, filterTvList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // More CheckInternet
    // Popularity
    private void checkInternetConnectionForMoreTvFilterResult_WithPopularity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithPopularity();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithPopularityAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithPopularityAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithPopularityBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithPopularityBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithPopularityAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithPopularityAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    // HighestRated
    private void checkInternetConnectionForMoreTvFilterResult_WithHighestRated() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithHighestRated();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithHighestRatedAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithHighestRatedAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithHighestRatedBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithHighestRatedBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreTvFilterResult_WithHighestRatedAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreTvFilterResult_WithHighestRatedAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    // More loadJSON
    // Popularity
    private void loadJSONForMoreTvFilterResult_WithPopularity() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithType("popularity.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithPopularityAndGenres() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenres(formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithPopularityBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeBollywood("hi", "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithPopularityAndGenresBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // HighestRated
    private void loadJSONForMoreTvFilterResult_WithHighestRated() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithType("vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithHighestRatedAndGenres() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenres(formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithHighestRatedBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeBollywood("hi", "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreTvFilterResult_WithHighestRatedAndGenresBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterTvPage++;

        Call<FilterTvResponse> call = filterTvService.getTvWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterTvPage);
        call.enqueue(new Callback<FilterTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterTvResponse> call, @NonNull Response<FilterTvResponse> response) {

                try {
                    if (response.body() != null) {
                        filterTvList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterTvList.size(), filterTvList.size());

                        float adCounter = filterTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterTvResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityTv.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Other
    private void showProgress() {
        filterRV.setAlpha(0.5f);
        filterMainPB.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        filterRV.setAlpha(1.0f);
        filterMainPB.setVisibility(View.INVISIBLE);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

