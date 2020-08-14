package com.muradismayilov.martiandeveloper.moviesaverapp.search.search_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
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
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools.MovieGridSpacingItemDecoration;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchMovie;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchPerson;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_model.SearchTv;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_recycler.SearchMovieAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_recycler.SearchPersonAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_recycler.SearchTvAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response.SearchMovieResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response.SearchPersonResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_response.SearchTvResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_service.SearchMovieService;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_service.SearchPersonService;
import com.muradismayilov.martiandeveloper.moviesaverapp.search.search_service.SearchTvService;

import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    // UI Components
    // RecyclerView
    @BindView(R.id.searchRV)
    RecyclerView searchRV;
    // FloatingSearchView
    @BindView(R.id.floatingSearchView)
    FloatingSearchView floatingSearchView;
    // Layout
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    // ProgressBar
    @BindView(R.id.searchMainPB)
    ProgressBar searchMainPB;
    @BindView(R.id.searchMorePB)
    ProgressBar searchMorePB;
    // RadioButton
    @BindView(R.id.searchMovieRB)
    RadioButton searchMovieRB;
    @BindView(R.id.searchTvRB)
    RadioButton searchTvRB;
    @BindView(R.id.searchActorRB)
    RadioButton searchActorRB;
    // RadioGroup
    @BindView(R.id.searchRG)
    RadioGroup searchRG;
    // SwipeRefreshLayout
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    // FrameLayout
    @BindView(R.id.adViewPlaceholderFL)
    FrameLayout adViewPlaceholderFL;
    // Activity
    @SuppressLint("StaticFieldLeak")
    public static Activity mActivity;

    // Strings
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;
    @BindString(R.string.banner_ad_for_search)
    String banner_ad_for_search;
    @BindString(R.string.interstitial_ad_for_more)
    String interstitial_ad_for_more;

    // Variables
    // List
    private List<SearchMovie> searchMovieList;
    private List<SearchTv> searchTvList;
    private List<SearchPerson> searchPersonList;
    private Set<String> historyList;
    // Adapter
    private RecyclerView.Adapter searchAdapter;
    // Service
    private SearchMovieService searchMovieService;
    private SearchTvService searchTvService;
    private SearchPersonService searchPersonService;
    // Integer
    private int searchMoviePage, searchTvPage, searchPersonPage;
    // String
    private String searchRequest, LOG, searchType, premium;
    // Final
    private static final String PREFS_NAME = "PingBusPrefs2";
    private static final String PREFS_SEARCH_HISTORY = "SearchHistory2";
    // Firebase
    private FirebaseUser mUser;
    // InterstitialAd
    private InterstitialAd interstitialAd;
    // AdRequest
    private AdRequest interstitialAdRequest;

    //This just for illustration how to populate suggestions
    private static class SimpleSuggestions implements SearchSuggestion {
        private final String mData;

        SimpleSuggestions(String string) {
            mData = string;
        }

        @Override
        public String getBody() {
            return mData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mData);
        }

        public static final Parcelable.Creator<SimpleSuggestions> CREATOR
                = new Parcelable.Creator<SimpleSuggestions>() {
            public SimpleSuggestions createFromParcel(Parcel in) {
                return new SimpleSuggestions(in);
            }

            public SimpleSuggestions[] newArray(int size) {
                return new SimpleSuggestions[size];
            }
        };

        private SimpleSuggestions(Parcel in) {
            mData = in.readString();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        initialFunctions();
    }

    private void initialFunctions() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTwo));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));

        searchMainPB.setVisibility(View.INVISIBLE);
        searchMorePB.setVisibility(View.INVISIBLE);

        floatingSearchView.setSearchFocused(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            searchRV.addItemDecoration(new MovieGridSpacingItemDecoration(2, 32, false));
        }

        declareVariables();
        setListeners();
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
        // Shared
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        historyList = new HashSet<>(settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<>()));

        // Client & Service
        Client searchMovieClient = new Client();
        searchMovieService = searchMovieClient.getClient().create(SearchMovieService.class);
        Client searchTvClient = new Client();
        searchTvService = searchTvClient.getClient().create(SearchTvService.class);
        Client searchPersonClient = new Client();
        searchPersonService = searchPersonClient.getClient().create(SearchPersonService.class);

        // Integer
        searchMoviePage = 1;
        searchTvPage = 1;
        searchPersonPage = 1;

        // String
        searchRequest = "";
        searchType = "movie";
        LOG = "MartianDeveloper";
        premium = "";

        // List
        searchMovieList = new ArrayList<>();
        searchTvList = new ArrayList<>();
        searchPersonList = new ArrayList<>();

        // LayoutManager
        RecyclerView.LayoutManager searchLayoutManager = new GridLayoutManager(this, 2);
        searchRV.setLayoutManager(searchLayoutManager);

        // Adapter
        searchAdapter = new SearchMovieAdapter(this, searchMovieList);
        searchRV.setAdapter(searchAdapter);

        // Activity
        mActivity = SearchActivity.this;
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
        adView.setAdUnitId(banner_ad_for_search);
        adViewPlaceholderFL.addView(adView);

        AdRequest bannerAdRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(bannerAdRequest);

        interstitialAd = new InterstitialAd(SearchActivity.this);
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

    private void setListeners() {
        floatingSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            List<SearchSuggestion> list = new ArrayList<>();
            for (String item : historyList) {
                if (item.contains(newQuery)) {
                    list.add(new SimpleSuggestions(item));
                }
            }
            floatingSearchView.swapSuggestions(list);
        });

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                floatingSearchView.setSearchFocused(false);
                if (!TextUtils.isEmpty(searchSuggestion.getBody())) {
                    searchMovieRB.setChecked(true);
                    searchRequest = searchSuggestion.getBody();
                    checkInternetConnectionForMovieSearchResult();
                    addSearchInput(searchRequest);
                    searchType = "movie";
                    floatingSearchView.setSearchText(searchSuggestion.getBody());
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (!TextUtils.isEmpty(currentQuery)) {
                    searchMovieRB.setChecked(true);
                    searchRequest = currentQuery;
                    checkInternetConnectionForMovieSearchResult();
                    addSearchInput(searchRequest);
                    searchType = "movie";
                }
            }
        });

        searchRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    checkInternetConnectionForMoreResult();

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {

            switch (searchType) {
                case "movie":
                    searchMovieRB.setChecked(true);
                    checkInternetConnectionForMovieSearchResult();
                    break;
                case "tv":
                    searchTvRB.setChecked(true);
                    checkInternetConnectionForTvSearchResult();
                    break;
                case "person":
                    searchActorRB.setChecked(true);
                    checkInternetConnectionForPersonSearchResult();
                    break;
            }

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });

        searchRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.searchMovieRB:
                    searchType = "movie";
                    checkInternetConnectionForMovieSearchResult();
                    break;
                case R.id.searchTvRB:
                    searchType = "tv";
                    checkInternetConnectionForTvSearchResult();
                    break;
                case R.id.searchActorRB:
                    searchType = "person";
                    checkInternetConnectionForPersonSearchResult();
                    break;
            }
        });

        floatingSearchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> leftIcon.setImageResource(R.drawable.ic_history));

        floatingSearchView.setOnHomeActionClickListener(this::onBackPressed);
    }

    private void savePrefs() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, historyList);

        editor.apply();
    }

    private void addSearchInput(String input) {
        historyList.add(input);
    }

    // Movie
    private void checkInternetConnectionForMovieSearchResult() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMovieSearchResult();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForMovieSearchResult() {
        showProgress();
        searchMoviePage = 1;

        Call<SearchMovieResponse> call = searchMovieService.getMovieWithRequest(getResources().getString(R.string.api_key), searchRequest, searchMoviePage);

        call.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchMovieResponse> call, @NonNull Response<SearchMovieResponse> response) {
                try {

                    if (response.body() != null) {
                        searchMovieList = response.body().getResults();
                    }

                    if (searchMovieList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    searchAdapter = new SearchMovieAdapter(SearchActivity.this, searchMovieList);
                    searchRV.setAdapter(searchAdapter);

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<SearchMovieResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Tv
    private void checkInternetConnectionForTvSearchResult() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForTvSearchResult();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForTvSearchResult() {
        showProgress();
        searchTvPage = 1;

        Call<SearchTvResponse> call = searchTvService.getTvWithRequest(getResources().getString(R.string.api_key), searchRequest, searchTvPage);

        call.enqueue(new Callback<SearchTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchTvResponse> call, @NonNull Response<SearchTvResponse> response) {
                try {

                    if (response.body() != null) {
                        searchTvList = response.body().getResults();
                    }

                    if (searchTvList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    searchAdapter = new SearchTvAdapter(SearchActivity.this, searchTvList);
                    searchRV.setAdapter(searchAdapter);

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<SearchTvResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Person
    private void checkInternetConnectionForPersonSearchResult() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForPersonSearchResult();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForPersonSearchResult() {
        showProgress();
        searchPersonPage = 1;

        Call<SearchPersonResponse> call = searchPersonService.getPersonWithRequest(getResources().getString(R.string.api_key), searchRequest, searchPersonPage);

        call.enqueue(new Callback<SearchPersonResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchPersonResponse> call, @NonNull Response<SearchPersonResponse> response) {
                try {

                    if (response.body() != null) {
                        searchPersonList = response.body().getResults();
                    }

                    if (searchPersonList.size() == 0) {
                        emptyLL.setVisibility(View.VISIBLE);
                    } else {
                        emptyLL.setVisibility(View.GONE);
                    }

                    searchAdapter = new SearchPersonAdapter(SearchActivity.this, searchPersonList);
                    searchRV.setAdapter(searchAdapter);

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<SearchPersonResponse> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // More
    private void checkInternetConnectionForMoreResult() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            loadJSONForMoreResult();

        } else {
            Toast.makeText(this, no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForMoreResult() {
        searchMorePB.setVisibility(View.VISIBLE);

        switch (searchType) {
            case "movie":
                moreMovie();
                break;
            case "tv":
                moreTv();
                break;
            case "person":
                morePerson();
                break;
        }
    }

    private void moreMovie() {
        searchMoviePage++;

        Call<SearchMovieResponse> call = searchMovieService.getMovieWithRequest(getResources().getString(R.string.api_key), searchRequest, searchMoviePage);
        call.enqueue(new Callback<SearchMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchMovieResponse> call, @NonNull Response<SearchMovieResponse> response) {

                try {
                    if (response.body() != null) {
                        searchMovieList.addAll(response.body().getResults());

                        searchAdapter.notifyItemRangeInserted(searchMovieList.size(), searchMovieList.size());

                        float adCounter = searchMoviePage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                searchMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<SearchMovieResponse> call, @NonNull Throwable t) {
                searchMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moreTv() {
        searchTvPage++;

        Call<SearchTvResponse> call = searchTvService.getTvWithRequest(getResources().getString(R.string.api_key), searchRequest, searchTvPage);
        call.enqueue(new Callback<SearchTvResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchTvResponse> call, @NonNull Response<SearchTvResponse> response) {

                try {
                    if (response.body() != null) {
                        searchTvList.addAll(response.body().getResults());

                        searchAdapter.notifyItemRangeInserted(searchTvList.size(), searchTvList.size());

                        float adCounter = searchTvPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                searchMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<SearchTvResponse> call, @NonNull Throwable t) {
                searchMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void morePerson() {
        searchPersonPage++;

        Call<SearchPersonResponse> call = searchPersonService.getPersonWithRequest(getResources().getString(R.string.api_key), searchRequest, searchPersonPage);
        call.enqueue(new Callback<SearchPersonResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchPersonResponse> call, @NonNull Response<SearchPersonResponse> response) {

                try {
                    if (response.body() != null) {
                        searchPersonList.addAll(response.body().getResults());

                        searchAdapter.notifyItemRangeInserted(searchPersonList.size(), searchPersonList.size());

                        float adCounter = searchPersonPage % 4;

                        if (adCounter == 0.0) {
                            if (interstitialAd != null) {
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                }

                searchMorePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<SearchPersonResponse> call, @NonNull Throwable t) {
                searchMorePB.setVisibility(View.INVISIBLE);
                Toast.makeText(SearchActivity.this, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        searchRV.setAlpha(0.5f);
        searchMainPB.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        searchRV.setAlpha(1.0f);
        searchMainPB.setVisibility(View.INVISIBLE);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void onStop() {
        super.onStop();
        savePrefs();

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
