package com.example.s331389_s331378_mappe2_lemoete;

public class Kontakt {

    long _ID;
    String brukernavn;
    String navn;
    String telefon;

    public Kontakt() {
    }

    public Kontakt(String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public Kontakt(String brukernavn, String navn, String telefon) {
        this.brukernavn = brukernavn;
        this.navn = navn;
        this.telefon = navn;
    }

    public Kontakt(long _ID, String brukernavn, String navn, String telefon) {
        this._ID = _ID;
        this.brukernavn = brukernavn;
        this.navn = navn;
        this.telefon = telefon;
    }

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
}
