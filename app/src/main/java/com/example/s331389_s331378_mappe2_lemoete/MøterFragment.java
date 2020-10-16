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

public class MøterFragment extends Fragment {

    DBHandler db;
    ListView lv;
    Møte clickedMøte;
    ArrayList<String> møterString;
    List<Møte> moter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_moeter, container, false);
        lv = (ListView)v.findViewById(R.id.liste_møter);
        registerForContextMenu(lv);
        listmoter();

        return v;
    }

    public void listmoter(){
        db = new DBHandler(getContext());
        moter = db.hentAlleMoter();
        møterString = new ArrayList<>();


        for(Møte etMøte : moter){
            String ut = etMøte.toString();
            møterString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, møterString);
        lv.setAdapter(adapter);
    }






}
