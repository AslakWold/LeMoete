package com.example.s331389_s331378_mappe2_lemoete;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class EndreMoeteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    EditText typeInn;
    EditText stedInn;
    EditText datoInn;
    EditText tidInn;
    DBHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endremoete);
        typeInn = (EditText) findViewById(R.id.text_type);
        stedInn = (EditText) findViewById(R.id.text_sted);
        datoInn = (EditText) findViewById(R.id.text_dato);
        datoInn.setEnabled(false);
        tidInn = (EditText) findViewById(R.id.text_tid);
        tidInn.setEnabled(false);
        startup();

    }

    //Setter info fra valgt møte veed start av activity
    public void startup(){
        tidInn.setText(getIntent().getExtras().getString("TID"));
        stedInn.setText(getIntent().getExtras().getString("STED"));
        datoInn.setText(getIntent().getExtras().getString("DATO"));
        typeInn.setText(getIntent().getExtras().getString("TYPE"));
    }

    //Oppdaterer møte
    public void btnLeggTil(View v){
        db = new DBHandler(this);
        if(!typeInn.getText().toString().isEmpty() && !tidInn.getText().toString().isEmpty() &&
                !datoInn.getText().toString().isEmpty() && !stedInn.getText().toString().isEmpty()){
            Møte nyttmøte = new Møte();
            nyttmøte.setType(typeInn.getText().toString());
            nyttmøte.setSted(stedInn.getText().toString());
            nyttmøte.setDato(datoInn.getText().toString());
            nyttmøte.setTid(tidInn.getText().toString());
            nyttmøte.setMoete_ID(getIntent().getExtras().getInt("ID"));

            //Får tilbake møtet med id for å kunne bruke den i til å lage møtedeltagelse

            db.oppdatereMote(nyttmøte);
            finish();

        }else{
            toastMelding("Du må fylle inn verdier i alle feltene");
        }
    }
    public void btnClear(View v){
        onBackPressed();
    }

    @Override //Lager en alertdialog når vi trykker tilbake fra "Ny kontakt"
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Avslutt?")
                .setMessage("Endringene lagres ikke.")
                .setPositiveButton(R.string.avslutt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EndreMoeteActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.ikke_avslutt, null)
                .show();
    }



    //Metoder for dato og tid
    public void btnDatoDialog(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }
    public void btnTimeDialog(View view) {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
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

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutt) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hours);
        c.set(Calendar.MINUTE,minutt);
        String currentTimeString =c.get(Calendar.HOUR_OF_DAY) + ":" +c.get(Calendar.MINUTE);


        tidInn.setText(currentTimeString);
    }
    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
