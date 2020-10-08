package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class KontakterActivity extends AppCompatActivity {

    DBHandler db;
    ListView lv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        lv = (ListView)findViewById(R.id.kontakterList);
    }


    //Legge til kode fra forelesning her
    public void btnLeggTil(View v){

    }
    public void btnEndre(View v){

    }
    public void btnSlett(View v){

    }

    public void ListKontakter(){
        //List<Kontakt> kontakter = db.finnAlleKontakter();
       /* List<Kontakt> kontakter = new ArrayList<>();
        ArrayAdapter <Kontakt> utKontakter = new ArrayAdapter<>(this,lv, kontakter);*/

    }


}
