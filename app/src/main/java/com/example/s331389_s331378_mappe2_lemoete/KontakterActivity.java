package com.example.s331389_s331378_mappe2_lemoete;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class KontakterActivity extends AppCompatActivity {

    EditText navnInn;
    EditText telefonInn;
    EditText brukernavnInn;

    public DBHandler db= new DBHandler(this);
    ListView lv;
    List<String> kontaktListe = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        navnInn = (EditText) findViewById(R.id.text_navn);
        telefonInn = (EditText)findViewById(R.id.text_tlf);
        brukernavnInn = (EditText)findViewById(R.id.text_brukernavn);
        /*lv = (ListView) findViewById(R.id.kontakterList);
        //lv = (ListView) findViewById(R.id.kontakterList);
        ListKontakter(); */
    }

    //Buttons
    //Legge til kode fra forelesning her
    public void btnLeggTil(View v){
        Kontakt nyKontakt = new Kontakt(brukernavnInn.getText().toString(),
                navnInn.getText().toString(),
                telefonInn.getText().toString());

        db.leggTilKontakt(nyKontakt);
        super.onBackPressed();

        //recreate();
        /*brukernavnInn.setText("");
        navnInn.setText("");
        telefonInn.setText(""); */


    }
    public void btnEndre(View v){
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navnInn.getText().toString());
        kontakt.setTelefon(telefonInn.getText().toString());
        kontakt.setBrukernavn(brukernavnInn.getText().toString());

        db.oppdaterKontakt(kontakt);
        recreate();

    }
    public void btnSlett(View v){
        String brukernavn = brukernavnInn.getText().toString();
        db.slettKontakt(brukernavn);
        recreate();
    }

    public void btnClear(View v) {
        onBackPressed();
    }

    //Buttons - slutt

    public void ListKontakter(){
        //List<Kontakt> kontakter = db.finnAlleKontakter();
       /* List<Kontakt> kontakter = new ArrayList<>();
        ArrayAdapter <Kontakt> utKontakter = new ArrayAdapter<>(this,lv, kontakter);*/
       List<Kontakt> kontakter = db.hentAlle("Kontakter");
       ArrayList<String> kontaktString = new ArrayList<>();

       for(Kontakt enKontakt : kontakter){
           String ut = "";
           ut+="ID : " + enKontakt.get_ID() + " NAVN : "+enKontakt.getNavn()
           + " TELEFON : " + enKontakt.getTelefon() + " BRUKERNAVN : " + enKontakt.getBrukernavn();
           kontaktString.add(ut);
       }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);



    }

    //Hjelpemetoder


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Avslutt?")
                .setMessage("Endringene lagres ikke.")
                .setPositiveButton(R.string.avslutt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        KontakterActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.ikke_avslutt, null)
                .show();
    }
}
