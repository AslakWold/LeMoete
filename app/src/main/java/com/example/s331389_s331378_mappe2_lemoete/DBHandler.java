package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "_ID";
    static String KEY_USER_NAME = "brukernavn";
    static String KEY_NAME = "navn";
    static String KEY_PH_NO = "telefon";
    long result;

   /* static String TABLE_MØTER = "Møter";
    static String STED = "sted";
    static String TYPE_MØTE = "type";
    //tidspunkt
    //ID-felt
    //Oversikt over kontakter i møte

    //Møtedeltagelse
    //ikke sikker på hvilke felter vi trenger. */
   //Brukernavn og MøteID

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "MøteDatabase";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableKontakter(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        onCreate(db);
    }


    //Legger til data

    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, kontakt.getBrukernavn());
        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());
        result = db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }


    //Legger til data - slutt

    //Sletter data

    public void slettKontakt(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_KONTAKTER, KEY_ID + " =? ",
                new String[] {Long.toString(id)});
        db.close();
    }


    //Sletter data - slutt



    //Oppdaterer databser -sTart

    public int oppdaterKontakt(Kontakt kontakt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_USER_NAME, kontakt.getBrukernavn());
        values.put(KEY_PH_NO,kontakt.getTelefon());

        int endret = db.update(TABLE_KONTAKTER, values, KEY_ID + "= ?",new String[]{String.valueOf(kontakt.get_ID())});

        db.close();
        return endret;
    }
    //Oppdater databaser -slutt

    //Returnerer data

    public Cursor getKontakter() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT brukernavn FROM " + TABLE_KONTAKTER;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public List<Kontakt> hentAlle(String TABLE) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
        String selectQue = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(selectQue, null);

        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.setBrukernavn(cursor.getString(1));
                kontakt.setNavn(cursor.getString(2));
                kontakt.setTelefon(cursor.getString(3));
                kontaktListe.add(kontakt);
        } while (cursor.moveToNext()) ;
        cursor.close();
        db.close();
    }
        return kontaktListe;

    }

    //Returnerer data - slutt

    //Tabeller
    public void createTableKontakter(SQLiteDatabase db) {
        String LAG_TABLE_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER
                + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT NOT NULL UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        Log.d("SQL", LAG_TABLE_KONTAKTER);
        db.execSQL(LAG_TABLE_KONTAKTER);
    }

    public void createTableMøter(SQLiteDatabase db) {

    }

    public void createTableMøteDeltagelse(SQLiteDatabase db) {

    }

    //Tabeller slutt

    //Metoder som returner

    //Hjelpemetoder

    /* public boolean dataLagtTil(long result) {
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    } */

    //Hjelpemetoder slutt

} //DBHandler slutt
