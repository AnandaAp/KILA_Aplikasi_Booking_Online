package com.ndaktau.kila;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ndaktau.kila.HomeFragment;
import com.ndaktau.kila.fragment_profile;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;


    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new fragment_profile();


            default:
                return null;

        }

    }
    public int getCount(){
        return numOfTabs;
    }
}