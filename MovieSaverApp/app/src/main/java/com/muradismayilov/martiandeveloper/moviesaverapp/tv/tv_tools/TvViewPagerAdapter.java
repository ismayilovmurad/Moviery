package com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_tools;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment.TvPopularFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment.TvTopRatedFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.tv.tv_fragment.TvTrendingFragment;

public class TvViewPagerAdapter extends FragmentPagerAdapter {

    public TvViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TvTrendingFragment();
        } else if (position == 1) {
            return new TvTopRatedFragment();
        } else {
            return new TvPopularFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Trending";
            case 1:
                return "Top Rated";
            case 2:
                return "Popular";
            default:
                return null;
        }
    }
}
