package com.example.ngay_29_7_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.ngay_29_7_2021.fragments.DangKyFragment;
import com.example.ngay_29_7_2021.fragments.DangNhapFragment;
import com.example.ngay_29_7_2021.fragments.HomeFragment;

public class MainActitivy extends AppCompatActivity {
    FragmentManager fragmentManager;
    public DangNhapFragment dangNhapFragment;
    public DangKyFragment dangKyFragment;
    public HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        homeFragment = new HomeFragment();
        dangNhapFragment = new DangNhapFragment();
        dangKyFragment = new DangKyFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.fragment_container,dangNhapFragment)
                .commit();
    }
}