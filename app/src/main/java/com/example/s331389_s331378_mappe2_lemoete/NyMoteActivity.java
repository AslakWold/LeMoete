package com.example.s331389_s331378_mappe2_lemoete;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NyMoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText typeInn;
    EditText stedInn;
    EditText datoInn;
    EditText tidInn;
    DBHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggtilmote);
        typeInn = (EditText) findViewById(R.id.text_type);
        stedInn = (EditText) findViewById(R.id.text_sted);
        datoInn = (EditText) findViewById(R.id.text_dato);
        datoInn.setEnabled(false);
        tidInn = (EditText) findViewById(R.id.text_tid);

    }

    //Buttons

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

    public void btnDatoDialog(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    //Buttons - slutt

    //konverterer inputString til dato
    public Date StringToDate(String dato) throws ParseException {
        Date date = DateFormat.getDateInstance(DateFormat.SHORT).parse(dato);
        return date;
    }

    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        datoInn.setText(currentDateString);
    }
}
