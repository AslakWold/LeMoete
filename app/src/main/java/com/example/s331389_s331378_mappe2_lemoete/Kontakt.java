package com.example.s331389_s331378_mappe2_lemoete;

public class Kontakt {

    long _ID;
    String brukernavn;
    String navn;
    String telefon;

    //kontruktører
    public Kontakt() { }
    public Kontakt(String brukernavn, String navn, String telefon) {
        this.brukernavn = brukernavn;
        this.navn = navn;
        this.telefon = telefon;
    }

    public Kontakt(long _ID, String brukernavn, String navn, String telefon) {
        this._ID = _ID;
        this.brukernavn = brukernavn;
        this.navn = navn;
        this.telefon = telefon;
    }
    //kontruktører slutt

    //getters og setters

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    //getters og setters slutt

    public String toString(){
        String ut= "Navn : " + navn + "\nBrukernavn : " + brukernavn + "\nTelefon : " + telefon +
                "\nID : " + _ID;
        return  ut;
    }
}
