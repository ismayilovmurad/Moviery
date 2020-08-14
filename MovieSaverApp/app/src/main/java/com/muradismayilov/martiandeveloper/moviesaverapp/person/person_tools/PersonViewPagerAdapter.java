package com.muradismayilov.martiandeveloper.moviesaverapp.person.person_tools;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_fragment.PersonPopularFragment;
import com.muradismayilov.martiandeveloper.moviesaverapp.person.person_fragment.PersonTrendingFragment;

public class PersonViewPagerAdapter extends FragmentPagerAdapter {

    public PersonViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PersonTrendingFragment();
        } else {
            return new PersonPopularFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Trending";
            case 1:
                return "Popular";
            default:
                return null;
        }
    }
}
