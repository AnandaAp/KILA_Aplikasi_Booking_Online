package com.ndaktau.kila;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ndaktau.kila._sliders.FragmentSlider;
import com.ndaktau.kila._sliders.OnSwipeTouchListener;
import com.ndaktau.kila._sliders.SliderIndicator;
import com.ndaktau.kila._sliders.SliderPagerAdapter;
import com.ndaktau.kila._sliders.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;
    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private ImageButton badminton,basket,futsal,sepakbola;
    private static String kategori;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = (SliderView) view.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.pagesContainer);
        setupSlider();

        badminton= view.findViewById(R.id.badminton);
        futsal= view.findViewById(R.id.futsal);
        sepakbola= view.findViewById(R.id.sepakbola);
        basket= view.findViewById(R.id.basket);


        badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , DetailLapangan.class);
                HomeFragment.setKategori("Badminton");
                startActivity(intent);

            }
        });

        futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , DetailLapangan.class);
                HomeFragment.setKategori("Futsal");
                startActivity(intent);
            }
        });

        sepakbola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , DetailLapangan.class);
                HomeFragment.setKategori("Sepakbola");
                startActivity(intent);
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity() , DetailLapangan.class);
                HomeFragment.setKategori("Basket");
                startActivity(intent);
            }
        });
        return view;
    }
    //private void moveToDetaillapangan(View view){
     // }
    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSlider.newInstance("https://s3-ap-southeast-1.amazonaws.com/fibostorage/news/news22351jpg.jpg"));
        fragments.add(FragmentSlider.newInstance("https://4.bp.blogspot.com/-YpJTuobb_Qw/WeTNZfWUBiI/AAAAAAAAAH8/sqzqh1iSS74-zSybCDF-n3XdQbyNCFR0gCLcBGAs/s1600/Biayalapanganfutsaloutdoor.jpg"));
        fragments.add(FragmentSlider.newInstance("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTH8P9sxQBbJWKxrbpG8TNik-I9luYoWmVf3A&usqp=CAU"));
        fragments.add(FragmentSlider.newInstance("http://www.menucool.com/slider/prod/image-slider-4.jpg"));

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();

        sliderView.setOnTouchListener(new OnSwipeTouchListener(requireActivity()) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {

            }

        });
    }

    public static String getKategori() {
        return kategori;
    }

    public static void setKategori(String kategori) {
        HomeFragment.kategori = kategori;
    }
}
