package com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_activity;

import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.muradismayilov.martiandeveloper.moviesaverapp.common.tools.Client;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonImage;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonMovie;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_model.PersonTv;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_recycler.person_movie_recycler.PersonMovieAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_recycler.person_tv_recycler.PersonTvAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonDetailResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonImageResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonMovieResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_response.PersonTvResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service.PersonDetailService;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service.PersonImageService;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service.PersonMovieService;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_service.PersonTvService;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_tools.PersonSliderAdapter;
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

public class ProfileActivityPersonDetail extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    // TextView
    @BindView(R.id.section0_moreTV)
    TextView section0_moreTV;
    @BindView(R.id.section0_birthdayTV)
    TextView section0_birthdayTV;
    @BindView(R.id.section0_knownForTV)
    TextView section0_knownForTV;
    @BindView(R.id.section0_popularityTV)
    TextView section0_popularityTV;
    @BindView(R.id.section1_placeOfBirthTV)
    TextView section1_placeOfBirthTV;
    @BindView(R.id.section1_biographyTV)
    TextView section1_biographyTV;
    // ImageView
    @BindView(R.id.backdropIV)
    ImageView backdropIV;
    @BindView(R.id.section0_posterIV)
    ImageView section0_posterIV;
    // Layout
    @BindView(R.id.section1_mainLL)
    LinearLayout section1_mainLL;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    // RecyclerView
    @BindView(R.id.section2_moviesRV)
    RecyclerView section2_moviesRV;
    @BindView(R.id.section3_tvRV)
    RecyclerView section3_tvRV;
    // ToolBar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // ProgressBar
    @BindView(R.id.mainPB)
    ProgressBar mainPB;
    // SliderView
    @BindView(R.id.section4_picturesSV)
    SliderView section4_picturesSV;
    // FloatingActionButton
    @BindView(R.id.mainFAB)
    FloatingActionButton mainFAB;
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
    @BindString(R.string.delete_successful)
    String delete_successful;
    // Id
    @BindString(R.string.banner_ad_for_detail)
    String banner_ad_for_person_detail;
    @BindString(R.string.interstitial_ad_for_add_delete)
    String interstitial_ad_for_add;

    // Variables
    // Integer
    private int id, actionCount;
    // String
    private String name, premium;
    // Final
    private final String LOG = "MartianDeveloper";
    private final String ACTION_COUNT_SHARED_PREFERENCES = "actionCount";
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    // List
    private List<PersonMovie> personMovieList;
    private List<PersonTv> personTvList;
    private List<PersonImage> personImageList;
    // Adapter
    private RecyclerView.Adapter personMovieAdapter;
    private RecyclerView.Adapter personTvAdapter;
    // LayoutManager
    private RecyclerView.LayoutManager personMovieLayoutManager;
    private RecyclerView.LayoutManager personTvLayoutManager;
    // Boolean
    private boolean isExpand;
    // Slider
    private PersonSliderAdapter personSliderAdapter;
    // FireBase
    private FirebaseUser mUser;
    // Service
    private PersonMovieService personMovieService;
    private PersonDetailService personDetailService;
    private PersonImageService personImageService;
    private PersonTvService personTvService;
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
        setContentView(R.layout.profile_activity_person_detail);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        declareVariables();
        setToolbar();
        setListeners();
        hideProgress();

        collapse(section1_mainLL);

        section4_picturesSV.setIndicatorAnimation(IndicatorAnimations.WORM);
        section4_picturesSV.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
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
        name = getIntent().getStringExtra("name");

        // String
        premium = "";

        // Boolean
        isExpand = false;

        // FireBase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Client & Service
        Client movieClient = new Client();
        personMovieService = movieClient.getClient().create(PersonMovieService.class);
        Client detailClient = new Client();
        personDetailService = detailClient.getClient().create(PersonDetailService.class);
        Client imageClient = new Client();
        personImageService = imageClient.getClient().create(PersonImageService.class);
        Client tvClient = new Client();
        personTvService = tvClient.getClient().create(PersonTvService.class);

        // Integer
        SharedPreferences sharedPreferences = getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE);
        actionCount = sharedPreferences.getInt("actionCount", 0);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ProfileActivityPersonDetail.this.setTitle(name);
    }

    private void setListeners() {
        // TextView
        section0_moreTV.setOnClickListener(this);
        // FloatingActionButton
        mainFAB.setOnClickListener(this);
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
        adView.setAdUnitId(banner_ad_for_person_detail);
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
                Toast.makeText(ProfileActivityPersonDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(ProfileActivityPersonDetail.this::finish, 500);
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
            getDetailDataFromJSON();
            getMovieDataFromJSON();
            getTvDataFromJSON();
            getImageDataFromJSON();

        } else {
            hideProgress();
            Toast.makeText(this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
        }
    }


    // Get
    private void getDetailDataFromJSON() {

        if (id != -1) {
            showProgress();
            Call<PersonDetailResponse> call = personDetailService.getPersonDetail(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<PersonDetailResponse>() {
                @Override
                public void onResponse(@NonNull Call<PersonDetailResponse> call, @NonNull Response<PersonDetailResponse> response) {

                    if (response.body() != null) {
                        try {
                            // Profile
                            try {
                                Glide.with(ProfileActivityPersonDetail.this)
                                        .load(response.body().getProfile_path())
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

                            // Birthday
                            try {
                                String _birthday = response.body().getBirthday() + "";
                                section0_birthdayTV.setText(_birthday);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Known For
                            try {
                                String _known_for = response.body().getKnown_for_department() + "";
                                section0_knownForTV.setText(_known_for);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Popularity
                            try {
                                String _popularity = response.body().getPopularity() + "";
                                section0_popularityTV.setText(_popularity);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Biography
                            try {
                                String _biography = response.body().getBiography() + "";
                                section1_biographyTV.setText(_biography);
                            } catch (Exception e) {
                                hideProgress();
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }

                            // Place of Birth
                            try {
                                String _place_of_birth = response.body().getPlaceOfBirth() + "";
                                section1_placeOfBirthTV.setText(_place_of_birth);
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
                public void onFailure(@NonNull Call<PersonDetailResponse> call, @NonNull Throwable t) {
                    hideProgress();
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
            });
        } else {
            Log.d(LOG, "Id is -1");
        }
    }

    private void getMovieDataFromJSON() {
        if (id != -1) {
            showProgress();

            Call<PersonMovieResponse> call = personMovieService.getPersonMovie(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<PersonMovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<PersonMovieResponse> call, @NonNull Response<PersonMovieResponse> response) {

                    if (response.body() != null) {
                        try {
                            personMovieList = response.body().getCast();

                            if (personMovieList != null) {
                                if (personMovieList.size() != 0) {
                                    personMovieLayoutManager = new LinearLayoutManager(ProfileActivityPersonDetail.this);
                                    ((LinearLayoutManager) personMovieLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section2_moviesRV.setLayoutManager(personMovieLayoutManager);
                                    personMovieAdapter = new PersonMovieAdapter(ProfileActivityPersonDetail.this, personMovieList);
                                    section2_moviesRV.setAdapter(personMovieAdapter);
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
                public void onFailure(@NonNull Call<PersonMovieResponse> call, @NonNull Throwable t) {
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

    private void getTvDataFromJSON() {
        if (id != -1) {
            showProgress();

            Call<PersonTvResponse> call = personTvService.getPersonTv(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<PersonTvResponse>() {
                @Override
                public void onResponse(@NonNull Call<PersonTvResponse> call, @NonNull Response<PersonTvResponse> response) {

                    if (response.body() != null) {
                        try {
                            personTvList = response.body().getCast();

                            if (personTvList != null) {
                                if (personTvList.size() != 0) {
                                    personTvLayoutManager = new LinearLayoutManager(ProfileActivityPersonDetail.this);
                                    ((LinearLayoutManager) personTvLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                                    section3_tvRV.setLayoutManager(personTvLayoutManager);
                                    personTvAdapter = new PersonTvAdapter(ProfileActivityPersonDetail.this, personTvList);
                                    section3_tvRV.setAdapter(personTvAdapter);
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
                public void onFailure(@NonNull Call<PersonTvResponse> call, @NonNull Throwable t) {
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

            Call<PersonImageResponse> call = personImageService.getPersonImages(id, getResources().getString(R.string.api_key));
            call.enqueue(new Callback<PersonImageResponse>() {
                @Override
                public void onResponse(@NonNull Call<PersonImageResponse> call, @NonNull Response<PersonImageResponse> response) {

                    if (response.body() != null) {
                        try {
                            personImageList = response.body().getProfiles();

                            if (personImageList != null) {
                                if (personImageList.size() != 0) {

                                    try {
                                        Glide.with(ProfileActivityPersonDetail.this)
                                                .load(personImageList.get(0).getFile_path())
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

                                    personSliderAdapter = new PersonSliderAdapter(ProfileActivityPersonDetail.this, personImageList);
                                    section4_picturesSV.setSliderAdapter(personSliderAdapter);
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
                public void onFailure(@NonNull Call<PersonImageResponse> call, @NonNull Throwable t) {
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
                            .child("Person")
                            .child("Favorites")
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
                                        Toast.makeText(ProfileActivityPersonDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                        final Handler handler = new Handler();
                                        handler.postDelayed(ProfileActivityPersonDetail.this::finish, 500);
                                    }
                                } else {
                                    hideProgress();
                                    Toast.makeText(ProfileActivityPersonDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(ProfileActivityPersonDetail.this::finish, 500);
                                }
                            } else {
                                SharedPreferences.Editor editor =
                                        getSharedPreferences(ACTION_COUNT_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                                editor.putInt("actionCount", actionCount);
                                editor.apply();

                                Toast.makeText(ProfileActivityPersonDetail.this, delete_successful, Toast.LENGTH_SHORT).show();

                                final Handler handler = new Handler();
                                handler.postDelayed(ProfileActivityPersonDetail.this::finish, 500);
                            }
                        } else {
                            hideProgress();
                            if (task.getException() != null) {
                                Log.d(LOG, errorSTR + task.getException().getLocalizedMessage());
                                Toast.makeText(ProfileActivityPersonDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                                Toast.makeText(ProfileActivityPersonDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ProfileActivityPersonDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            } else {
                Log.d(LOG, "Exception is null! Cause is on the addToFavorite, MovieDetailActivity");
                Toast.makeText(ProfileActivityPersonDetail.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
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
