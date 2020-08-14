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
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_model.FilterMovie;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_recycler.FilterMovieAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_response.FilterMovieResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.filter_service.FilterMovieService;
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

public class FilterActivityMovie extends AppCompatActivity {

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
    @BindView(R.id.filterYearTV)
    TextView filterYearTV;
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
    private String genres, year, type, LOG, premium;
    // StringBuilder
    private StringBuilder formattedGenres, uiGenres;
    // List
    private List<FilterMovie> filterMovieList;
    // Adapter
    private RecyclerView.Adapter filterAdapter;
    // Service
    private FilterMovieService filterMovieService;
    // Integer
    private int filterMoviePage;
    // Firebase
    private FirebaseUser mUser;
    // AdRequest
    private AdRequest interstitialAdRequest;
    // InterstitialAd
    private InterstitialAd interstitialAd;
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
        setContentView(R.layout.filter_activity_movie);
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
        FilterActivityMovie.this.setTitle(filter_result);
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        // String
        genres = getIntent().getStringExtra("genres");
        year = getIntent().getStringExtra("year");
        type = getIntent().getStringExtra("type");
        LOG = "MartianDeveloper";
        premium = "";
        // StringBuilder
        formattedGenres = new StringBuilder();
        uiGenres = new StringBuilder();
        // Client & Service
        Client filterMovieClient = new Client();
        filterMovieService = filterMovieClient.getClient().create(FilterMovieService.class);
        // Integer
        filterMoviePage = 1;
        // List
        filterMovieList = new ArrayList<>();
        // LayoutManager
        RecyclerView.LayoutManager filterLayoutManager = new GridLayoutManager(this, 2);
        filterRV.setLayoutManager(filterLayoutManager);
        // Adapter
        filterAdapter = new FilterMovieAdapter(this, filterMovieList);
        filterRV.setAdapter(filterAdapter);
        // Boolean
        String bollywood = getIntent().getStringExtra("bollywood");
        if(bollywood != null){
            isBollywood = bollywood.equals("true");
        }
        // Activity
        mActivity = FilterActivityMovie.this;
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
                if (s.equals("28")) {
                    uiGenres.append("Action, ");
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

                if (s.equals("14")) {
                    uiGenres.append("Fantasy, ");
                }

                if (s.equals("27")) {
                    uiGenres.append("Horror, ");
                }

                if (s.equals("9648")) {
                    uiGenres.append("Mystery, ");
                }

                if (s.equals("878")) {
                    uiGenres.append("Sci-Fi, ");
                }

                if (s.equals("10752")) {
                    uiGenres.append("War, ");
                }

                if (s.equals("12")) {
                    uiGenres.append("Adventure, ");
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

                if (s.equals("36")) {
                    uiGenres.append("History, ");
                }

                if (s.equals("10402")) {
                    uiGenres.append("Music, ");
                }

                if (s.equals("10749")) {
                    uiGenres.append("Romance, ");
                }

                if (s.equals("53")) {
                    uiGenres.append("Thriller, ");
                }

                if (s.equals("37")) {
                    uiGenres.append("Western, ");
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
        if (genres.equals("none") && year.equals("none")) {
            filterYearTV.setText(none);
            filterGenresTV.setText(none);
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularity();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none") && year.equals("none")) {
            filterYearTV.setText(none);
            filterGenresTV.setText(uiGenres.toString());
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenres();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenres();
                }
            }
        }

        // Popularity or Highest Rated With Year
        if (genres.equals("none") && !year.equals("none")) {
            filterYearTV.setText(year);
            filterGenresTV.setText(none);
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndYear();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYear();
                }
            }
        }

        // Popularity or Highest Rated With Genres And Year
        if (!genres.equals("none") && !year.equals("none")) {
            filterYearTV.setText(year);
            filterGenresTV.setText(uiGenres.toString());
            if (type.equals("Popularity")) {
                filterTypeTV.setText(popularity);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYear();
                }
            } else {
                filterTypeTV.setText(highest_rated);
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYear();
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

        interstitialAd = new InterstitialAd(FilterActivityMovie.this);
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
        if (genres.equals("none") && year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularity();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none") && year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenres();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenres();
                }
            }
        }

        // Popularity or Highest Rated With Year
        if (genres.equals("none") && !year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndYear();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYear();
                }
            }
        }

        // Popularity or Highest Rated With Genres And Year
        if (!genres.equals("none") && !year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYear();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYear();
                }
            }
        }
    }

    private void checkForWhichMoreResult() {

        // Only Popularity or HighestRated
        if (genres.equals("none") && year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularity();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRated();
                }
            }
        }

        // Popularity or Highest Rated With Genres
        if (!genres.equals("none") && year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenres();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenres();
                }
            }
        }

        // Popularity or Highest Rated With Year
        if (genres.equals("none") && !year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndYearBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndYear();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndYearBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndYear();
                }
            }
        }

        // Popularity or Highest Rated With Genres And Year
        if (!genres.equals("none") && !year.equals("none")) {
            if (type.equals("Popularity")) {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresAndYear();
                }
            } else {
                if (isBollywood) {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood();
                } else {
                    checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresAndYear();
                }
            }
        }
    }

    // CheckInternet
    // Popularity
    private void checkInternetConnectionForMovieFilterResult_WithPopularity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularity();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndGenresAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithPopularityAndGenresAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithPopularityAndGenresAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    // Highest Rated
    private void checkInternetConnectionForMovieFilterResult_WithHighestRated() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRated();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndGenresAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    // LoadJSON
    // Popularity
    private void loadJSONForMovieFilterResult_WithPopularity() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithType("popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndGenres() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenres(formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndYear() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYear(Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndGenresAndYear() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYear(formattedGenres.toString(), Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeBollywood("hi", "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndGenresBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndYearBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYearBollywood("hi", Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithPopularityAndGenresAndYearBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYearBollywood("hi", formattedGenres.toString(), Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // HighestRated
    private void loadJSONForMovieFilterResult_WithHighestRated() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithType("vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndGenres() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenres(formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndYear() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYear(Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndGenresAndYear() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYear(formattedGenres.toString(), Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeBollywood("hi", "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndGenresBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndYearBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYearBollywood("hi", Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood() {
        showProgress();
        filterMoviePage = 1;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYearBollywood("hi", formattedGenres.toString(), Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);

        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        filterMovieList = response.body().getResults();
                    }

                    if (filterMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    filterAdapter = new FilterMovieAdapter(FilterActivityMovie.this, filterMovieList);
                    filterRV.swapAdapter(filterAdapter, true);

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // More CheckInternet
    // Popularity
    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularity();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndGenresAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithPopularityAndGenresAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithPopularityAndGenresAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    // HighestRated
    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRated() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRated();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenres() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenres();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresAndYear() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresAndYear();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternetConnectionForMoreMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }


    // More loadJSON
    // Popularity
    private void loadJSONForMoreMovieFilterResult_WithPopularity() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithType("popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeBollywood("hi", "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndGenres() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenres(formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndGenresBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndYear() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYear(Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndYearBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYearBollywood("hi", Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndGenresAndYear() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYear(formattedGenres.toString(), Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithPopularityAndGenresAndYearBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYearBollywood("hi", formattedGenres.toString(), Integer.valueOf(year), "popularity.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // HighestRated
    private void loadJSONForMoreMovieFilterResult_WithHighestRated() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithType("vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeBollywood("hi", "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenres() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenres(formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresBollywood("hi", formattedGenres.toString(), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndYear() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYear(Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndYearBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndYearBollywood("hi", Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresAndYear() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYear(formattedGenres.toString(), Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadJSONForMoreMovieFilterResult_WithHighestRatedAndGenresAndYearBollywood() {
        filterMorePB.setVisibility(View.VISIBLE);
        filterMoviePage++;

        Call<FilterMovieResponse> call = filterMovieService.getMovieWithTypeAndGenresAndYearBollywood("hi", formattedGenres.toString(), Integer.valueOf(year), "vote_average.desc", getResources().getString(R.string.api_key), filterMoviePage);
        call.enqueue(new Callback<FilterMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<FilterMovieResponse> call, @NonNull Response<FilterMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        filterMovieList.addAll(response.body().getResults());

                        filterAdapter.notifyItemRangeInserted(filterMovieList.size(), filterMovieList.size());

                        float adCounter = filterMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                filterMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<FilterMovieResponse> call, @NonNull Throwable t) {
                filterMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(FilterActivityMovie.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
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
