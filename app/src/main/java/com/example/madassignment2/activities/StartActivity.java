package com.example.madassignment2.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.madassignment2.fragment.FragmentMap;
import com.example.madassignment2.fragment.FragmentSelect;
import com.example.madassignment2.R;
import com.example.madassignment2.fragment.FragmentStatus;

public class StartActivity  extends AppCompatActivity {

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);

        FragmentManager fm = getSupportFragmentManager();

        FragmentStatus fragmentStatus = (FragmentStatus) fm.findFragmentById(R.id.status_screen);
        if (fragmentStatus == null) {
            fragmentStatus = new FragmentStatus();
            fm.beginTransaction().add(R.id.status_screen, fragmentStatus).commit();
        }


        FragmentSelect fragmentSelect = (FragmentSelect) fm.findFragmentById(R.id.select_screen);
        if (fragmentSelect == null) {
            fragmentSelect = new FragmentSelect();
            fm.beginTransaction().add(R.id.select_screen, fragmentSelect).commit();
        }

        FragmentMap fragmentMap = (FragmentMap) fm.findFragmentById(R.id.map_screen);
        if (fragmentMap == null) {
            fragmentMap = new FragmentMap();
            fragmentMap.setSelectFragment(fragmentSelect);
            fm.beginTransaction().add(R.id.map_screen, fragmentMap).commit();
        }

        //setters to initialise the fragments to allow implementation inbtween fragments.
        fragmentMap.setSelectFragment(fragmentSelect);
        fragmentMap.setStatusFragment(fragmentStatus);
        fragmentStatus.setFragmentMap(fragmentMap);
    }
}
