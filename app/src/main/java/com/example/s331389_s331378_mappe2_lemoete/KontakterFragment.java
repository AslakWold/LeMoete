package com.example.s331389_s331378_mappe2_lemoete;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class KontakterFragment extends Fragment {

    DBHandler db;
    ListView lv;
    Kontakt clickedKontakt;
    ArrayList<String> kontaktString;
    List<Kontakt> kontakter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kontakter, container, false);
        lv = (ListView)v.findViewById(R.id.liste_kontakter);
        registerForContextMenu(lv);
        ListKontakter();
        return v;
    }

    @Override
    public void onResume() {
        ListKontakter();
        super.onResume();
    }


    //Lister kontakter
    public void ListKontakter(){
        db = new DBHandler(getContext());
        kontakter = db.hentAlle("Kontakter");
        kontaktString = new ArrayList<>();

        for(Kontakt enKontakt : kontakter){
            String ut = enKontakt.toString();
            kontaktString.add(ut);
        }

        //Fyller listview ved hjelp av Adapter
        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);
    }


    //Lager context menu ved langt klikk på listview
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.liste_kontakter){
            AdapterView.AdapterContextMenuInfo ACMI =(AdapterView.AdapterContextMenuInfo) menuInfo;
            long valgtKontaktPos = ((AdapterView.AdapterContextMenuInfo) menuInfo).id;
            int idValgtKontakt = (int)valgtKontaktPos;
            clickedKontakt = kontakter.get(idValgtKontakt);
            System.out.println(clickedKontakt.get_ID());

            //HENT TEKST FRA STRING
            MenuItem mi1 = menu.add(1,1,1,R.string.endre);
            MenuItem mi2 = menu.add(0,0,0,R.string.slett);
        }
    }

    //Sjekker valgt element i contextmeny
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo ACMI = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case 0:
                onSlettClicked();
                break;
            case 1:
                onEndreClicked();
                break;
            default:
                break;

        }
        return true;
    }

    //Sletter kontakt ved valgt slett
    public void onSlettClicked(){
        db.slettKontakt(clickedKontakt.brukernavn,clickedKontakt.get_ID());
        onResume();
    }

    //Åpner endrekontaktactivity ved valgt endre og sender med informasjon med intent
    public void onEndreClicked(){
        Intent i = new Intent(getActivity(),EndreKontaktActivity.class);
        i.putExtra("BRUKERNAVN", clickedKontakt.getBrukernavn());
        i.putExtra("NAVN", clickedKontakt.getNavn());
        i.putExtra("TELEFON", clickedKontakt.getTelefon());
        i.putExtra("ID",clickedKontakt.get_ID());
        startActivity(i);
    }

}
