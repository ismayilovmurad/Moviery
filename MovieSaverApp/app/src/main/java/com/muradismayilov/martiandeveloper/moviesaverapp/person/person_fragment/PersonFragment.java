package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.FeedActivity;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_tools.PersonViewPagerAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions")
public class PersonFragment extends Fragment {

    // UI Components
    // ViewPager
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    // TabLayout
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    // Layout
    @BindView(R.id.person_no_internetCL)
    ConstraintLayout person_no_internetCL;
    // Button
    @BindView(R.id.person_refreshBTN)
    Button person_refreshBTN;

    // Strings
    @BindString(R.string.trending)
    String trendingSTR;
    @BindString(R.string.popular)
    String popularSTR;
    @BindString(R.string.error)
    String errorSTR;
    @BindString(R.string.went_wrong)
    String went_wrongSTR;

    public PersonFragment() {
        // Required Default Constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ButterKnife.bind(this, view);
        onClickListeners();
        checkInternetConnectionForFirstTime();
    }

    private void onClickListeners() {
        person_refreshBTN.setOnClickListener(v -> checkInternetConnectionForFirstTime());
    }

    @SuppressLint("RestrictedApi")
    private void checkInternetConnectionForFirstTime() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                setViewPager();
                person_no_internetCL.setVisibility(View.INVISIBLE);

            } else {
                person_no_internetCL.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("InflateParams")
    private void setViewPager() {
        PersonViewPagerAdapter adapter = new PersonViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        TextView tabOne = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.person_layout_custom_tab, null);
        TextView tabTwo = (TextView) LayoutInflater.from(FeedActivity.mContext).inflate(R.layout.person_layout_custom_tab, null);
        tabOne.setText(trendingSTR);
        tabTwo.setText(popularSTR);
        try {
            tabLayout.getTabAt(0).setCustomView(tabOne);
            tabLayout.getTabAt(1).setCustomView(tabTwo);
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                Toast.makeText(FeedActivity.mContext, errorSTR + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FeedActivity.mContext, errorSTR + went_wrongSTR, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

