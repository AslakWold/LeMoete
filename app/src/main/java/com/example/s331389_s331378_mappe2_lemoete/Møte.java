package com.example.s331389_s331378_mappe2_lemoete;

import java.util.Date;

public class Møte {
    Date tid;
    String sted;
    String type;
    int moete_ID;

    public Møte() {

    }

    public Møte(Date tid, String sted, String type, int moete_ID) {
        this.tid = tid;
        this.sted = sted;
        this.type = type;
        this.moete_ID = moete_ID;
    }

    public Møte(Date tid, String sted, String type){
        this.tid=tid;
        this.sted=sted;
        this.type = type;
    }

    public Date getTid() {
        return tid;
    }

    public void setTid(Date tid) {
        this.tid = tid;
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
}
