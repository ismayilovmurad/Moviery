package com.muradismayilov.martiandeveloper.moviesaverapp.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.auth.LogInActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.notification.AlertReceiver;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment.MovieFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_fragment.PersonFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.profile.profile_fragment.ProfileFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_activity.SearchActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment.TvFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    // UI Components
    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // DrawerLayout
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    // NavigationView
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    // ImageView
    @BindView(R.id.themoviedbIV)
    ImageView themoviedbIV;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;
    // Appbar
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    // Strings
    @BindString(R.string.movies)
    String movies;
    @BindString(R.string.series_n_tv_shows)
    String series_n_tv_shows;
    @BindString(R.string.actors)
    String actors;
    @BindString(R.string.check_out)
    String check_out;
    @BindString(R.string.share_via)
    String share_via;
    @BindString(R.string.app_name)
    String app_name;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.banner_ad_for_feed)
    String banner_ad_for_feed;
    @BindString(R.string.no_internet_connection)
    String no_internet_connectionSTR;
    @BindString(R.string.payment_canceled)
    String payment_canceled;
    @BindString(R.string.payment_successful)
    String payment_successful;
    @BindString(R.string.already_premium)
    String already_premium;

    // Variables
    // Context
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;
    // ActionBarDrawerToggle
    private ActionBarDrawerToggle drawerToggle;
    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    // String
    private String LOG, username, premium;
    // BillingClient
    private BillingClient mBillingClient;
    // List
    private List<SkuDetails> inAPPList;
    private ArrayList<Integer> notificationTimeList;
    // Boolean
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.feed_activity_home);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        setToolbar();
        declareVariables();
        getUsername();
        setTitle(movies);
        handleDrawer();
        setFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForAds();
        setBilling();
        setTMDBLogo();
    }

    private void setTMDBLogo() {
        Glide.with(FeedActivity.this)
                .load(R.drawable.powered_by_themoviedb)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_placeholder)
                .priority(Priority.IMMEDIATE)
                .into(themoviedbIV);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void declareVariables() {
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Context
        mContext = FeedActivity.this;
        mActivity = FeedActivity.this;
        // Navigation Drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        // String
        LOG = "MartianDeveloper";
        username = "";
        premium = "";
        // Billing
        mBillingClient = BillingClient
                .newBuilder(this)
                .enablePendingPurchases()
                .setListener(this)
                .build();
        // List
        inAPPList = new ArrayList<>();
        notificationTimeList = new ArrayList<>();
        notificationTimeList.add(10 * 60 * 60 * 1000);
        notificationTimeList.add(12 * 60 * 60 * 1000);
        notificationTimeList.add(18 * 60 * 60 * 1000);
        notificationTimeList.add(24 * 60 * 60 * 1000);

        // Boolean
        doubleBackToExitPressedOnce = false;
    }

    private void getUsername() {
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
                            username = String.valueOf(dataSnapshot.child("name").getValue());
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
                Log.d(LOG, "Exception is null! Cause is in the getUsername, FeedActivity");
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
        adView.setAdUnitId(banner_ad_for_feed);
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

    @SuppressWarnings("SameReturnValue")
    private void handleDrawer() {
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView.setCheckedItem(R.id.movies);
        navigationView.setNavigationItemSelectedListener(item -> {
            Bundle params = new Bundle();
            params.putInt("ButtonID", item.getItemId());
            String buttonName;

            switch (item.getItemId()) {
                case R.id.movies:
                    buttonName = "moviesNAV";
                    appbar.setExpanded(true, true);
                    changeFragment(new MovieFragment());
                    setTitle(movies);
                    break;
                case R.id.tv:
                    buttonName = "tvNAV";
                    appbar.setExpanded(true, true);
                    changeFragment(new TvFragment());
                    setTitle(series_n_tv_shows);
                    break;
                case R.id.actors:
                    buttonName = "actorsNAV";
                    appbar.setExpanded(true, true);
                    changeFragment(new PersonFragment());
                    setTitle(actors);
                    break;
                case R.id.my_profile:
                    buttonName = "my_profileNAV";
                    appbar.setExpanded(true, true);
                    changeFragment(new ProfileFragment());
                    setTitle(username);
                    break;
                case R.id.rate_us:
                    buttonName = "rate_usNAV";
                    rateUs();
                    break;
                case R.id.share:
                    buttonName = "shareNAV";
                    share();
                    break;
                case R.id.about:
                    buttonName = "aboutNAV";
                    goToAbout();
                    break;
                case R.id.no_ads:
                    buttonName = "no_adsNAV";
                    checkForBilling();
                    break;
                case R.id.log_out:
                    buttonName = "log_outNAV";
                    showLogOutDialog();
                    break;
                default:
                    return true;
            }

            mFirebaseAnalytics.logEvent(buttonName, params);

            final Handler handler = new Handler();
            handler.postDelayed(() -> drawerLayout.closeDrawers(), 200);

            return true;
        });

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.no_ads);
        SpannableString s = new SpannableString(menuItem.getTitle() + "    ($3.99)");
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTwo)), 0, s.length(), 0);
        menuItem.setTitle(s);
    }

    private void checkForBilling() {
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
                                checkInternetConnectionForBilling();
                            } else {
                                Toast.makeText(FeedActivity.this, already_premium, Toast.LENGTH_SHORT).show();
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

    private void setFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.fragment_placeholder, new MovieFragment());
        transaction.commit();
    }

    private void go() {
        Intent go_intent = new Intent(FeedActivity.this, LogInActivity.class);
        startActivity(go_intent);
        FeedActivity.this.finish();
    }

    private void resetFragment() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_placeholder, new MovieFragment());
        transaction.commit();

        navigationView.setCheckedItem(R.id.movies);
        setTitle(movies);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.commit();
    }

    private void rateUs() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.muradismayilov.martiandeveloper.moviesaverapp");
        Intent rate_us_intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(rate_us_intent);
    }

    private void share() {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = check_out + "\nhttps://play.google.com/store/apps/details?id=com.muradismayilov.martiandeveloper.moviesaverapp";
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, app_name);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, share_via));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle params = new Bundle();
        params.putInt("ButtonID", item.getItemId());
        String buttonName = "";

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.search) {
            buttonName = "search";
            goToSearch();
        }

        mFirebaseAnalytics.logEvent(buttonName, params);

        return super.onOptionsItemSelected(item);
    }

    private void goToSearch() {
        Intent intent = new Intent(FeedActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void goToAbout() {
        Intent intent = new Intent(FeedActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void checkInternetConnectionForBilling() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            showBillingDialog();

        } else {

            Toast.makeText(this, no_internet_connectionSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void setBilling() {
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    List<String> inAppListString = new ArrayList<>();
                    inAppListString.add("no_ads");

                    SkuDetailsParams.Builder inAppParams = SkuDetailsParams.newBuilder();
                    inAppParams.setSkusList(inAppListString).setType(BillingClient.SkuType.INAPP);

                    mBillingClient.querySkuDetailsAsync(inAppParams.build(), (billingResult1, list) -> inAPPList = list);

                } else {
                    Toast.makeText(FeedActivity.this, went_wrongSTR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(FeedActivity.this, went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBillingDialog() {
        if (inAPPList.size() != 0) {
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(inAPPList.get(0))
                    .build();

            mBillingClient.launchBillingFlow(FeedActivity.this, flowParams);
        }
    }

    private void showLogOutDialog() {
        final AlertDialog dialog_log_out = new AlertDialog.Builder(FeedActivity.this).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.feed_dialog_log_out, null);

        final Button yesBTN = view.findViewById(R.id.yesBTN);
        final Button noBTN = view.findViewById(R.id.noBTN);

        yesBTN.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putInt("ButtonID", v.getId());
            String buttonName = "yesBTN";

            if (mAuth != null) {
                mAuth.signOut();
                go();
            } else {
                Toast.makeText(FeedActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                Log.d(LOG, "mAuth is null in the showLogOutDialog, FeedActivity");
            }

            mFirebaseAnalytics.logEvent(buttonName, params);
        });

        noBTN.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putInt("ButtonID", v.getId());
            String buttonName = "noBTN";

            dialog_log_out.dismiss();

            mFirebaseAnalytics.logEvent(buttonName, params);
        });

        dialog_log_out.setView(view);
        dialog_log_out.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Log.d(LOG, "Yeah there's an internet!");

        } else {
            resetFragment();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {

            for (Purchase purchase : list) {

                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
                }

                saveToDB();
            }
        }

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {

            Toast.makeText(FeedActivity.this, payment_canceled, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDB() {
        if (mUser != null) {
            DatabaseReference mDatabaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Users")
                    .child(mUser.getUid())
                    .child("User Information");

            mDatabaseReference.child("premium").setValue("true").addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(FeedActivity.this, payment_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FeedActivity.this, went_wrongSTR, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(FeedActivity.this, went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press the back key again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }

    @Override
    protected void onStop() {
        cancelAlarm();
        startAlarm();
        super.onStop();
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }

    private void startAlarm() {
        Random random = new Random();
        int i = random.nextInt(notificationTimeList.size());

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            assert alarmManager != null;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + notificationTimeList.get(i), 1000 * 60, pendingIntent);
        }
    }
}
