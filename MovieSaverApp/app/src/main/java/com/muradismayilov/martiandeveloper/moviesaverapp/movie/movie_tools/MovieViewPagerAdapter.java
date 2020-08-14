package com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_tools;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment.MoviePopularFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment.MovieTopRatedFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment.MovieTrendingFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.movie.movie_fragment.MovieUpComingFragment;

public class MovieViewPagerAdapter extends FragmentPagerAdapter {

    public MovieViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MovieTrendingFragment();
        } else if (position == 1) {
            return new MovieTopRatedFragment();
        } else if (position == 2) {
            return new MoviePopularFragment();
        } else {
            return new MovieUpComingFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
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
            case 3:
                return "UpComing";
            default:
                return null;
        }
    }
}
