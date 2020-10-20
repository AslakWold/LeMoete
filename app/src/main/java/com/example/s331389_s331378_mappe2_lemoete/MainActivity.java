package com.example.s331389_s331378_mappe2_lemoete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public DBHandler db;
    FloatingActionButton leggTilKnapp;
    FloatingActionButton leggTilMote;
    String tidspunkt;
    String melding;
    boolean periodisk;
    Fragment selectedFragment;


    //SettingsFragment
    EditText innMelding;
    TextView tidInn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPeriodisk();

        //Sjekker om det skal startes en am ved start dersom det ikke er noen og det er skrudd på
        AlarmManager am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        if(periodisk && am!=null){
            startService();
        }

        leggTilKnapp = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        leggTilMote = (FloatingActionButton) findViewById(R.id.leggTilMote);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new MøterFragment()).commit();

        //startSettingsfragment();
        getPermission();
    }



    //Metode som starter melding/notifikasjon service
    public void startService(){
        Intent i = new Intent();
        i.setAction("mittbroadacast");
        sendBroadcast(i);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment = new MøterFragment();
                    switch (item.getItemId()) {
                        case R.id.møteoversikt:
                            selectedFragment = new MøterFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_container, selectedFragment).commit();
                            break;
                        case R.id.kontakter:
                            selectedFragment = new KontakterFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_container, selectedFragment).commit();
                            break;
                        case R.id.instillinger:
                            selectedFragment = new SettingsFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_container, selectedFragment).commit();
                            break;
                    }
                    return true;
                }
            };


    //Buttons

    public void btnKontaktEditor(View v) {
        Intent intent = new Intent(this, NyKontaktActivity.class);
        startActivity(intent);
    }

    //Buttons slutt

    //Toast-metode

    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //Buttonsmetoder som starter nye aktiviteter

    public void startKontakter(View v){
        Intent i = new Intent(this, NyKontaktActivity.class);
        startActivity(i);
    }
    public void leggTilMote(View v){
        Intent i = new Intent(this, NyMoteActivity.class);
        startActivity(i);
    }


    //Spør om tillatelse til å sende melding dersom det ikke allerede er gitt.
    public void getPermission(){
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION == PackageManager.PERMISSION_GRANTED) {
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }


    //Shared preferences metoder
    public void savetidspunkt(){
        getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .edit()
                .putString("tidspunkt",tidspunkt)
                .apply();
    }
    public void gettidspunkt(){
        tidspunkt  = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getString("tidspunkt","7:0");
    }
    public void saveMelding(){
        getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .edit()
                .putString("melding",melding)
                .apply();
    }
    public void getMelding(){
        melding  = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getString("melding","Husk møte idag");
    }

    public void getPeriodisk(){
        periodisk = getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("periodisk",true);
    }




}