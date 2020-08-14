package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.filter.activity.FilterActivityMovie;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools.MovieViewPagerAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions")
public class MovieFragment extends Fragment {

    // UI Components
    // ViewPager
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    // TabLayout
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    // Layout
    @BindView(R.id.movie_no_internetCL)
    ConstraintLayout movie_no_internetCL;
    // Button
    @BindView(R.id.movie_refreshBTN)
    Button movie_refreshBTN;
    // FloatingActionButton
    @BindView(R.id.filterFAB)
    FloatingActionButton filterFAB;

    // Strings
    @BindString(R.string.trending)
    String trendingSTR;
    @BindString(R.string.top_rated)
    String top_ratedSTR;
    @BindString(R.string.popular)
    String popularSTR;
    @BindString(R.string.up_coming)
    String up_comingSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;
    @BindString(R.string.bad_format)
    String bad_format;

    // Variables
    // String
    private String genres, year;
    private String type = "Popularity";
    // Boolean
    private boolean isBollywood;
    // Firebase
    private FirebaseAnalytics mFirebaseAnalytics;

    public MovieFragment() {
        // Required Default Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ButterKnife.bind(this, view);
        initialFunctions();
    }

    private void initialFunctions() {
        declareVariables();
        onClickListeners();
        checkInternetConnectionForFirstTime();
    }

