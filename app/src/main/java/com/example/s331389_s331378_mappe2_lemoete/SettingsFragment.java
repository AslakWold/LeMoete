package com.example.s331389_s331378_mappe2_lemoete;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    TextView tidInn;
    ImageButton imageButtonTid;
    String tidspunkt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_innstillinger, container, false);
        tidInn = (TextView) v.findViewById(R.id.txtTid);
        imageButtonTid = (ImageButton)v.findViewById(R.id.tid_icon);
        imageButtonTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VelgTid();
            }
        });
        return v;
    }



    //Metoder for settingsfragment


    public void VelgTid() {
        TimePickerFragment2 timePicker = new TimePickerFragment2(this);
        timePicker.show(getFragmentManager(), "time picker");
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutt) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hours);
        c.set(Calendar.MINUTE,minutt);
        String currentTimeString =c.get(Calendar.HOUR_OF_DAY) + ":" +c.get(Calendar.MINUTE);

        tidspunkt = currentTimeString;
        tidInn.setText(currentTimeString);
        savetidspunkt();
        startService();
    }
    public void savetidspunkt(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
                preferences.edit()
                .putString("tidspunkt",tidspunkt)
                .apply();
    }
    //Metode som starter melding/notifikasjon service
    public void startService(){
        Intent i = new Intent();
        i.setAction("mittbroadacast");
        getActivity().sendBroadcast(i);
    }
}

/*
/*public class SettingsFragment extends PreferenceFragment {
    FragmentManager fragment;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    /* public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_innstillinger, container, false);

        getFragmentManager().beginTransaction().replace(R.id.fragment_preferences, new SettingsFragment()).commit();
        return v;
    } */

/*
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
}*/
