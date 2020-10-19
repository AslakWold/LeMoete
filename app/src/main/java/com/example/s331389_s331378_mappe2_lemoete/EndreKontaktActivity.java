package com.example.s331389_s331378_mappe2_lemoete;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EndreKontaktActivity extends AppCompatActivity {

    EditText navnInn;
    EditText telefonInn;
    EditText brukernavnInn;
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endrekontakt);
        navnInn = (EditText) findViewById(R.id.text_navn);
        telefonInn = (EditText)findViewById(R.id.text_telefonnummer);
        brukernavnInn = (EditText)findViewById(R.id.text_brukernavn);
        brukernavnInn.setEnabled(false);

        //Setter info fra valgt kontakt
        brukernavnInn.setText(getIntent().getExtras().getString("BRUKERNAVN"));
        navnInn.setText(getIntent().getExtras().getString("NAVN"));
        telefonInn.setText(getIntent().getExtras().getString("TELEFON"));
    }

    //Buttons

    //Oppdaterer kontakt
    public void btnEndre(View v){
        db = new DBHandler(this);
        Kontakt kontakt = new Kontakt();
        if(!navnInn.getText().toString().isEmpty()
                && !telefonInn.getText().toString().isEmpty()) {
            kontakt.setNavn(navnInn.getText().toString());
            kontakt.setTelefon(telefonInn.getText().toString());
            kontakt.setBrukernavn(brukernavnInn.getText().toString());
            kontakt.set_ID(getIntent().getExtras().getLong("ID"));

            db.oppdaterKontakt(kontakt);
            finish();
        } else {
            toastMelding("Du må fylle inn verdier i alle feltene");
        }

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
                        EndreKontaktActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.ikke_avslutt, null)
                .show();
    }

    //Hjelpemetoder - slutt
}
