package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NyMoteActivity extends AppCompatActivity {
    EditText typeInn;
    EditText stedInn;
    EditText DatoInn;
    EditText tidInn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_leggtilmote);
        typeInn = (EditText) findViewById(R.id.text_type);
        stedInn = (EditText) findViewById(R.id.text_sted);
        DatoInn = (EditText) findViewById(R.id.text_dato);
        tidInn = (EditText) findViewById(R.id.text_tid);
        super.onCreate(savedInstanceState);
    }

    public void btnLeggTil(View v){

    }
    public void btnClear(View v){
        onBackPressed();
    }
}
