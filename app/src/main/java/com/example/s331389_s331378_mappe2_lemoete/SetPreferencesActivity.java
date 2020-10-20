package com.example.s331389_s331378_mappe2_lemoete;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class SetPreferencesActivity extends AppCompatActivity  {

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        super.onCreate(savedInstanceState);
        PrefsFragment prefsFragment = new PrefsFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, prefsFragment).commit();

    }//onCreate ends

    public void btnTimeDialog(View view) {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    /*public void onTimeSet(TimePicker timePicker, int hours, int minutt) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hours);
        c.set(Calendar.MINUTE,minutt);
        String currentTimeString =c.get(Calendar.HOUR_OF_DAY) + ":" +c.get(Calendar.MINUTE);


        .setText(currentTimeString);
    } */


    public static class PrefsFragment extends PreferenceFragment  implements TimePickerDialog.OnTimeSetListener {
        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
        EditTextPreference editTextPreference;
        Fragment fm;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            editTextPreference = (EditTextPreference) findPreference("tid_velg");
            fm = new Fragment();

            //Lytter etter endringer av valg i Preferencelistene
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    getActivity().recreate();
                }
            };

        }

        /*public void btnTimeDialog(View view) {
            TimePickerFragment timePicker = new TimePickerFragment();
            timePicker.show(getFragmentManager(), "time picker");
        } */

        public void onTimeSet(TimePicker timePicker, int hours, int minutt) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,hours);
            c.set(Calendar.MINUTE,minutt);
            String currentTimeString =c.get(Calendar.HOUR_OF_DAY) + ":" +c.get(Calendar.MINUTE);

            editTextPreference.setText(currentTimeString);
            //.setText(currentTimeString);
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

    } //PrefsFragment end
}
