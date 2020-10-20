package com.example.s331389_s331378_mappe2_lemoete;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.Switch;
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
    Switch startPeriodisk;
    EditText etxtMelding;
    boolean sendPeriodic;
    String melding;
    ImageButton lagreMelding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_innstillinger, container, false);
        //Henter inn tidpunkt, Melding og om det skal sendes fra Sharedpreferences
        getTidspunkt();
        getMelding();
        getPeriodisk();

        //Initialiserer og setter informasjon i felter
        tidInn = (TextView) v.findViewById(R.id.txtTid);
        etxtMelding = (EditText) v.findViewById(R.id.eTextMelding);
        etxtMelding.setText(melding);
        tidInn.setText(tidspunkt);
        imageButtonTid = (ImageButton)v.findViewById(R.id.tid_icon);
        lagreMelding = (ImageButton) v.findViewById(R.id.lagreText);
        startPeriodisk = (Switch)v.findViewById(R.id.onOffSwitch);
        startPeriodisk.setChecked(sendPeriodic);
        sendPeriodic = startPeriodisk.isChecked();


        //Onclick metoder som brukes siden det er fragment
        lagreMelding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                melding = etxtMelding.getText().toString();
                System.out.println(melding);
                saveMelding();
                stoppService();
                startService();
            }
        });

        startPeriodisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPeriodic = startPeriodisk.isChecked();
                savePeriodisk();
                if(sendPeriodic){
                    startService();
                }else{
                    stoppService();
                }
            }
        });

        imageButtonTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VelgTid();
            }
        });
        return v;
    }

    //Metoder for settingsfragment


    //Metode for å velge tid for alarm
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
        if(sendPeriodic){
            stoppService();
            startService();
        }
    }
    //Slutt metoder for å velge tid


    //Metode som starter/stopper melding/notifikasjon service
    public void startService(){
        Intent i = new Intent();
        i.setAction("mittbroadacast");
        getActivity().sendBroadcast(i);
    }
    public void stoppService(){
        Intent i = new Intent(getActivity(),MinService.class);
        PendingIntent pi = PendingIntent.getService(getActivity(), 0,i,0);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        if(am!=null){
            am.cancel(pi);
        }
    }


    //SharedPreferences metoder
    public void getMelding(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        melding  = preferences
                .getString("melding","Husk møte idag");
    }
    public void saveMelding(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        preferences.edit()
                .putString("melding",melding)
                .apply();
    }
    public void savePeriodisk(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        preferences.edit()
                .putBoolean("periodisk",sendPeriodic)
                .apply();
    }
    public void getPeriodisk(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        sendPeriodic = preferences.getBoolean("periodisk",true);
    }
    public void savetidspunkt(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        preferences.edit()
                .putString("tidspunkt",tidspunkt)
                .apply();
    }
    public void getTidspunkt(){
        SharedPreferences preferences =this.getActivity().getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        tidspunkt  = preferences
                .getString("tidspunkt","7:0");
    }
    //Slutt sharedpreferences metoder.


}//Slutt class