    private void declareVariables() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(FeedActivity.mContext);
    }

    private void onClickListeners() {
        movie_refreshBTN.setOnClickListener(v -> checkInternetConnectionForFirstTime());

        filterFAB.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putInt("ButtonID", v.getId());
            String buttonName = "filterFAB";

            openFilterDialog();

            mFirebaseAnalytics.logEvent(buttonName, params);
        });
    }

    @SuppressLint("RestrictedApi")
    private void checkInternetConnectionForFirstTime() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                setViewPager();
                movie_no_internetCL.setVisibility(View.INVISIBLE);
                filterFAB.setVisibility(View.VISIBLE);

            } else {
                filterFAB.setVisibility(View.INVISIBLE);
                movie_no_internetCL.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("InflateParams")
    private void setViewPager() {
        MovieViewPagerAdapter adapter = new MovieViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        TextView tabOne = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.movie_layout_custom_tab, null);
        TextView tabTwo = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.movie_layout_custom_tab, null);
        TextView tabThree = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.movie_layout_custom_tab, null);
        TextView tabFour = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.movie_layout_custom_tab, null);
        tabOne.setText(trendingSTR);
        tabTwo.setText(top_ratedSTR);
        tabThree.setText(popularSTR);
        tabFour.setText(up_comingSTR);
        try {
            tabLayout.getTabAt(0).setCustomView(tabOne);
            tabLayout.getTabAt(1).setCustomView(tabTwo);
            tabLayout.getTabAt(2).setCustomView(tabThree);
            tabLayout.getTabAt(3).setCustomView(tabFour);
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Toast.makeText(FeedActivity.mContext, errorSTR + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFilterDialog() {
        final AlertDialog filterDialog = new AlertDialog.Builder(FeedActivity.mContext).create();
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.filter_dialog_movie, null);

        handleFilterActions(view, filterDialog);

        filterDialog.setView(view);
        filterDialog.show();
    }

    private void handleFilterActions(View view, final AlertDialog filterDialog) {
        final CheckBox filterActionCB = view.findViewById(R.id.filterActionCB);
        final CheckBox filterAnimationCB = view.findViewById(R.id.filterAnimationCB);
        final CheckBox filterCrimeCB = view.findViewById(R.id.filterCrimeCB);
        final CheckBox filterDramaCB = view.findViewById(R.id.filterDramaCB);
        final CheckBox filterFantasyCB = view.findViewById(R.id.filterFantasyCB);
        final CheckBox filterHorrorCB = view.findViewById(R.id.filterHorrorCB);
        final CheckBox filterMysteryCB = view.findViewById(R.id.filterMysteryCB);
        final CheckBox filterScienceFictionCB = view.findViewById(R.id.filterScienceFictionCB);
        final CheckBox filterWarCB = view.findViewById(R.id.filterWarCB);
        final CheckBox filterAdventureCB = view.findViewById(R.id.filterAdventureCB);
        final CheckBox filterComedyCB = view.findViewById(R.id.filterComedyCB);
        final CheckBox filterDocumentaryCB = view.findViewById(R.id.filterDocumentaryCB);
        final CheckBox filterFamilyCB = view.findViewById(R.id.filterFamilyCB);
        final CheckBox filterHistoryCB = view.findViewById(R.id.filterHistoryCB);
        final CheckBox filterMusicCB = view.findViewById(R.id.filterMusicCB);
        final CheckBox filterRomanceCB = view.findViewById(R.id.filterRomanceCB);
        final CheckBox filterThrillerCB = view.findViewById(R.id.filterThrillerCB);
        final CheckBox filterWesternCB = view.findViewById(R.id.filterWesternCB);
        final TextInputEditText filterYearET = view.findViewById(R.id.filterYearET);
        final RadioGroup filterRG = view.findViewById(R.id.filterRG);
        final Button filterCancelBTN = view.findViewById(R.id.filterCancelBTN);
        Button filterFilterBTN = view.findViewById(R.id.filterFilterBTN);
        final CheckBox filterBollywoodCB = view.findViewById(R.id.filterBollywoodCB);

        filterFilterBTN.setOnClickListener(v -> {
            genres = "";
            year = filterYearET.getText().toString().trim();

            // Bollywood
            isBollywood = filterBollywoodCB.isChecked();

            // Genres
            if (filterActionCB.isChecked()) {
                genres += " 28";
            }
            if (filterAnimationCB.isChecked()) {
                genres += " 16";
            }
            if (filterCrimeCB.isChecked()) {
                genres += " 80";
            }
            if (filterDramaCB.isChecked()) {
                genres += " 18";
            }
            if (filterFantasyCB.isChecked()) {
                genres += " 14";
            }
            if (filterHorrorCB.isChecked()) {
                genres += " 27";
            }
            if (filterMysteryCB.isChecked()) {
                genres += " 9648";
            }
            if (filterScienceFictionCB.isChecked()) {
                genres += " 878";
            }
            if (filterWarCB.isChecked()) {
                genres += " 10752";
            }
            if (filterAdventureCB.isChecked()) {
                genres += " 12";
            }
            if (filterComedyCB.isChecked()) {
                genres += " 35";
            }
            if (filterDocumentaryCB.isChecked()) {
                genres += " 99";
            }
            if (filterFamilyCB.isChecked()) {
                genres += " 10751";
            }
            if (filterHistoryCB.isChecked()) {
                genres += " 36";
            }
            if (filterMusicCB.isChecked()) {
                genres += " 10402";
            }
            if (filterRomanceCB.isChecked()) {
                genres += " 10749";
            }
            if (filterThrillerCB.isChecked()) {
                genres += " 53";
            }
            if (filterWesternCB.isChecked()) {
                genres += " 37";
            }

            // Genres
            if (TextUtils.isEmpty(genres) || genres == null) {
                genres = "none";
            }

            // Year
            if (TextUtils.isEmpty(year) || year == null) {
                year = "none";
            }

            // Type
            filterRG.setOnCheckedChangeListener((group, checkedId) -> {

                switch (checkedId) {
                    case R.id.filterPopularityRB:
                        type = "Popularity";
                        break;
                    case R.id.filterHighestRatedRB:
                        type = "HighestRated";
                        break;
                }
            });

            // Check for the Year
            if (year.length() != 4) {
                Toast.makeText(FeedActivity.mContext, bad_format, Toast.LENGTH_SHORT).show();
            } else {
                filterDialog.dismiss();
                goToFilterActivity();
            }

        });

        filterCancelBTN.setOnClickListener(v -> filterDialog.dismiss());
    }

    private void goToFilterActivity() {
        Intent filterIntent = new Intent(FeedActivity.mContext, FilterActivityMovie.class);
        filterIntent.putExtra("genres", genres);
        filterIntent.putExtra("year", year);
        filterIntent.putExtra("type", type);
        if (isBollywood) {
            filterIntent.putExtra("bollywood", "true");
        } else {
            filterIntent.putExtra("bollywood", "false");
        }
        startActivity(filterIntent);
    }
}
