package com.example.s331389_s331378_mappe2_lemoete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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