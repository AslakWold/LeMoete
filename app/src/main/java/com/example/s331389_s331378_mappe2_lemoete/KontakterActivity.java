package com.example.s331389_s331378_mappe2_lemoete;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class KontakterActivity extends AppCompatActivity {

    public DBHandler db;
    ListView lv;
    ArrayList<String> kontaktListe = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        //lv = (ListView) findViewById(R.id.kontakterList);
        ListKontakter();
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
        Log.d("TAG", "Listkontakter: Lister kontaktene i ListView");
        Cursor kontakter = db.getKontakter();
        while(kontakter.moveToNext()) {
            kontaktListe.add(kontakter.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kontaktListe);
        lv.setAdapter(adapter);

    }


}
