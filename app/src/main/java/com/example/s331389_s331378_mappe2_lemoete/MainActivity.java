package com.example.s331389_s331378_mappe2_lemoete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public DBHandler db;
    FloatingActionButton leggTilKnapp;
    //Button kontakter, møteOversikt, opprettMøte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leggTilKnapp = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new MøterFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.møteoversikt:
                            selectedFragment = new MøterFragment();
                            break;
                        case R.id.kontakter:
                            selectedFragment = new KontakterFragment();
                            break;
                        case R.id.instillinger:
                            selectedFragment = new InnstillingerFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    //Buttons

    public void btnKontaktEditor(View v) {
        Intent intent = new Intent(this, KontakterActivity.class);
        startActivity(intent);
    }

    //Buttons slutt


    public void leggTilKontakt(Kontakt kontakt) {
        db.leggTilKontakt(kontakt);
        if(db.result != -1) {
            toastMelding("Kontakt lagt til");
        } else {
            toastMelding("Fikk ikke lagt til kontakt");
        }
    }
    //Toast-metode

    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //Buttonsmetoder som starter nye aktiviteter
    public void startMøteOversikt(View view) {
    }
    public void startOpprettMøte(View v){
    }
    public void startKontakter(View v){
        Intent i = new Intent(this,KontakterActivity.class);
        startActivity(i);
    }
}