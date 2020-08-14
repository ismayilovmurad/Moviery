package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvCast;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailCreatedBy;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailNetworks;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailProductionCompanies;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvDetailSeasons;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvImage;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvSimilar;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvVideo;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_cast_recycler.TvCastAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_created_by_recycler.TvCreatedByAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_networks_recycler.TvNetworksAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_production_recycler.TvProductionAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_seasons_recycler.TvSeasonsAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_similar_recycler.TvSimilarAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvCastResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvDetailResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvImageResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvSimilarResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvVideoResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvCastService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvDetailService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvImageService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvSimilarService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvVideoService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools.TvSliderAdapter;
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

public class ProfileActivityTvDetail extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.section0_moreTV)
    TextView section0_moreTV;
    @BindView(R.id.section1_overviewTV)
    TextView section1_overviewTV;
    @BindView(R.id.section1_lastAirDateTV)
    TextView section1_lastAirDateTV;
    @BindView(R.id.section1_originalLanguageTV)
    TextView section1_originalLanguageTV;
    @BindView(R.id.section1_popularityTV)
    TextView section1_popularityTV;
    @BindView(R.id.section1_statusTV)
    TextView section1_statusTV;
    @BindView(R.id.section1_seasonsTV)
    TextView section1_seasonsTV;
    @BindView(R.id.section1_voteCountTV)
    TextView section1_voteCountTV;
    @BindView(R.id.section1_episodesTV)
    TextView section1_episodesTV;
    @BindView(R.id.section2_trailerTV)
    TextView section2_trailerTV;
    @BindView(R.id.section6_airDateTV)
    TextView section6_airDateTV;
    @BindView(R.id.section6_episodeTV)
    TextView section6_episodeTV;
    @BindView(R.id.section6_nameTV)
    TextView section6_nameTV;
    @BindView(R.id.section6_seasonTV)
    TextView section6_seasonTV;
    @BindView(R.id.section6_overviewTV)
    TextView section6_overviewTV;
    @BindView(R.id.section6_voteAverageTV)
    TextView section6_voteAverageTV;
    @BindView(R.id.section6_voteCountTV)
    TextView section6_voteCountTV;
    @BindView(R.id.section7_airDateTV)
    TextView section7_airDateTV;
    @BindView(R.id.section7_episodeTV)
    TextView section7_episodeTV;
    @BindView(R.id.section7_nameTV)
    TextView section7_nameTV;
    @BindView(R.id.section7_seasonTV)
    TextView section7_seasonTV;
    @BindView(R.id.section7_overviewTV)
    TextView section7_overviewTV;
    @BindView(R.id.section7_voteAverageTV)
    TextView section7_voteAverageTV;
    @BindView(R.id.section7_voteCountTV)
    TextView section7_voteCountTV;
    // ImageView
    @BindView(R.id.backdropIV)
    ImageView backdropIV;
    @BindView(R.id.section0_posterIV)
    ImageView section0_posterIV;
    @BindView(R.id.section2_watchTvInfoIV)
    ImageView section2_watchTvInfoIV;
    @BindView(R.id.section6_posterIV)
    ImageView section6_posterIV;
    @BindView(R.id.section7_posterIV)
    ImageView section7_posterIV;
    // LinearLayout
    @BindView(R.id.section1_mainLL)
    LinearLayout section1_mainLL;
    // CoordinatorLayout
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    // RecyclerView
    @BindView(R.id.section4_productionCompaniesRV)
    RecyclerView section4_productionCompaniesRV;
    @BindView(R.id.section3_castRV)
    RecyclerView section3_castRV;
    @BindView(R.id.section10_similarRV)
    RecyclerView section10_similarRV;
    @BindView(R.id.section5_createdByRV)
    RecyclerView section5_createdByRV;
    @BindView(R.id.section8_seasonsRV)
    RecyclerView section8_seasonsRV;
    @BindView(R.id.section9_networksRV)
    RecyclerView section9_networksRV;
    // ToolBar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // ProgressBar
    @BindView(R.id.mainPB)
    ProgressBar mainPB;
    @BindView(R.id.section10_similarPB)
    ProgressBar section10_similarPB;
    // SliderView
    @BindView(R.id.section11_picturesSV)
    SliderView section11_picturesSV;
    // FloatingActionButton
    @BindView(R.id.mainFAB)
    FloatingActionButton mainFAB;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;
    @BindView(R.id.section2_comingSoonFL)
    FrameLayout section2_comingSoonFL;
    // View
    @BindView(R.id.section2_view)
    View section2_view;
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
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;
    // Id
    @BindString(R.string.banner_ad_for_detail)
    String banner_ad_for_tv_detail;
    @BindString(R.string.interstitial_ad_for_add_delete)
    String interstitial_ad_for_add;
    @BindString(R.string.delete_successful)
    String delete_successful;

    // Variables
    // Integer
    private int id, page, actionCount;
    // String
    private String originalName, premium, type, videoKey, videoName;
    // Final
    private final String LOG = "MartianDeveloper";
    private final String ACTION_COUNT_SHARED_PREFERENCES = "actionCount";
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    // List
    private List<TvDetailProductionCompanies> tvProductionCompaniesList;
    private List<TvCast> tvCastList;
    private List<TvSimilar> tvSimilarList;
    private List<TvImage> tvImageList;
    private List<TvVideo> tvVideoList;
    private List<TvDetailCreatedBy> tvCreatedByList;
    private List<TvDetailSeasons> tvSeasonsList;
    private List<TvDetailNetworks> tvNetworksList;
    // Adapter
    private RecyclerView.Adapter productionAdapter;
    private RecyclerView.Adapter castAdapter;
    private RecyclerView.Adapter similarAdapter;
    private RecyclerView.Adapter createdByAdapter;
    private RecyclerView.Adapter seasonsAdapter;
    private RecyclerView.Adapter networksAdapter;
    // LayoutManager
    private RecyclerView.LayoutManager productionLayoutManager;
    private RecyclerView.LayoutManager castLayoutManager;
    private RecyclerView.LayoutManager similarLayoutManager;
    private RecyclerView.LayoutManager createdByLayoutManager;
    private RecyclerView.LayoutManager seasonsLayoutManager;
    private RecyclerView.LayoutManager networksLayoutManager;
    // Boolean
    private boolean isExpand;
    // Slider
    private TvSliderAdapter tvSliderAdapter;
    // FireBase
    private FirebaseUser mUser;
    // Service
    private TvCastService tvCastService;
    private TvDetailService tvDetailService;
    private TvSimilarService tvSimilarService;
    private TvImageService tvImageService;
    private TvVideoService tvVideoService;
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
        setContentView(R.layout.profile_activity_tv_detail);
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
        hideProgress();

        collapse(section1_mainLL);
        section10_similarPB.setVisibility(View.INVISIBLE);

        section11_picturesSV.setIndicatorAnimation(IndicatorAnimations.WORM);
        section11_picturesSV.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
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
        originalName = getIntent().getStringExtra("originalName");
        type = getIntent().getStringExtra("type");

        // Boolean
        isExpand = false;

        // FireBase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // String
        premium = "";
        videoKey = "";
        videoName = "";

        // Client & Service
        Client castClient = new Client();
        tvCastService = castClient.getClient().create(TvCastService.class);
        Client detailClient = new Client();
        tvDetailService = detailClient.getClient().create(TvDetailService.class);
        Client similarClient = new Client();
        tvSimilarService = similarClient.getClient().create(TvSimilarService.class);
        Client imageClient = new Client();
        tvImageService = imageClient.getClient().create(TvImageService.class);
        Client videoClient = new Client();
        tvVideoService = videoClient.getClient().create(TvVideoService.class);

        // Integer
        page = 1;

        SharedPreferences sharedPreferences = getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE);
        actionCount = sharedPreferences.getInt("actionCount", 0);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ProfileActivityTvDetail.this.setTitle(originalName);
    }

    private void setListeners() {
        // TextView
        section0_moreTV.setOnClickListener(this);
        // FloatingActionButton
        mainFAB.setOnClickListener(this);
        // RecyclerView
        section10_similarRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollHorizontally(1)) {

                    checkInternetConnectionForMoreSimilar();
                }
            }

        });

        section2_watchTvInfoIV.setOnClickListener(view -> showComingSoonDialog());
    }

    private void showComingSoonDialog() {
        final AlertDialog dialog_note = new AlertDialog.Builder(ProfileActivityTvDetail.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.feed_dialog_coming_soon, null);

        final Button okayBTN = view.findViewById(R.id.okayBTN);

        okayBTN.setOnClickListener(v -> dialog_note.dismiss());

        dialog_note.setView(view);
        dialog_note.setCanceledOnTouchOutside(false);
        dialog_note.show();
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
        adView.setAdUnitId(banner_ad_for_tv_detail);
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
                hideProgress();
                Toast.makeText(ProfileActivityTvDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(ProfileActivityTvDetail.this::finish, 500);
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


    // Get
    private void getVideoDataFromJSON() {

        if (id != -1) {
            showProgress();

            Call<TvVideoResponse> call = tvVideoService.getTvVideos(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<TvVideoResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(@NonNull Call<TvVideoResponse> call, @NonNull Response<TvVideoResponse> response) {

                    try {
                        if (response.body() != null) {
                            tvVideoList = response.body().getResults();

                            if (tvVideoList.size() != 0) {
                                videoKey = tvVideoList.get(0).getKey();
                                videoName = tvVideoList.get(1).getName();

                                section2_trailerTV.setText(videoName);

                                section2_youTubePlayerView.getPlayerUiController().showPlayPauseButton(false);

                                section2_view.setOnClickListener(view -> {
                                    Intent intent = new Intent(ProfileActivityTvDetail.this, FullScreenVideoActivity.class);
                                    intent.putExtra("videoKey", videoKey);

                                    //noinspection unchecked
                                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            ProfileActivityTvDetail.this,
                                            new Pair<>(section2_youTubePlayerView,
                                                    FullScreenVideoActivity.VIEW_NAME_HEADER_IMAGE));

                                    ActivityCompat.startActivity(ProfileActivityTvDetail.this, intent, activityOptions.toBundle());
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

                                            videoKey = tvVideoList.get(0).getKey();
                                            videoName = tvVideoList.get(0).getName();

                                            section2_trailerTV.setText(videoName);

                                            section2_youTubePlayerView.setVisibility(View.VISIBLE);
                                            section2_youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.cueVideo(videoKey, 0));

                                            break;
                                        case R.id.section2_trailer2RB:
                                            section2_comingSoonFL.setVisibility(View.INVISIBLE);

                                            if (tvVideoList.size() > 1) {
                                                videoKey = tvVideoList.get(1).getKey();
                                                videoName = tvVideoList.get(1).getName();
                                            } else {
                                                videoKey = "";
                                                videoName = "";
                                            }

                                            section2_trailerTV.setText(videoName);

                                            section2_youTubePlayerView.setVisibility(View.VISIBLE);
                                            section2_youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.cueVideo(videoKey, 0));
                                            break;
                                        case R.id.section2_watchTvRB:
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
                public void onFailure(@NonNull Call<TvVideoResponse> call, @NonNull Throwable t) {
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
            Call<TvDetailResponse> call = tvDetailService.getTvDetail(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<TvDetailResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvDetailResponse> call, @NonNull Response<TvDetailResponse> response) {

                    if (response.body() != null) {
                        try {
                            // Poster
                            try {
                                Glide.with(ProfileActivityTvDetail.this)
                                        .load(response.body().getPoster_path())
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .fitCenter()
                                        .centerCrop()
                                        .placeholder(R.drawable.image_placeholder)
                                        .priority(Priority.IMMEDIATE)
                                        .into(section0_posterIV);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Backdrop
                            try {
                                Glide.with(ProfileActivityTvDetail.this)
                                        .load(response.body().getBackdrop_path())
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .fitCenter()
                                        .centerCrop()
                                        .placeholder(R.drawable.image_placeholder)
                                        .priority(Priority.IMMEDIATE)
                                        .into(backdropIV);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Runtime
                            try {
                                String _episode_runtime = response.body().getEpisode_run_time() + "";
                                section0_runtimeTV.setText(_episode_runtime);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Release Date
                            try {
                                String _first_air_date = response.body().getFirst_air_date() + "";
                                section0_releasedTV.setText(_first_air_date);

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

                            // Original Language
                            try {
                                String _original_language = response.body().getOriginal_language() + "";
                                section1_originalLanguageTV.setText(_original_language);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Last Air Date
                            try {
                                String _last_air_date = response.body().getLast_air_date() + "";
                                section1_lastAirDateTV.setText(_last_air_date);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Number of Episodes
                            try {
                                String _number_of_episodes = response.body().getNumber_of_episodes() + "";
                                section1_episodesTV.setText(_number_of_episodes);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Number of Seasons
                            try {
                                String _number_of_seasons = response.body().getNumber_of_seasons() + "";
                                section1_seasonsTV.setText(_number_of_seasons);

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

                            // Vote Count
                            try {
                                String _vote_count = response.body().getVote_count() + "";
                                section1_voteCountTV.setText(_vote_count);

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

                            // Production Company
                            try {
                                tvProductionCompaniesList = response.body().getProduction_companies();

                                if (tvProductionCompaniesList != null) {
                                    if (tvProductionCompaniesList.size() != 0) {
                                        productionLayoutManager = new GridLayoutManager(ProfileActivityTvDetail.this, 2);
                                        section4_productionCompaniesRV.setLayoutManager(productionLayoutManager);
                                        productionAdapter = new TvProductionAdapter(ProfileActivityTvDetail.this, tvProductionCompaniesList);
                                        section4_productionCompaniesRV.setAdapter(productionAdapter);
                                    }
                                }
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Created By
                            try {
                                tvCreatedByList = response.body().getCreated_by();

                                if (tvCreatedByList != null) {
                                    if (tvCreatedByList.size() != 0) {
                                        createdByLayoutManager = new LinearLayoutManager(ProfileActivityTvDetail.this);
                                        ((LinearLayoutManager) createdByLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                        section5_createdByRV.setLayoutManager(createdByLayoutManager);
                                        createdByAdapter = new TvCreatedByAdapter(ProfileActivityTvDetail.this, tvCreatedByList);
                                        section5_createdByRV.setAdapter(createdByAdapter);
                                    }
                                }
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Last Episode To Air
                            try {
                                // Poster
                                Glide.with(ProfileActivityTvDetail.this)
                                        .load(response.body().getLast_episode_to_air().getStill_path())
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .fitCenter()
                                        .centerCrop()
                                        .placeholder(R.drawable.image_placeholder)
                                        .priority(Priority.IMMEDIATE)
                                        .into(section6_posterIV);

                                // Air Date
                                String _air_date = response.body().getLast_episode_to_air().getAir_date() + "";
                                section6_airDateTV.setText(_air_date);

                                // Episode
                                String _episode = response.body().getLast_episode_to_air().getEpisode_number() + "";
                                section6_episodeTV.setText(_episode);

                                // Name
                                String _name = response.body().getLast_episode_to_air().getName() + "";
                                section6_nameTV.setText(_name);

                                // Season
                                String _season = response.body().getLast_episode_to_air().getSeason_number() + "";
                                section6_seasonTV.setText(_season);

                                // Vote Average
                                String _vote_average = response.body().getLast_episode_to_air().getVote_average() + "";
                                section6_voteAverageTV.setText(_vote_average);

                                // Vote Count
                                String _vote_count = response.body().getLast_episode_to_air().getVote_count() + "";
                                section6_voteCountTV.setText(_vote_count);

                                // Vote Count
                                String _overview = response.body().getLast_episode_to_air().getOverview() + "";
                                section6_overviewTV.setText(_overview);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Next Episode To Air
                            try {
                                // Poster
                                Glide.with(ProfileActivityTvDetail.this)
                                        .load(response.body().getNext_episode_to_air().getStill_path())
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .fitCenter()
                                        .centerCrop()
                                        .placeholder(R.drawable.image_placeholder)
                                        .priority(Priority.IMMEDIATE)
                                        .into(section7_posterIV);

                                // Air Date
                                String _air_date = response.body().getNext_episode_to_air().getAir_date() + "";
                                section7_airDateTV.setText(_air_date);

                                // Episode
                                String _episode = response.body().getNext_episode_to_air().getEpisode_number() + "";
                                section7_episodeTV.setText(_episode);

                                // Name
                                String _name = response.body().getNext_episode_to_air().getName() + "";
                                section7_nameTV.setText(_name);

                                // Season
                                String _season = response.body().getNext_episode_to_air().getSeason_number() + "";
                                section7_seasonTV.setText(_season);

                                // Vote Average
                                String _vote_average = response.body().getNext_episode_to_air().getVote_average() + "";
                                section7_voteAverageTV.setText(_vote_average);

                                // Vote Count
                                String _vote_count = response.body().getNext_episode_to_air().getVote_count() + "";
                                section7_voteCountTV.setText(_vote_count);

                                // Vote Count
                                String _overview = response.body().getNext_episode_to_air().getOverview() + "";
                                section7_overviewTV.setText(_overview);

                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Seasons
                            try {
                                tvSeasonsList = response.body().getSeasons();

                                if (tvSeasonsList != null) {
                                    if (tvSeasonsList.size() != 0) {
                                        seasonsLayoutManager = new LinearLayoutManager(ProfileActivityTvDetail.this);
                                        ((LinearLayoutManager) seasonsLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                        section8_seasonsRV.setLayoutManager(seasonsLayoutManager);
                                        seasonsAdapter = new TvSeasonsAdapter(ProfileActivityTvDetail.this, tvSeasonsList);
                                        section8_seasonsRV.setAdapter(seasonsAdapter);
                                    }
                                }
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Networks
                            try {
                                tvNetworksList = response.body().getNetworks();

                                if (tvNetworksList != null) {
                                    if (tvNetworksList.size() != 0) {
                                        networksLayoutManager = new GridLayoutManager(ProfileActivityTvDetail.this, 2);
                                        section9_networksRV.setLayoutManager(networksLayoutManager);
                                        networksAdapter = new TvNetworksAdapter(ProfileActivityTvDetail.this, tvNetworksList);
                                        section9_networksRV.setAdapter(networksAdapter);
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
                public void onFailure(@NonNull Call<TvDetailResponse> call, @NonNull Throwable t) {
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

            Call<TvCastResponse> call = tvCastService.getTvCast(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<TvCastResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvCastResponse> call, @NonNull Response<TvCastResponse> response) {

                    if (response.body() != null) {
                        try {
                            tvCastList = response.body().getCast();

                            if (tvCastList != null) {
                                if (tvCastList.size() != 0) {
                                    castLayoutManager = new LinearLayoutManager(ProfileActivityTvDetail.this);
                                    ((LinearLayoutManager) castLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section3_castRV.setLayoutManager(castLayoutManager);
                                    castAdapter = new TvCastAdapter(ProfileActivityTvDetail.this, tvCastList);
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
                public void onFailure(@NonNull Call<TvCastResponse> call, @NonNull Throwable t) {
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

            Call<TvSimilarResponse> call = tvSimilarService.getTvSimilar(id, getResources().getString(R.string.api_key), 1);
            call.enqueue(new Callback<TvSimilarResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvSimilarResponse> call, @NonNull Response<TvSimilarResponse> response) {

                    if (response.body() != null) {
                        try {
                            tvSimilarList = response.body().getResults();

                            if (tvSimilarList != null) {
                                if (tvSimilarList.size() != 0) {
                                    similarLayoutManager = new LinearLayoutManager(ProfileActivityTvDetail.this);
                                    ((LinearLayoutManager) similarLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section10_similarRV.setLayoutManager(similarLayoutManager);
                                    similarAdapter = new TvSimilarAdapter(ProfileActivityTvDetail.this, tvSimilarList);
                                    section10_similarRV.setAdapter(similarAdapter);
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
                public void onFailure(@NonNull Call<TvSimilarResponse> call, @NonNull Throwable t) {
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

            Call<TvImageResponse> call = tvImageService.getTvImages(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<TvImageResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvImageResponse> call, @NonNull Response<TvImageResponse> response) {

                    if (response.body() != null) {
                        try {
                            tvImageList = response.body().getBackdrops();

                            if (tvImageList != null) {
                                if (tvImageList.size() != 0) {

                                    tvSliderAdapter = new TvSliderAdapter(ProfileActivityTvDetail.this, tvImageList);
                                    section11_picturesSV.setSliderAdapter(tvSliderAdapter);
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
                public void onFailure(@NonNull Call<TvImageResponse> call, @NonNull Throwable t) {
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
        section10_similarPB.setVisibility(View.VISIBLE);

        page++;

        Call<TvSimilarResponse> call = tvSimilarService.getTvSimilar(id, getResources().getString(R.string.api_key), page);
        call.enqueue(new Callback<TvSimilarResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvSimilarResponse> call, @NonNull Response<TvSimilarResponse> response) {

                try {
                    if (response.body() != null) {
                        tvSimilarList.addAll(response.body().getResults());
                    }
                    similarAdapter.notifyItemRangeInserted(tvSimilarList.size(), tvSimilarList.size());

                } catch (Exception e) {
                    section10_similarPB.setVisibility(View.INVISIBLE);
                    if (e.getLocalizedMessage() != null) {
                        Log.d(LOG, errorSTR + e.getLocalizedMessage());
                    }
                }

                section10_similarPB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<TvSimilarResponse> call, @NonNull Throwable t) {
                section10_similarPB.setVisibility(View.INVISIBLE);
                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.section0_moreTV:
                handleMoreExpand();
                break;
            case R.id.mainFAB:
                removeFromDB();
                break;
        }
    }


    // Remove
    private void removeFromDB() {
        showProgress();

        try {
            if (mUser != null) {
                if (id != -1) {
                    DatabaseReference mDatabaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(mUser.getUid())
                            .child("Tv")
                            .child(type)
                            .child(String.valueOf(id));

                    mDatabaseReference.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            actionCount++;

                            hideProgress();

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
                                        hideProgress();
                                        Toast.makeText(ProfileActivityTvDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                        final Handler handler = new Handler();
                                        handler.postDelayed(ProfileActivityTvDetail.this::finish, 500);
                                    }
                                } else {
                                    hideProgress();
                                    Toast.makeText(ProfileActivityTvDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(ProfileActivityTvDetail.this::finish, 500);
                                }
                            } else {
                                SharedPreferences.Editor editor =
                                        getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                editor.putInt("actionCount", actionCount);
                                editor.apply();

                                Toast.makeText(ProfileActivityTvDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                final Handler handler = new Handler();
                                handler.postDelayed(ProfileActivityTvDetail.this::finish, 500);
                            }
                        } else {
                            hideProgress();
                            if (task.getException() != null) {
                                Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                Toast.makeText(ProfileActivityTvDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                                Toast.makeText(ProfileActivityTvDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
                Toast.makeText(ProfileActivityTvDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            } else {
                Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                Toast.makeText(ProfileActivityTvDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        }

    }


    // Open Close
    private void handleMoreExpand() {
        if (isExpand) {
            section0_moreTV.setText(R.string.more);
            collapse(section1_mainLL);
            isExpand = false;
        } else {
            section0_moreTV.setText(R.string.less);
            expand(section1_mainLL);
            isExpand = true;
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
