package com.example.s331389_s331378_mappe2_lemoete;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VisDeltagereActivity extends AppCompatActivity {
    DBHandler db;
    List<Kontakt> deltagere;
    List<String> stringDeltagere;
    int moete_id;
    ListView lv;
    Kontakt clickedKontakt;
    FloatingActionButton leggTilDeltagere;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visdeltagere);
        lv = findViewById(R.id.listDeltagere);
        db = new DBHandler(this);
        leggTilDeltagere = (FloatingActionButton) findViewById(R.id.legg_til_deltagere);
        registerForContextMenu(lv);
        moete_id = getIntent().getExtras().getInt("MOETE_ID");
        listDeltagere();


    }

    @Override
    protected void onResume() {
        listDeltagere();
        super.onResume();
    }

    public void btnBack(View v) {
        onBackPressed();
    }


    //Starter activity for å legge til flere deltagere
    public void btnLeggTilDeltagere(View v) {
        Intent intent = new Intent(this, KontaktTilMoeteActivity.class);
        intent.putExtra("moete_id", moete_id);
        System.out.println("I VIS DELTAGERE : " + moete_id);
        startActivity(intent);
    }


    //Oppretter contextmeny ved langt klikk
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.listDeltagere){
            AdapterView.AdapterContextMenuInfo ACMI =(AdapterView.AdapterContextMenuInfo) menuInfo;
            long valgtKontaktPos = ((AdapterView.AdapterContextMenuInfo) menuInfo).id;
            int idValgtKontakt = (int)valgtKontaktPos;
            clickedKontakt = deltagere.get(idValgtKontakt);
            System.out.println(clickedKontakt.get_ID());

            //HENT TEKST FRA STRING
            MenuItem mi2 = menu.add(0,0,0,R.string.slett);
        }
    }

    //Finner  valgt element i contextmenyen
        //Her bare ett valgt , men ingenting gjøres dersom det trykkes ut
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo ACMI = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 0:
                onSlettClicked();
                break;
            default:
                break;

        }
        return true;
    }

    //Sletter møtedeltagelse for valgt kontakt
    public void onSlettClicked(){
        db.slettDeltagelse(moete_id,clickedKontakt.get_ID());
        listDeltagere();
    }

    //Lister alle deltagere til møte
    public void listDeltagere(){
        //db = new DBHandler(this);
        deltagere = db.finnDeltagere(moete_id);
        stringDeltagere = new ArrayList<>();

        for(Kontakt kontakt : deltagere){
            stringDeltagere.add(kontakt.toString());
        }

        //Fyller listview ved hjelp av Arrayadapter
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringDeltagere);
        lv.setAdapter(adapter);
    }
}
