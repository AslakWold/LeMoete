package com.example.s331389_s331378_mappe2_lemoete;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class KontaktTilMoeteActivity extends AppCompatActivity {
    DBHandler db;
    List<Kontakt> kontakter;
    List<Kontakt> brukteKontakter;
    List<String> kontaktString;
    Kontakt clickedKontakt;
    List<Kontakt> lagtTil;
    ListView lv;
    int id_moete;
    long id_kontakt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        setContentView(R.layout.activity_kontakttilmoete);
        lv = findViewById(R.id.kontaker_kontaktmote);
        lagtTil = new ArrayList<>();
        id_moete = getIntent().getExtras().getInt("moete_id");
        listKontakter();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedKontakt = kontakter.get(i);
                lagtTil.add(clickedKontakt);
                //id_kontakt = clickedKontakt.get_ID();
                //db.leggTilDeltagelse(id_moete,id_kontakt);
                fjernFraList(i);
            }
        });
    }

    public void btnLeggTil(View v){
        for(Kontakt kontakt : lagtTil){
            id_kontakt = kontakt.get_ID();
            db.leggTilDeltagelse(id_moete,id_kontakt);
        }
        super.onBackPressed();
    }
    public void btnClear(View v){
        onBackPressed();
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

        brukteKontakter = db.finnDeltagere(id_moete);

        for(Kontakt kontakt : brukteKontakter){
            for(Kontakt kontakt1 : kontakter){
                if(kontakt.getBrukernavn().equals(kontakt1.getBrukernavn())){
                    kontakter.remove(kontakt1);
                    break;
                }
            }
        }

        for(Kontakt enKontakt : kontakter){
            String ut = enKontakt.toString();
            kontaktString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);
    }

    public void hentDeltagere(){
        db = new DBHandler(this);

        brukteKontakter = db.finnDeltagere(id_moete);


    }

    @Override //Lager en alertdialog når vi trykker tilbake fra "Ny kontakt"
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Avslutt?")
                .setMessage("Endringene lagres ikke.")
                .setPositiveButton(R.string.avslutt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       KontaktTilMoeteActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.ikke_avslutt, null)
                .show();
    }
}
