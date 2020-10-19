package com.example.s331389_s331378_mappe2_lemoete;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NyKontaktActivity extends AppCompatActivity {

    EditText navnInn;
    EditText telefonInn;
    EditText brukernavnInn;

    public DBHandler db= new DBHandler(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakter);
        navnInn = (EditText) findViewById(R.id.text_navn);
        telefonInn = (EditText)findViewById(R.id.text_telefonnummer);
        brukernavnInn = (EditText)findViewById(R.id.text_brukernavn);
    }

    //Buttons

    //Legger til Kontakt til databsen
    public void btnLeggTil(View v){
        if(!brukernavnInn.getText().toString().isEmpty()
        && !navnInn.getText().toString().isEmpty()
        && !telefonInn.getText().toString().isEmpty()) {
            Kontakt nyKontakt = new Kontakt(brukernavnInn.getText().toString(),
                    navnInn.getText().toString(),
                    telefonInn.getText().toString());

            db.leggTilKontakt(nyKontakt);
            finish();
        } else {
            toastMelding("Du må fylle inn verdier i alle feltene");
        }
    }

    public void btnEndre(View v){
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navnInn.getText().toString());
        kontakt.setTelefon(telefonInn.getText().toString());

        db.oppdaterKontakt(kontakt);
        recreate();

    }

    public void btnClear(View v) {
        onBackPressed();
    }

    //Buttons - slutt

    //Hjelpemetoder

    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override //Lager en alertdialog når vi trykker tilbake fra "Ny kontakt"
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Avslutt?")
                .setMessage("Endringene lagres ikke.")
                .setPositiveButton(R.string.avslutt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NyKontaktActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.ikke_avslutt, null)
                .show();
    }
}
