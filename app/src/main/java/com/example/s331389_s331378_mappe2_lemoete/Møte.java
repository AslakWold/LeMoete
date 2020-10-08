package com.example.s331389_s331378_mappe2_lemoete;

import java.util.ArrayList;
import java.util.Date;

public class Møte {
    Date tid;
    String sted;
    ArrayList<Kontakt> møteMedlemmer;
    int _ID;

    public Møte(Date tid, String sted, ArrayList<Kontakt> møteMedlemmer){
        this.tid=tid;
        this.sted=sted;
        this.møteMedlemmer=møteMedlemmer;
    }
}
