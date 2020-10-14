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

    public void ListKontakter(){
        db = new DBHandler(getContext());
        kontakter = db.hentAlle("Kontakter");
        kontaktString = new ArrayList<>();

        for(Kontakt enKontakt : kontakter){
            String ut = enKontakt.toString();
            kontaktString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.liste_kontakter){
            AdapterView.AdapterContextMenuInfo ACMI =(AdapterView.AdapterContextMenuInfo) menuInfo;
            long valgtKontaktPos = ((AdapterView.AdapterContextMenuInfo) menuInfo).id;
            int idValgtKontakt = (int)valgtKontaktPos;
            clickedKontakt = kontakter.get(idValgtKontakt);

            //HENT TEKST FRA STRING
            MenuItem mi1 = menu.add(1,1,1,R.string.endre);
            MenuItem mi2 = menu.add(0,0,0,R.string.slett);
        }
    }

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

    public void onSlettClicked(){
        db.slettKontakt(clickedKontakt.brukernavn);
        onResume();
    }
    public void onEndreClicked(){
        Intent i = new Intent(getActivity(),EndreKontaktActivity.class);
        i.putExtra("BRUKERNAVN", clickedKontakt.getBrukernavn());
        i.putExtra("NAVN", clickedKontakt.getNavn());
        i.putExtra("TELEFON", clickedKontakt.getTelefon());
        i.putExtra("ID",clickedKontakt.get_ID());
        startActivity(i);
    }



    //Metode som finner første tall i en String
    public int findID(String enkontakt){
        char [] spill = enkontakt.toCharArray();
        boolean fortsett = true;
        String ut = "";
        for(int i = 0; i<spill.length;i++){

            //Finner ascii verdien til tegnet
            int sjekk = spill[i] & 127;

            //Om det er heltall vil ascii verdien være mellom 58 og 47
            if(sjekk>47 && sjekk< 58){
                ut+=spill[i];
                fortsett=false;
            }
            //Breaker vis forrige verdi var tall og dette ikke er
            else if(!fortsett){
                break;
            }
        }
        return Integer.parseInt(ut);
    }

}
