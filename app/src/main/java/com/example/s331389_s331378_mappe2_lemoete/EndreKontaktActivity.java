package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EndreKontaktActivity extends AppCompatActivity {

    EditText navnInn;
    EditText telefonInn;
    EditText brukernavnInn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endrekontakt);
        navnInn = (EditText) findViewById(R.id.text_navn);
        telefonInn = (EditText)findViewById(R.id.text_tlf);
        brukernavnInn = (EditText)findViewById(R.id.text_brukernavn);
    }
}
