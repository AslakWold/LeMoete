package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NyMoteActivity extends AppCompatActivity {
    EditText typeInn;
    EditText stedInn;
    EditText datoInn;
    EditText tidInn;
    DBHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_leggtilmote);
        typeInn = (EditText) findViewById(R.id.text_type);
        stedInn = (EditText) findViewById(R.id.text_sted);
        datoInn = (EditText) findViewById(R.id.text_dato);
        tidInn = (EditText) findViewById(R.id.text_tid);
        super.onCreate(savedInstanceState);
    }

    public void btnLeggTil(View v){
        db = new DBHandler(this);
        if(!typeInn.getText().toString().isEmpty() && !tidInn.getText().toString().isEmpty() &&
        !datoInn.getText().toString().isEmpty() && !stedInn.getText().toString().isEmpty()){
            Møte nyttmøte = new Møte();
            nyttmøte.setType(typeInn.getText().toString());
            nyttmøte.setSted(stedInn.getText().toString());
            try {
                Date dato = StringToDate(datoInn.getText().toString());
                nyttmøte.setDato(dato);
            } catch (ParseException e) {
                toastMelding("Dato må skrives på formen dd/MM/yyyy");
            }
            nyttmøte.setTid(Time.valueOf(tidInn.getText().toString()));


            //Får tilbake møtet med id for å kunne bruke den i til å lage møtedeltagelse

            Møte passMøte = db.leggTilMote(nyttmøte);





        }else{
            toastMelding("Du må fylle inn verdier i alle feltene");
        }


    }
    public void btnClear(View v){
        onBackPressed();
    }

    //konverterer inputString til dato
    public Date StringToDate(String dato) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dato);
        return date;
    }

    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
