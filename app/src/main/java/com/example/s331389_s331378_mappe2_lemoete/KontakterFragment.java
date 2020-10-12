package com.example.s331389_s331378_mappe2_lemoete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kontakter, container, false);
        lv = (ListView)v.findViewById(R.id.liste_kontakter);

        ListKontakter();
        return v;
    }

    public void ListKontakter(){
        //List<Kontakt> kontakter = db.finnAlleKontakter();
       /* List<Kontakt> kontakter = new ArrayList<>();
        ArrayAdapter <Kontakt> utKontakter = new ArrayAdapter<>(this,lv, kontakter);*/
        db = new DBHandler(getActivity());
        List<Kontakt> kontakter = db.hentAlle("Kontakter");
        ArrayList<String> kontaktString = new ArrayList<>();

        for(Kontakt enKontakt : kontakter){
            String ut = "";
            ut+="ID : " + enKontakt.get_ID() + " NAVN : "+enKontakt.getNavn()
                    + " TELEFON : " + enKontakt.getTelefon() + " BRUKERNAVN : " + enKontakt.getBrukernavn();
            kontaktString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, kontaktString);
        lv.setAdapter(adapter);



    }
}
