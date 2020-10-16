package com.example.s331389_s331378_mappe2_lemoete;

import java.sql.Time;
import java.util.Date;

public class Møte {
    Date dato;
    Time tid;
    String sted;
    String type;
    int moete_ID;
    //Tror kanskje vi må ha dato og tid fordi mange funksjoner er gått ut på dato.

    public Møte() {

    }

    public Møte(Date dato, Time tid, String sted, String type, int moete_ID) {
        this.dato = dato;
        this.sted = sted;
        this.type = type;
        this.tid = tid;
        this.moete_ID = moete_ID;
    }

    public Møte(Date dato, Time tid, String sted, String type){
        this.dato = dato;
        this.sted= sted;
        this.type = type;
        this.tid = tid;
    }

    public Time getTid() {
        return tid;
    }

    public void setTid(Time tid) {
        this.tid = tid;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMoete_ID() {
        return moete_ID;
    }

    public void setMoete_ID(int moete_ID) {
        this.moete_ID = moete_ID;
    }

    public String toString(){
        String ut = "ID : " + moete_ID + "\nType : " + type + "\nSted : " + sted + "\nDato : " + dato
        + "Tid : " + tid;
        return ut;
    }
}
