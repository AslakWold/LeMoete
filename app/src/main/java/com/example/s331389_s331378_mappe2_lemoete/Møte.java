package com.example.s331389_s331378_mappe2_lemoete;

public class Møte {
    String dato;
    String tid;
    String sted;
    String type;
    int moete_ID;

    //Kontruktører
    public Møte() {} //Tom

    public Møte(String dato, String tid, String sted, String type, int moete_ID) {
        this.dato = dato;
        this.sted = sted;
        this.type = type;
        this.tid = tid;
        this.moete_ID = moete_ID;
    }

    public Møte(String dato, String tid, String sted, String type){
        this.dato = dato;
        this.sted= sted;
        this.type = type;
        this.tid = tid;
    }


    //Getters og setters
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
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
        + "\nTid : " + tid;
        return ut;
    }
}
