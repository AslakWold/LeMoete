package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class KontaktTilMoeteActivity extends AppCompatActivity {
    DBHandler db;
    List<Kontakt> kontakter;
    List<String> kontaktString;
    Kontakt clickedKontakt;
    ListView lv;
    int id_moete;
    long id_kontakt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        setContentView(R.layout.activity_kontakttilmoete);
        lv = findViewById(R.id.kontaker_kontaktmote);
        listKontakter();
        id_moete = getIntent().getExtras().getInt("moete_id");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedKontakt = kontakter.get(i);
                id_kontakt = clickedKontakt.get_ID();
                db.leggTilDeltagelse(id_moete,id_kontakt);
                fjernFraList(i);
            }
        });
    }


    public void fjernFraList(int pos){
        kontakter.remove(pos);
        kontaktString = new ArrayList<>();
        for(Kontakt enKontakt : kontakter){
            String ut = enKontakt.toString();
            kontaktString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);

    }
    public void listKontakter(){
        db = new DBHandler(this);
        kontakter = db.hentAlle("Kontakter");
        kontaktString = new ArrayList<>();

        for(Kontakt enKontakt : kontakter){
            String ut = enKontakt.toString();
            kontaktString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);
    }
}
