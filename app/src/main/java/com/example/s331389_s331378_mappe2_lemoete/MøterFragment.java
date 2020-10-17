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
        db = new DBHandler(getContext());
        registerForContextMenu(lv);
        listmoter();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedMøte = moter.get(i);
                Intent intent = new Intent(getActivity(), VisDeltagereActivity.class);
                intent.putExtra("MOETE_ID",clickedMøte.getMoete_ID());
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        listmoter();
        super.onResume();
    }

    public void listmoter(){
        moter = db.hentAlleMoter();
        møterString = new ArrayList<>();


        for(Møte etMøte : moter){
            String ut = etMøte.toString();
            møterString.add(ut);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, møterString);
        lv.setAdapter(adapter);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId()==R.id.liste_møter){
            AdapterView.AdapterContextMenuInfo ACMI =(AdapterView.AdapterContextMenuInfo) menuInfo;
            long valgtMoetePos = ((AdapterView.AdapterContextMenuInfo) menuInfo).id;
            int idValgtMoete = (int)valgtMoetePos;
            clickedMøte = moter.get(idValgtMoete);

            //HENT TEKST FRA STRING
            MenuItem mi2 = menu.add(0,0,0,R.string.slett);
            MenuItem mi1 = menu.add(1,1,1,R.string.endre);
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

    public void onEndreClicked(){
        Intent i = new Intent(getActivity(), EndreMoeteActivity.class);
        i.putExtra("ID", clickedMøte.getMoete_ID());
        i.putExtra("TID", clickedMøte.getTid());
        i.putExtra("DATO",clickedMøte.getDato());
        i.putExtra("TYPE",clickedMøte.getType());
        i.putExtra("STED", clickedMøte.getSted());
        startActivity(i);
    }
    public void onSlettClicked(){
        db.slettMoeteDeltagelser(clickedMøte.getMoete_ID());
        db.slettMote(clickedMøte.getMoete_ID());
        listmoter();
    }






}
