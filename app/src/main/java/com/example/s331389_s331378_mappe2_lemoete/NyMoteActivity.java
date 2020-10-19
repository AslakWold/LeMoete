package com.example.s331389_s331378_mappe2_lemoete;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class NyMoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
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
        tidInn.setEnabled(false);

    }

    //Buttons

    //Legger til møte i databser
    public void btnLeggTil(View v){
        db = new DBHandler(this);
        if(!typeInn.getText().toString().isEmpty() && !tidInn.getText().toString().isEmpty() &&
        !datoInn.getText().toString().isEmpty() && !stedInn.getText().toString().isEmpty()){
            Møte nyttmøte = new Møte();
            nyttmøte.setType(typeInn.getText().toString());
            nyttmøte.setSted(stedInn.getText().toString());
            nyttmøte.setDato(datoInn.getText().toString());
            nyttmøte.setTid(tidInn.getText().toString());

            //Får tilbake møtet med id for å kunne bruke den i til å lage møtedeltagelse

            Møte passMøte = db.leggTilMote(nyttmøte);
            System.out.println(passMøte.toString());

            Intent i = new Intent(this, KontaktTilMoeteActivity.class);
            i.putExtra("moete_id",passMøte.getMoete_ID());
            startActivity(i);
            finish();
        }else{
            toastMelding("Du må fylle inn verdier i alle feltene");
        }
    }


    public void btnClear(View v){
        onBackPressed();
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


    //Buttons - slutt



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
    //Slutt metoder dato og tid


    public void toastMelding(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
