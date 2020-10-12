package com.example.s331389_s331378_mappe2_lemoete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public DBHandler db;
    //Button kontakter, møteOversikt, opprettMøte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DBHandler(this);
    }

    //Buttons


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