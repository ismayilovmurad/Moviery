package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FullScreenVideoActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.tools.Client;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieCast;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieDetailProductionCompanies;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieImage;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieSimilar;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_model.MovieVideo;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_cast_recycler.MovieCastAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_production_recycler.MovieProductionAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_recycler.movie_similar_recycler.MovieSimilarAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieCastResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieDetailResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieImageResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieSimilarResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_response.MovieVideoResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service.MovieCastService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service.MovieDetailService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service.MovieImageService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service.MovieSimilarService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_service.MovieVideoService;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools.MovieSliderAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.apache.commons.io.FileUtils;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    // TextView
    @BindView(R.id.section0_runtimeTV)
    TextView section0_runtimeTV;
    @BindView(R.id.section0_releasedTV)
    TextView section0_releasedTV;
    @BindView(R.id.section0_genreTV)
    TextView section0_genreTV;
    @BindView(R.id.section0_voteAverageTV)
    TextView section0_voteAverageTV;
    @BindView(R.id.moreTV)
    TextView moreTV;
    @BindView(R.id.section1_overviewTV)
    TextView section1_overviewTV;
    @BindView(R.id.section1_budgetTV)
    TextView section1_budgetTV;
    @BindView(R.id.section1_originalLanguageTV)
    TextView section1_originalLanguageTV;
    @BindView(R.id.section1_popularityTV)
    TextView section1_popularityTV;
    @BindView(R.id.section1_statusTV)
    TextView section1_statusTV;
    @BindView(R.id.section1_tagLineTV)
    TextView section1_tagLineTV;
    @BindView(R.id.section1_voteCountTV)
    TextView section1_voteCountTV;
    @BindView(R.id.favoriteTV)
    TextView favoriteTV;
    @BindView(R.id.watch_laterTV)
    TextView watch_laterTV;
    @BindView(R.id.watchedTV)
    TextView watchedTV;
    @BindView(R.id.section1_revenueTV)
    TextView section1_revenueTV;
    @BindView(R.id.section2_trailerTV)
    TextView section2_trailerTV;
    // ImageView
    @BindView(R.id.backdropIV)
    ImageView backdropIV;
    @BindView(R.id.section0_posterIV)
    ImageView section0_posterIV;
    @BindView(R.id.section2_watchMovieInfoIV)
    ImageView section2_watchMovieInfoIV;
    // LinearLayout
    @BindView(R.id.section1_mainLL)
    LinearLayout section1_mainLL;
    // CoordinatorLayout
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;
    @BindView(R.id.section2_comingSoonFL)
    FrameLayout section2_comingSoonFL;
    // View
    @BindView(R.id.section2_view)
    View section2_view;
    // RecyclerView
    @BindView(R.id.section4_productionCompaniesRV)
    RecyclerView section4_productionCompaniesRV;
    @BindView(R.id.section3_castRV)
    RecyclerView section3_castRV;
    @BindView(R.id.section5_similarRV)
    RecyclerView section5_similarRV;
    // ToolBar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // ProgressBar
    @BindView(R.id.mainPB)
    ProgressBar mainPB;
    @BindView(R.id.section5_similarPB)
    ProgressBar section5_similarPB;
    // SliderView
    @BindView(R.id.section6_picturesSV)
    SliderView section6_picturesSV;
    // FloatingActionButton
    @BindView(R.id.mainFAB)
    FloatingActionButton mainFAB;
    @BindView(R.id.favoriteFAB)
    FloatingActionButton favoriteFAB;
    @BindView(R.id.watch_laterFAB)
    FloatingActionButton watch_laterFAB;
    @BindView(R.id.watchedFAB)
    FloatingActionButton watchedFAB;
    // RadioGroup
    @BindView(R.id.section2_trailerOptionsRG)
    RadioGroup section2_trailerOptionsRG;
    // YoutubePlayerView
    @BindView(R.id.section2_youTubePlayerView)
    YouTubePlayerView section2_youTubePlayerView;

    // Strings
    // Error
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    // Notification
    @BindString(R.string.added_to_favorites)
    String added_to_favorites;
    @BindString(R.string.added_to_watch_later)
    String added_to_watch_later;
    @BindString(R.string.added_to_watched)
    String added_to_watched;
    @BindString(R.string.already_added)
    String already_added;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;
    // Id
    @BindString(R.string.banner_ad_for_detail)
    String banner_ad_for_detail;
    @BindString(R.string.interstitial_ad_for_add_delete)
    String interstitial_ad_for_add;

    // Variables
    // Integer
    private int id, page, actionCount;
    // String
    private String originalTitle, titleForDB, poster_pathForDB, release_dateForDB,
            genreForDB, currentTimeStamp, premium, toastMessage, videoKey, videoName;
    // Final
    private final String LOG = "MartianDeveloper";
    private final String SHARED_PREFERENCES = "RATE";
    private final String ACTION_COUNT_SHARED_PREFERENCES = "actionCount";
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    // List
    private List<MovieDetailProductionCompanies> movieProductionCompaniesList;
    private List<MovieCast> movieCastList;
    private List<MovieSimilar> movieSimilarList;
    private List<MovieImage> movieImageList;
    private List<MovieVideo> movieVideoList;
    // Adapter
    private RecyclerView.Adapter productionAdapter;
    private RecyclerView.Adapter castAdapter;
    private RecyclerView.Adapter similarAdapter;
    // LayoutManager
    private RecyclerView.LayoutManager productionLayoutManager;
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager similarLayoutManager;
    // Boolean
    private boolean isExpand, isOpen, isContainForFavorite, isContainForWatchLater,
            isContainForWatched, isRate;
    // Slider
    private MovieSliderAdapter movieSliderAdapter;
    // Animation
    private Animation fab_open, fab_close, fab_clock, fab_anti_clock;
    // FireBase
    private FirebaseUser mUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    // Service
    private MovieCastService movieCastService;
    private MovieDetailService movieDetailService;
    private MovieSimilarService movieSimilarService;
    private MovieImageService movieImageService;
    private MovieVideoService movieVideoService;
    // Ad
    private InterstitialAd interstitialAd;
    // AdRequest
    private AdRequest interstitialAdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.movie_activity_detail);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        ViewCompat.setTransitionName(section0_posterIV, VIEW_NAME_HEADER_IMAGE);
        supportPostponeEnterTransition();
        supportStartPostponedEnterTransition();
        getLifecycle().addObserver(section2_youTubePlayerView);

        declareVariables();
        setToolbar();
        setListeners();
        getShared();
        hideProgress();

        collapse(section1_mainLL);
        section5_similarPB.setVisibility(View.INVISIBLE);

        section6_picturesSV.setIndicatorAnimation(IndicatorAnimations.WORM);
        section6_picturesSV.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkInternet();
        checkForAds();
    }

    private void declareVariables() {
        // Get from Intent
        id = getIntent().getIntExtra("id", -1);
        originalTitle = getIntent().getStringExtra("originalTitle");
        titleForDB = getIntent().getStringExtra("title");
        poster_pathForDB = getIntent().getStringExtra("posterPath");
        release_dateForDB = getIntent().getStringExtra("releaseDate");

        // Poster
        try {
            Glide.with(MovieDetailActivity.this)
                    .load(getResources().getString(R.string.w342_poster_image_url) + poster_pathForDB)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .dontAnimate()
                    .dontTransform()
                    .placeholder(R.drawable.image_placeholder)
                    .priority(Priority.IMMEDIATE)
                    .into(section0_posterIV);
        } catch (Exception e) {
            hideProgress();
            Log.d(LOG, errorSTR + e.getLocalizedMessage());
        }

        // Boolean
        isExpand = false;
        isContainForFavorite = false;
        isContainForWatchLater = false;
        isContainForWatched = false;

        // Animation
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anti_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // String
        genreForDB = "N/A";
        premium = "";
        toastMessage = "";
        videoKey = "";
        videoName = "";

        // CurrentTimeStamp
        long tsLong = System.currentTimeMillis() / 1000;
        currentTimeStamp = Long.toString(tsLong);

        // Client & Service
        Client castClient = new Client();
        movieCastService = castClient.getClient().create(MovieCastService.class);
        Client detailClient = new Client();
        movieDetailService = detailClient.getClient().create(MovieDetailService.class);
        Client similarClient = new Client();
        movieSimilarService = similarClient.getClient().create(MovieSimilarService.class);
        Client imageClient = new Client();
        movieImageService = imageClient.getClient().create(MovieImageService.class);
        Client videoClient = new Client();
        movieVideoService = videoClient.getClient().create(MovieVideoService.class);

        // Integer
        page = 1;

        // ActionCount
        SharedPreferences sharedPreferences = getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE);
        actionCount = sharedPreferences.getInt("actionCount", 0);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        MovieDetailActivity.this.setTitle(originalTitle);
    }

    private void setListeners() {
        // TextView
        moreTV.setOnClickListener(this);
        // FloatingActionButton
        mainFAB.setOnClickListener(this);
        favoriteFAB.setOnClickListener(this);
        watch_laterFAB.setOnClickListener(this);
        watchedFAB.setOnClickListener(this);
        // RecyclerView
        section5_similarRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollHorizontally(1)) {

                    checkInternetConnectionForMoreSimilar();
                }
            }

        });

        section2_watchMovieInfoIV.setOnClickListener(view -> showComingSoonDialog());
    }

    private void showComingSoonDialog() {
        final AlertDialog dialog_note = new AlertDialog.Builder(MovieDetailActivity.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.feed_dialog_coming_soon, null);

        final Button okayBTN = view.findViewById(R.id.okayBTN);

        okayBTN.setOnClickListener(v -> dialog_note.dismiss());

        dialog_note.setView(view);
        dialog_note.setCanceledOnTouchOutside(false);
        dialog_note.show();
    }

    private void getShared() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String rate = sharedPreferences.getString("rate", "nope");
        assert rate != null;
        isRate = !rate.equals("nope");
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
        adView.setAdUnitId(banner_ad_for_detail);
        adViewPlaceholderFL.addView(adView);

        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(bannerAdRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(interstitial_ad_for_add);
        interstitialAdRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialAdRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                final Handler handler = new Handler();
                handler.postDelayed(() -> Toast.makeText(MovieDetailActivity.this, toastMessage + "", Toast.LENGTH_SHORT).show(), 300);

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

    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            // Check
            checkIfFavoriteHasTheItem();
            checkIfWatchLaterHasTheItem();
            checkIfWatchedHasTheItem();

            // Get
            getVideoDataFromJSON();
            getDetailDataFromJSON();
            getCastDataFromJSON();
            getSimilarDataFromJSON();
            getImageDataFromJSON();

        } else {
            hideProgress();
            Toast.makeText(this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
        }
    }


    // Check
    private void checkIfFavoriteHasTheItem() {
        showProgress();

        try {
            if (mUser != null) {
                if (id != -1) {

                    DatabaseReference mDatabaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(mUser.getUid())
                            .child("Movie")
                            .child("Favorites");

                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            try {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.getKey() != null) {
                                        if (ds.getKey().equals(String.valueOf(id))) {
                                            isContainForFavorite = true;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            hideProgress();
                            Log.d(LOG, errorSTR + databaseError.getMessage());
                        }
                    });

                } else {
                    hideProgress();
                    Log.d(LOG, "ID is null on the checkIfFavoriteHasTheItem, MovieDetailActivity");
                }
            } else {
                hideProgress();
                Log.d(LOG, "User is null on the checkIfFavoriteHasTheItem, MovieDetailActivity");
            }

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfFavoriteHasItem, MovieDetailActivity");
            }
        }
    }

    private void checkIfWatchLaterHasTheItem() {
        showProgress();

        try {
            if (mUser != null) {
                if (id != -1) {

                    DatabaseReference mDatabaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(mUser.getUid())
                            .child("Movie")
                            .child("Watch Later");

                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            try {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.getKey() != null) {
                                        if (ds.getKey().equals(String.valueOf(id))) {
                                            isContainForWatchLater = true;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            hideProgress();
                            Log.d(LOG, errorSTR + databaseError.getMessage());

                        }
                    });

                } else {
                    hideProgress();
                    Log.d(LOG, "ID is null on the checkIfWatchLaterHasTheItem, MovieDetailActivity");
                }
            } else {
                hideProgress();
                Log.d(LOG, "User is null on the checkIfWatchLaterHasTheItem, MovieDetailActivity");
            }

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfWatchLaterHasTheItem, MovieDetailActivity");
            }
        }
    }

    private void checkIfWatchedHasTheItem() {
        showProgress();

        try {
            if (mUser != null) {
                if (id != -1) {

                    DatabaseReference mDatabaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(mUser.getUid())
                            .child("Movie")
                            .child("Watched");

                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            try {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (ds.getKey() != null) {
                                        if (ds.getKey().equals(String.valueOf(id))) {
                                            isContainForWatched = true;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            hideProgress();
                            Log.d(LOG, errorSTR + databaseError.getMessage());

                        }
                    });

                } else {
                    hideProgress();
                    Log.d(LOG, "ID is null on the checkIfWatchedHasTheItem, MovieDetailActivity");
                }
            } else {
                hideProgress();
                Log.d(LOG, "User is null on the checkIfWatchedHasTheItem, MovieDetailActivity");
            }

        } catch (Exception e) {
            hideProgress();
            if (e.getLocalizedMessage() != null) {
                Log.d(LOG, errorSTR + e.getLocalizedMessage());
            } else {
                Log.d(LOG, "Exception is null! Cause is on the checkIfWatchedHasTheItem, MovieDetailActivity");
            }
        }
    }


    // Get
    private void getVideoDataFromJSON() {

        if (id != -1) {
            showProgress();

            Call<MovieVideoResponse> call = movieVideoService.getMovieVideos(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<MovieVideoResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(@NonNull Call<MovieVideoResponse> call, @NonNull Response<MovieVideoResponse> response) {

                    try {
                        if (response.body() != null) {
                            movieVideoList = response.body().getResults();

                            if (movieVideoList.size() != 0) {

                                videoKey = movieVideoList.get(0).getKey();
                                videoName = movieVideoList.get(1).getName();

                                section2_trailerTV.setText(videoName);

                                section2_youTubePlayerView.getPlayerUiController().showPlayPauseButton(false);

                                section2_view.setOnClickListener(view -> {
                                    Intent intent = new Intent(MovieDetailActivity.this, FullScreenVideoActivity.class);
                                    intent.putExtra("videoKey", videoKey);

                                    //noinspection unchecked
                                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            MovieDetailActivity.this,
                                            new Pair<>(section2_youTubePlayerView,
                                                    FullScreenVideoActivity.VIEW_NAME_HEADER_IMAGE));

                                    ActivityCompat.startActivity(MovieDetailActivity.this, intent, activityOptions.toBundle());
                                });

                                section2_youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                                    @Override
                                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                        youTubePlayer.cueVideo(videoKey, 0);
                                    }

                                    @Override
                                    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {
                                        if (playerState == PlayerConstants.PlayerState.PLAYING) {
                                            section2_youTubePlayerView.getPlayerUiController().showFullscreenButton(true);
                                        }
                                    }

                                    @Override
                                    public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {

                                    }

                                    @Override
                                    public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

                                    }

                                    @Override
                                    public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {

                                    }

                                    @Override
                                    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

                                    }

                                    @Override
                                    public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {

                                    }

                                    @Override
                                    public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

                                    }

                                    @Override
                                    public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {

                                    }

                                    @Override
                                    public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

                                    }
                                });

                                section2_trailerOptionsRG.setOnCheckedChangeListener((group, checkedId) -> {
                                    switch (checkedId) {
                                        case R.id.section2_trailer1RB:
                                            section2_comingSoonFL.setVisibility(View.INVISIBLE);

                                            videoKey = movieVideoList.get(0).getKey();
                                            videoName = movieVideoList.get(0).getName();

                                            section2_trailerTV.setText(videoName);

                                            section2_youTubePlayerView.setVisibility(View.VISIBLE);
                                            section2_youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.cueVideo(videoKey, 0));

                                            break;
                                        case R.id.section2_trailer2RB:
                                            section2_comingSoonFL.setVisibility(View.INVISIBLE);

                                            if (movieVideoList.size() > 1) {
                                                videoKey = movieVideoList.get(1).getKey();
                                                videoName = movieVideoList.get(1).getName();
                                            } else {
                                                videoKey = "";
                                                videoName = "";
                                            }

                                            section2_trailerTV.setText(videoName);

                                            section2_youTubePlayerView.setVisibility(View.VISIBLE);
                                            section2_youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.cueVideo(videoKey, 0));
                                            break;
                                        case R.id.section2_watchMovieRB:
                                            section2_comingSoonFL.setVisibility(View.VISIBLE);
                                            section2_youTubePlayerView.setVisibility(View.INVISIBLE);

                                            section2_youTubePlayerView.getYouTubePlayerWhenReady(YouTubePlayer::pause);
                                            break;
                                    }
                                });
                            }
                        } else {
                            hideProgress();
                        }

                    } catch (Exception e) {
                        hideProgress();
                        if (e.getLocalizedMessage() != null) {
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(@NonNull Call<MovieVideoResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    if (t.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + t.getLocalizedMessage());
                    }
                }
            });
        } else {
            Log.d(LOG, errorSTR + "Id is -1");
        }
    }

    private void getDetailDataFromJSON() {

        if (id != -1) {
            showProgress();
            Call<MovieDetailResponse> call = movieDetailService.getMovieDetail(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<MovieDetailResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieDetailResponse> call, @NonNull Response<MovieDetailResponse> response) {

                    if (response.body() != null) {
                        try {

                            // Backdrop
                            try {
                                Glide.with(MovieDetailActivity.this)
                                        .load(response.body().getBackdrop_path())
                                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                                        .dontAnimate()
                                        .dontTransform()
                                        .placeholder(R.drawable.image_placeholder)
                                        .priority(Priority.NORMAL)
                                        .into(backdropIV);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Runtime
                            try {
                                String _runtime = response.body().getRuntime() + "";
                                section0_runtimeTV.setText(_runtime);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Release Date
                            try {
                                String _release_date = response.body().getRelease_date() + "";
                                section0_releasedTV.setText(_release_date);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Genre
                            try {
                                StringBuilder genre = new StringBuilder();

                                for (int i = 0; i < response.body().getGenres().size(); i++) {
                                    genre.append(response.body().getGenres().get(i).getName()).append(" ");
                                }

                                section0_genreTV.setText(genre.toString());

                                genreForDB = genre.toString();

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Vote Average
                            try {
                                String _vote_average = response.body().getVote_average() + "";
                                section0_voteAverageTV.setText(_vote_average);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Overview
                            try {
                                String _overview = response.body().getOverview() + "";
                                section1_overviewTV.setText(_overview);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Budget
                            try {
                                String _budget = response.body().getBudget() + "";
                                section1_budgetTV.setText(_budget);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Original Language
                            try {
                                String _original_language = response.body().getOriginal_language() + "";
                                section1_originalLanguageTV.setText(_original_language);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Revenue
                            try {
                                String _revenue = response.body().getRevenue() + "";
                                section1_revenueTV.setText(_revenue);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Popularity
                            try {
                                String _popularity = response.body().getPopularity() + "";
                                section1_popularityTV.setText(_popularity);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Status
                            try {
                                String _status = response.body().getStatus() + "";
                                section1_statusTV.setText(_status);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Vote Count
                            try {
                                String _vote_count = response.body().getVote_count() + "";
                                section1_voteCountTV.setText(_vote_count);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Tagline
                            try {
                                String _tagline = response.body().getTagline() + "";
                                section1_tagLineTV.setText(_tagline);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Production Company
                            try {
                                movieProductionCompaniesList = response.body().getProduction_companies();

                                if (movieProductionCompaniesList != null) {
                                    if (movieProductionCompaniesList.size() != 0) {
                                        productionLayoutManager = new GridLayoutManager(MovieDetailActivity.this, 2);
                                        section4_productionCompaniesRV.setLayoutManager(productionLayoutManager);
                                        productionAdapter = new MovieProductionAdapter(MovieDetailActivity.this, movieProductionCompaniesList);
                                        section4_productionCompaniesRV.setAdapter(productionAdapter);
                                    }
                                }
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                        } catch (Exception e) {
                            hideProgress();
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(@NonNull Call<MovieDetailResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
            });
        } else {
            Log.d(LOG, "Id is -1");
        }
    }

    private void getCastDataFromJSON() {
        if (id != -1) {
            showProgress();

            Call<MovieCastResponse> call = movieCastService.getMovieCast(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<MovieCastResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieCastResponse> call, @NonNull Response<MovieCastResponse> response) {

                    if (response.body() != null) {
                        try {
                            movieCastList = response.body().getResults();

                            if (movieCastList != null) {
                                if (movieCastList.size() != 0) {
                                    castLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                                    ((LinearLayoutManager) castLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section3_castRV.setLayoutManager(castLayoutManager);
                                    castAdapter = new MovieCastAdapter(MovieDetailActivity.this, movieCastList);
                                    section3_castRV.setAdapter(castAdapter);
                                }
                            }
                        } catch (Exception e) {
                            hideProgress();
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(@NonNull Call<MovieCastResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    if (t.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + t.getLocalizedMessage());
                    }
                }
            });

        } else {
            hideProgress();
            Log.d(LOG, "id is -1");
        }
    }

    private void getSimilarDataFromJSON() {
        if (id != -1) {
            showProgress();

            Call<MovieSimilarResponse> call = movieSimilarService.getMovieSimilar(id, getResources().getString(R.string.api_key), 1);
            call.enqueue(new Callback<MovieSimilarResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieSimilarResponse> call, @NonNull Response<MovieSimilarResponse> response) {

                    if (response.body() != null) {
                        try {
                            movieSimilarList = response.body().getResults();

                            if (movieSimilarList != null) {
                                if (movieSimilarList.size() != 0) {
                                    similarLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                                    ((LinearLayoutManager) similarLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section5_similarRV.setLayoutManager(similarLayoutManager);
                                    similarAdapter = new MovieSimilarAdapter(MovieDetailActivity.this, movieSimilarList);
                                    section5_similarRV.setAdapter(similarAdapter);
                                }
                            }
                        } catch (Exception e) {
                            hideProgress();
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(@NonNull Call<MovieSimilarResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    if (t.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + t.getLocalizedMessage());
                    }
                }
            });

        } else {
            hideProgress();
            Log.d(LOG, "id is -1");
        }
    }

    private void getImageDataFromJSON() {
        if (id != -1) {
            showProgress();

            Call<MovieImageResponse> call = movieImageService.getMovieImages(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<MovieImageResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieImageResponse> call, @NonNull Response<MovieImageResponse> response) {

                    if (response.body() != null) {
                        try {
                            movieImageList = response.body().getBackdrops();

                            if (movieImageList != null) {
                                if (movieImageList.size() != 0) {

                                    movieSliderAdapter = new MovieSliderAdapter(MovieDetailActivity.this, movieImageList);
                                    section6_picturesSV.setSliderAdapter(movieSliderAdapter);
                                }
                            }
                        } catch (Exception e) {
                            hideProgress();
                            Log.d(LOG, errorSTR + e.getLocalizedMessage());
                        }
                    }

                    hideProgress();
                }

                @Override
                public void onFailure(@NonNull Call<MovieImageResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    if (t.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + t.getLocalizedMessage());
                    }
                }
            });

        } else {
            hideProgress();
            Log.d(LOG, "Id is -1");
        }
    }


    // Check Internet For More Similar
    private void checkInternetConnectionForMoreSimilar() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreSimilar();

        } else {
            Toast.makeText(FeedActivity.mContext, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    // Get Data For More Similar
    private void loadJSONForMoreSimilar() {
        section5_similarPB.setVisibility(View.VISIBLE);

        page++;

        Call<MovieSimilarResponse> call = movieSimilarService.getMovieSimilar(id, getResources().getString(R.string.api_key), page);
        call.enqueue(new Callback<MovieSimilarResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieSimilarResponse> call, @NonNull Response<MovieSimilarResponse> response) {

                try {
                    if (response.body() != null) {
                        movieSimilarList.addAll(response.body().getResults());
                    }
                    similarAdapter.notifyItemRangeInserted(movieSimilarList.size(), movieSimilarList.size());

                } catch (Exception e) {
                    section5_similarPB.setVisibility(View.INVISIBLE);
                    if (e.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    }
                }

                section5_similarPB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieSimilarResponse> call, @NonNull Throwable t) {
                section5_similarPB.setVisibility(View.INVISIBLE);
                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", v.getId());
        String buttonName;

        switch (v.getId()) {
            case R.id.moreTV:
                buttonName = "moreTV";
                handleMoreExpand();
                break;
            case R.id.mainFAB:
                buttonName = "mainFAB";
                handleFabExpand();
                break;
            case R.id.favoriteFAB:
                buttonName = "favoriteFAB";
                closeFAB();
                addToFavorite();
                break;
            case R.id.watch_laterFAB:
                buttonName = "watch_laterFAB";
                closeFAB();
                addToWatchLater();
                break;
            case R.id.watchedFAB:
                buttonName = "watchedFAB";
                closeFAB();
                addToWatched();
                break;
            default:
                buttonName = "";
                break;
        }

        mFirebaseAnalytics.logEvent(buttonName, params);
    }


    // Open Close
    private void handleMoreExpand() {
        if (isExpand) {
            moreTV.setText(R.string.more);
            collapse(section1_mainLL);
            isExpand = false;
        } else {
            moreTV.setText(R.string.less);
            expand(section1_mainLL);
            isExpand = true;
        }
    }

    private void handleFabExpand() {
        if (isOpen) {
            closeFAB();
        } else {
            openFAB();
        }
    }

    private static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void closeFAB() {
        favoriteTV.setVisibility(View.INVISIBLE);
        watch_laterTV.setVisibility(View.INVISIBLE);
        watchedTV.setVisibility(View.INVISIBLE);
        favoriteFAB.startAnimation(fab_close);
        watch_laterFAB.startAnimation(fab_close);
        watchedFAB.startAnimation(fab_close);
        mainFAB.startAnimation(fab_anti_clock);
        favoriteFAB.setClickable(false);
        watch_laterFAB.setClickable(false);
        watchedFAB.setClickable(false);
        isOpen = false;
    }

    private void openFAB() {
        favoriteTV.setVisibility(View.VISIBLE);
        watch_laterTV.setVisibility(View.VISIBLE);
        watchedTV.setVisibility(View.VISIBLE);
        favoriteFAB.startAnimation(fab_open);
        watch_laterFAB.startAnimation(fab_open);
        watchedFAB.startAnimation(fab_open);
        mainFAB.startAnimation(fab_clock);
        favoriteFAB.setClickable(true);
        watch_laterFAB.setClickable(true);
        watchedFAB.setClickable(true);
        isOpen = true;
    }


    // Add
    private void addToFavorite() {

        if (isContainForFavorite) {
            hideProgress();
            Toast.makeText(MovieDetailActivity.this, already_added, Toast.LENGTH_SHORT).show();
        } else {
            showProgress();

            try {
                if (mUser != null) {
                    if (id != -1) {
                        DatabaseReference mDatabaseReference = FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("Users")
                                .child(mUser.getUid())
                                .child("Movie")
                                .child("Favorites")
                                .child(String.valueOf(id));

                        // 1 ID
                        mDatabaseReference.child("id").setValue(String.valueOf(id));

                        // 2 TITLE
                        if (titleForDB != null) {
                            mDatabaseReference.child("title").setValue(titleForDB);
                        } else {
                            mDatabaseReference.child("title").setValue("N/A");
                        }

                        // 3 ORIGINAL TITLE
                        if (originalTitle != null) {
                            mDatabaseReference.child("original_title").setValue(originalTitle);
                        } else {
                            mDatabaseReference.child("original_title").setValue("N/A");
                        }

                        // 4 POSTER PATH
                        if (poster_pathForDB != null) {
                            mDatabaseReference.child("poster_path").setValue(poster_pathForDB);
                        } else {
                            mDatabaseReference.child("poster_path").setValue("N/A");
                        }

                        // 5 RELEASE DATE
                        if (release_dateForDB != null) {
                            mDatabaseReference.child("release_date").setValue(release_dateForDB);
                        } else {
                            mDatabaseReference.child("release_date").setValue("N/A");
                        }

                        // 6 GENRE
                        if (genreForDB != null) {
                            mDatabaseReference.child("genre").setValue(genreForDB);
                        } else {
                            mDatabaseReference.child("genre").setValue("N/A");
                        }

                        // 7 TIMESTAMP
                        if (currentTimeStamp != null) {
                            mDatabaseReference.child("time").setValue(currentTimeStamp).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    actionCount++;

                                    hideProgress();
                                    toastMessage = added_to_favorites;

                                    if (actionCount == 5) {
                                        actionCount = 0;

                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        if (interstitialAd != null) {
                                            if (interstitialAd.isLoaded()) {
                                                interstitialAd.show();
                                            } else {
                                                if (!isRate) {
                                                    showRateDialog();
                                                }
                                                Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    hideProgress();
                                    if (task.getException() != null) {
                                        Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            mDatabaseReference.child("time").setValue("N/A");
                        }
                    } else {
                        hideProgress();
                        Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        Log.d(LOG, "ID is null on the addToFavorite, MovieDetailActivity");
                    }
                } else {
                    hideProgress();
                    Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                    Log.d(LOG, "User is null on the addToFavorite, MovieDetailActivity");
                }
            } catch (Exception e) {
                hideProgress();
                if (e.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void addToWatchLater() {

        if (isContainForWatchLater) {
            hideProgress();
            Toast.makeText(MovieDetailActivity.this, already_added, Toast.LENGTH_SHORT).show();
        } else {
            showProgress();

            try {
                if (mUser != null) {
                    if (id != -1) {
                        DatabaseReference mDatabaseReference = FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("Users")
                                .child(mUser.getUid())
                                .child("Movie")
                                .child("Watch Later")
                                .child(String.valueOf(id));

                        // 1 ID
                        mDatabaseReference.child("id").setValue(String.valueOf(id));

                        // 2 TITLE
                        if (titleForDB != null) {
                            mDatabaseReference.child("title").setValue(titleForDB);
                        } else {
                            mDatabaseReference.child("title").setValue("N/A");
                        }

                        // 3 ORIGINAL TITLE
                        if (originalTitle != null) {
                            mDatabaseReference.child("original_title").setValue(originalTitle);
                        } else {
                            mDatabaseReference.child("original_title").setValue("N/A");
                        }

                        // 4 POSTER PATH
                        if (poster_pathForDB != null) {
                            mDatabaseReference.child("poster_path").setValue(poster_pathForDB);
                        } else {
                            mDatabaseReference.child("poster_path").setValue("N/A");
                        }

                        // 5 RELEASE DATE
                        if (release_dateForDB != null) {
                            mDatabaseReference.child("release_date").setValue(release_dateForDB);
                        } else {
                            mDatabaseReference.child("release_date").setValue("N/A");
                        }

                        // 6 GENRE
                        if (genreForDB != null) {
                            mDatabaseReference.child("genre").setValue(genreForDB);
                        } else {
                            mDatabaseReference.child("genre").setValue("N/A");
                        }

                        // 7 TIMESTAMP
                        if (currentTimeStamp != null) {
                            mDatabaseReference.child("time").setValue(currentTimeStamp).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    actionCount++;

                                    hideProgress();
                                    toastMessage = added_to_watch_later;

                                    if (actionCount == 5) {
                                        actionCount = 0;

                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        if (interstitialAd != null) {
                                            if (interstitialAd.isLoaded()) {
                                                interstitialAd.show();
                                            } else {
                                                if (!isRate) {
                                                    showRateDialog();
                                                }
                                                Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    hideProgress();
                                    if (task.getException() != null) {
                                        Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            mDatabaseReference.child("time").setValue("N/A");
                        }
                    } else {
                        hideProgress();
                        Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        Log.d(LOG, "ID is null on the addToFavorite, MovieDetailActivity");
                    }
                } else {
                    hideProgress();
                    Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                    Log.d(LOG, "User is null on the addToFavorite, MovieDetailActivity");
                }
            } catch (Exception e) {
                hideProgress();
                if (e.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void addToWatched() {

        if (isContainForWatched) {
            hideProgress();
            Toast.makeText(MovieDetailActivity.this, already_added, Toast.LENGTH_SHORT).show();
        } else {
            showProgress();

            try {
                if (mUser != null) {
                    if (id != -1) {
                        DatabaseReference mDatabaseReference = FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("Users")
                                .child(mUser.getUid())
                                .child("Movie")
                                .child("Watched")
                                .child(String.valueOf(id));

                        // 1 ID
                        mDatabaseReference.child("id").setValue(String.valueOf(id));

                        // 2 TITLE
                        if (titleForDB != null) {
                            mDatabaseReference.child("title").setValue(titleForDB);
                        } else {
                            mDatabaseReference.child("title").setValue("N/A");
                        }

                        // 3 ORIGINAL TITLE
                        if (originalTitle != null) {
                            mDatabaseReference.child("original_title").setValue(originalTitle);
                        } else {
                            mDatabaseReference.child("original_title").setValue("N/A");
                        }

                        // 4 POSTER PATH
                        if (poster_pathForDB != null) {
                            mDatabaseReference.child("poster_path").setValue(poster_pathForDB);
                        } else {
                            mDatabaseReference.child("poster_path").setValue("N/A");
                        }

                        // 5 RELEASE DATE
                        if (release_dateForDB != null) {
                            mDatabaseReference.child("release_date").setValue(release_dateForDB);
                        } else {
                            mDatabaseReference.child("release_date").setValue("N/A");
                        }

                        // 6 GENRE
                        if (genreForDB != null) {
                            mDatabaseReference.child("genre").setValue(genreForDB);
                        } else {
                            mDatabaseReference.child("genre").setValue("N/A");
                        }

                        // 7 TIMESTAMP
                        if (currentTimeStamp != null) {
                            mDatabaseReference.child("time").setValue(currentTimeStamp).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    actionCount++;

                                    hideProgress();
                                    toastMessage = added_to_watched;

                                    if (actionCount == 5) {
                                        actionCount = 0;

                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        if (interstitialAd != null) {
                                            if (interstitialAd.isLoaded()) {
                                                interstitialAd.show();
                                            } else {
                                                if (!isRate) {
                                                    showRateDialog();
                                                }
                                                Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        SharedPreferences.Editor editor =
                                                getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                        editor.putInt("actionCount", actionCount);
                                        editor.apply();

                                        Toast.makeText(MovieDetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    hideProgress();
                                    if (task.getException() != null) {
                                        Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                                        Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            mDatabaseReference.child("time").setValue("N/A");
                        }
                    } else {
                        hideProgress();
                        Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        Log.d(LOG, "ID is null on the addToFavorite, MovieDetailActivity");
                    }
                } else {
                    hideProgress();
                    Toast.makeText(this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                    Log.d(LOG, "User is null on the addToFavorite, MovieDetailActivity");
                }
            } catch (Exception e) {
                hideProgress();
                if (e.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                    Toast.makeText(MovieDetailActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // Others
    private void showRateDialog() {
        final AlertDialog dialog_rate_us = new AlertDialog.Builder(MovieDetailActivity.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.feed_dialog_rate_us, null);

        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView remindMeLaterTV = view.findViewById(R.id.remindMeLaterTV);

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            dialog_rate_us.dismiss();
            isRate = true;

            SharedPreferences.Editor editor =
                    getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE).edit();
            editor.putString("rate", "rate");
            editor.apply();

            Uri open_uri = Uri.parse("https://play.google.com/store/apps/details?id=com.muradismayilov.martiandeveloper.moviesaverapp");
            Intent open_intent = new Intent(Intent.ACTION_VIEW, open_uri);
            startActivity(open_intent);
        });

        remindMeLaterTV.setOnClickListener(v -> dialog_rate_us.dismiss());

        dialog_rate_us.setView(view);
        dialog_rate_us.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgress() {
        coordinatorLayout.setAlpha(0.5f);
        mainPB.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        coordinatorLayout.setAlpha(1.0f);
        mainPB.setVisibility(View.INVISIBLE);
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
}
