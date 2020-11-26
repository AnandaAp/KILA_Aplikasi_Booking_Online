package com.ndaktau.kila;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {

    private final int numOfTabs;


    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
            default:
                return null;

        }

    }
    public int getCount(){
        return numOfTabs;
    }
}