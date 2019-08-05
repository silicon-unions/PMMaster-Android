package com.codesignet.pmp.home.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codesignet.pmp.home.ui.fragments.AllStatisticsFragment;
import com.codesignet.pmp.home.ui.fragments.AnswersStatisticsFragment;

public class StatisticsAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public StatisticsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AllStatisticsFragment.newInstance();
            case 1:
                return AnswersStatisticsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}