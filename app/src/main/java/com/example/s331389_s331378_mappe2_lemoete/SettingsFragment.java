package com.example.s331389_s331378_mappe2_lemoete;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.zip.Inflater;

public class SettingsFragment extends PreferenceFragment {
    FragmentManager fragment;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    /* public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_innstillinger, container, false);

        getFragmentManager().beginTransaction().replace(R.id.fragment_preferences, new SettingsFragment()).commit();
        return v;
    } */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //Lytter etter endringer av valg i Preferencelistene
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                getActivity().recreate();
            }
        };

    }

    //registrer og uregistrerer endringene
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        Log.d("TAG", "Er i onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        Log.d("TAG", "Er i onPause");
    }
}
