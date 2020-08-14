package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.tools.Client;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_model.TvMain;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_recycler.tv_main_recycler.TvMainAdapter;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_response.TvMainResponse;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_service.TvMainService;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools.TvGridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class TvTrendingFragment extends Fragment {

    // UI Components
    // RecyclerView
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    // ProgressBar
    @BindView(R.id.mainPB)
    ProgressBar mainPB;
    @BindView(R.id.morePB)
    ProgressBar morePB;
    // Layout
    @BindView(R.id.emptyLL)
    LinearLayout emptyLL;
    // SwipeRefreshLayout
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    // ImageButton
    @BindView(R.id.backToTopIBTN)
    ImageButton backToTopIBTN;

    // Strings
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.no_internet_connection)
    String no_internet_connection;
    @BindString(R.string.interstitial_ad_for_more)
    String interstitial_ad_for_more;
    @BindString(R.string.reached)
    String reached;

    // Variables
    // List
    private List<TvMain> tvMainList;
    // GridLayoutManager
    private GridLayoutManager mLayoutManager;
    // Adapter
    private RecyclerView.Adapter mAdapter;
    // Service
    private TvMainService apiService;
    // Integer
    private int page;
    // String
    private String LOG, premium;
    // InterstitialAd
    private InterstitialAd interstitialAd;
    // AdRequest
    private AdRequest interstitialAdRequest;
    // Firebase
    private FirebaseUser mUser;

    public TvTrendingFragment() {
        // Required Default Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_fragment_view_pager, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ButterKnife.bind(this, view);
        initialMethods();
    }

    private void initialMethods() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTwo));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorPrimaryDark));

        mainPB.setVisibility(View.INVISIBLE);
        morePB.setVisibility(View.INVISIBLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mRecyclerView.addItemDecoration(new TvGridSpacingItemDecoration(2, 32, false));
        }

        declareVariables();
        setListeners();
        checkInternetConnectionForFirstTime();
        checkForAds();
    }

    private void declareVariables() {
        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // Client & Service
        Client client = new Client();
        apiService = client.getClient().create(TvMainService.class);

        // Integer
        page = 1;

        // List
        tvMainList = new ArrayList<>();

        // LayoutManager
        mLayoutManager = new GridLayoutManager(FeedActivity.mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Adapter
        mAdapter = new TvMainAdapter(FeedActivity.mContext, tvMainList);
        mRecyclerView.setAdapter(mAdapter);

        // String
        LOG = "MartianDeveloper";
        premium = "";
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(() -> {

            checkInternetConnectionForSwipeRefresh();

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    checkInternetConnectionForMore();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mLayoutManager.findFirstVisibleItemPosition() > 4) {
                    backToTopIBTN.setVisibility(View.VISIBLE);
                } else {
                    backToTopIBTN.setVisibility(View.GONE);
                }
            }
        });

        backToTopIBTN.setOnClickListener(v -> {
            mRecyclerView.smoothScrollToPosition(0);
            backToTopIBTN.setVisibility(View.GONE);
        });
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
        interstitialAd = new InterstitialAd(FeedActivity.mContext);
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

    private void checkInternetConnectionForFirstTime() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                mRecyclerView.setVisibility(View.VISIBLE);

                loadJSONForFirstTime();

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connection, Toast.LENGTH_SHORT).show();
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForFirstTime() {
        showProgress();

        Call<TvMainResponse> call = apiService.getTrendingTvDay(getResources().getString(R.string.api_key), page);
        call.enqueue(new Callback<TvMainResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvMainResponse> call, @NonNull Response<TvMainResponse> response) {

                if (response.body() != null) {
                    if (response.body().getResults() != null) {
                        try {

                            tvMainList = response.body().getResults();

                            if (tvMainList.size() == 0) {
                                emptyLL.setVisibility(View.VISIBLE);
                            } else {
                                emptyLL.setVisibility(View.INVISIBLE);
                                mAdapter = new TvMainAdapter(FeedActivity.mContext, tvMainList);
                                mRecyclerView.swapAdapter(mAdapter, true);
                            }

                        } catch (Exception e) {
                            hideProgress();
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<TvMainResponse> call, @NonNull Throwable t) {
                hideProgress();
                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
                Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkInternetConnectionForSwipeRefresh() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                mRecyclerView.setVisibility(View.VISIBLE);
                loadJSONForSwipeRefresh();

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForSwipeRefresh() {

        page = 1;

        Call<TvMainResponse> call = apiService.getTrendingTvDay(getResources().getString(R.string.api_key), page);
        call.enqueue(new Callback<TvMainResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvMainResponse> call, @NonNull Response<TvMainResponse> response) {

                if (response.body() != null) {
                    if (response.body().getResults() != null) {
                        try {

                            tvMainList = response.body().getResults();

                            if (tvMainList.size() == 0) {
                                emptyLL.setVisibility(View.VISIBLE);
                            } else {
                                emptyLL.setVisibility(View.INVISIBLE);
                                mAdapter = new TvMainAdapter(FeedActivity.mContext, tvMainList);
                                mRecyclerView.swapAdapter(mAdapter, true);
                            }

                        } catch (Exception e) {
                            hideProgress();
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvMainResponse> call, @NonNull Throwable t) {
                hideProgress();
                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
                Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkInternetConnectionForMore() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                loadJSONForMore();

            } else {
                Toast.makeText(FeedActivity.mContext, no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONForMore() {
        morePB.setVisibility(View.VISIBLE);

        page++;

        Call<TvMainResponse> call = apiService.getTrendingTvDay(getResources().getString(R.string.api_key), page);
        call.enqueue(new Callback<TvMainResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvMainResponse> call, @NonNull Response<TvMainResponse> response) {

                if (response.body() != null) {
                    if (response.body().getResults() != null) {
                        try {

                            if (response.body().getResults().size() != 0) {
                                tvMainList.addAll(response.body().getResults());

                                mAdapter.notifyItemRangeInserted(tvMainList.size(), tvMainList.size());

                                float adCounter = page % 4;

                                if (adCounter == 0.0) {
                                    if (interstitialAd != null) {
                                        if (interstitialAd.isLoaded()) {
                                            interstitialAd.show();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(FeedActivity.mContext, reached, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            morePB.setVisibility(View.INVISIBLE);
                            if (e.getLocalizedMessage() != null) {
                                Log.d(LOG, errorSTR + e.getLocalizedMessage());
                            }
                            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                morePB.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<TvMainResponse> call, @NonNull Throwable t) {
                morePB.setVisibility(View.INVISIBLE);
                if (t.getLocalizedMessage() != null) {
                    Log.d(LOG, errorSTR + t.getLocalizedMessage());
                }
                Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        mRecyclerView.setAlpha(0.5f);
        mainPB.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mRecyclerView.setAlpha(1.0f);
        mainPB.setVisibility(View.INVISIBLE);
    }
}
