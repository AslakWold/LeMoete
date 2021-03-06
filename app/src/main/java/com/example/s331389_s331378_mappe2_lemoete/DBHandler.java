package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_KONTAKT_ID = "Kontakt_ID";
    static String KEY_USER_NAME = "brukernavn";
    static String KEY_NAME = "navn";
    static String KEY_PH_NO = "telefon";
    long result;

    //Møter
    static String TABLE_MOETER = "Moeter";
    static String KEY_STED = "sted";
    static String KEY_TYPE_MOETE = "type";
    static String KEY_MOETE_ID = "Moete_ID";
    static String KEY_DATO = "dato";
    static String KEY_TID = "tid";


    //Møtedeltagelse - vil gi oversikten over hvilke kontakter som er med på møte.
    static String TABLE_MOETEDELTAGELSE = "Moetedeltagelse";



    static int DATABASE_VERSION = 7;
    static String DATABASE_NAME = "MoeteDatabase";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableKontakter(db);
        createTableMøter(db);
        createTableMøteDeltagelse(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOETEDELTAGELSE); //Må droppe "child-tables" først
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOETER);
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

    public Møte leggTilMote(Møte møte){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STED, møte.getSted());
        values.put(KEY_TYPE_MOETE, møte.getType());
        values.put(KEY_TID, String.valueOf(møte.getTid()));
        values.put(KEY_DATO, String.valueOf(møte.getDato()));
        result = db.insert(TABLE_MOETER, null, values);

        Møte nyttMøte = new Møte();

        String selectQue = "SELECT * FROM " + TABLE_MOETER;
        Cursor cursor = db.rawQuery(selectQue, null);
        cursor.moveToLast();

        nyttMøte.setMoete_ID(cursor.getInt(0));
        nyttMøte.setType(cursor.getString(1));
        nyttMøte.setSted(cursor.getString(2));
        try{
            nyttMøte.setDato(cursor.getString(3));
        }catch(Exception e){
            throw new IllegalArgumentException();
        }
        nyttMøte.setTid(cursor.getString(4));
        db.close();

        return nyttMøte;

    }

    public void leggTilDeltagelse(int moete_id, long kontakt_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_ID,String.valueOf(kontakt_id));
        values.put(KEY_MOETE_ID,String.valueOf(moete_id));
        result = db.insert(TABLE_MOETEDELTAGELSE,null,values);
        db.close();
    }


    //Legger til data - slutt

    //Sletter data

    public void slettKontakt(String brukernavn,long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_KONTAKTER, KEY_USER_NAME + " =? ",
                new String[] {brukernavn});
        db.delete(TABLE_KONTAKTER, KEY_KONTAKT_ID + " =? ",
                new String[] {String.valueOf(id)});

        db.close();
    }
    public void slettMote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_MOETER, KEY_MOETE_ID + " =? ",
                new String[] {String.valueOf(id)});
        db.delete(TABLE_MOETEDELTAGELSE, KEY_MOETE_ID + " =? ",
                new String[] {String.valueOf(id)});
        db.close();
    }
    public void slettDeltagelse(int moete_id, long kontakt_id){
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_MOETEDELTAGELSE, KEY_MOETE_ID + " =? AND " + KEY_KONTAKT_ID + " =? ",
                new String[]{String.valueOf(moete_id), String.valueOf(kontakt_id)});
        db.close();
    }
    public void slettMoeteDeltagelser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_MOETEDELTAGELSE, KEY_MOETE_ID + " =? ",
                new String[] {String.valueOf(id)});
        db.close();
    }


    //Sletter data - slutt



    //Oppdaterer databser -sTart

    public long oppdaterKontakt(Kontakt kontakt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_PH_NO,kontakt.getTelefon());
        values.put(KEY_USER_NAME, kontakt.getBrukernavn());

        long endret = db.update(TABLE_KONTAKTER, values, KEY_USER_NAME + "= ?",
                new String[]{kontakt.getBrukernavn()});

        //dataLagtTil(endret);
        db.close();
        return endret;
    }

    public long oppdatereMote(Møte møte){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STED, møte.getSted());
        values.put(KEY_TYPE_MOETE, møte.getType());
        values.put(KEY_TID, String.valueOf(møte.getTid()));
        values.put(KEY_DATO, String.valueOf(møte.getDato()));
        values.put(KEY_MOETE_ID, String.valueOf(møte.getMoete_ID()));

        long endret = db.update(TABLE_MOETER, values, KEY_MOETE_ID + "= ?",
                new String[]{String.valueOf(møte.getMoete_ID())});

        return endret;
    }
    //Oppdater databaser -slutt

    //Returnerer data

    /* public Cursor getKontakter() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT brukernavn FROM " + TABLE_KONTAKTER;
        Cursor data = db.rawQuery(query,null);
        return data;
    } */

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

    public List<Møte> hentAlleMoter(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Møte> moteList = new ArrayList<Møte>();
        String selectQue = "SELECT * FROM " + TABLE_MOETER;
        Cursor cursor = db.rawQuery(selectQue, null);

        if(cursor.moveToFirst()){
            do {
                Møte møte = new Møte();
                møte.setMoete_ID(cursor.getInt(0));
                møte.setType(cursor.getString(1));
                møte.setSted(cursor.getString(2));
                try{
                    møte.setDato(cursor.getString(3));
                }catch(Exception e){
                    throw new IllegalArgumentException();
                }
                møte.setTid(cursor.getString(4));
                moteList.add(møte);
            } while(cursor.moveToNext()) ;
            cursor.close();
            db.close();
        }
        return moteList;
    }

    //Finner alle deltagere som hører til møte
    public ArrayList<Kontakt> finnDeltagere(int moete_id){
        ArrayList<Kontakt> deltagere = new ArrayList<>();
        ArrayList<Long> kontaktId = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQue = "SELECT * FROM " + TABLE_KONTAKTER + " WHERE " + KEY_KONTAKT_ID + " IN(SELECT " + KEY_KONTAKT_ID +
                " FROM " + TABLE_MOETEDELTAGELSE + " WHERE " + KEY_MOETE_ID + " = " + moete_id + ")"  ;
        Cursor cursor = db.rawQuery(selectQue, null);
                if (cursor.moveToFirst()) {
                    do {

                            Kontakt kontakt = new Kontakt();
                            kontakt.set_ID(cursor.getLong(0));
                            kontakt.setBrukernavn(cursor.getString(1));
                            kontakt.setNavn(cursor.getString(2));
                            kontakt.setTelefon(cursor.getString(3));
                            deltagere.add(kontakt);

                } while (cursor.moveToNext()) ;
                cursor.close();
            }

        return deltagere;

    }

    public ArrayList<Møte> hentMoeterIdag(String dato){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Møte> moteList = new ArrayList<Møte>();
        String selectQue = "SELECT * FROM " + TABLE_MOETER;
        Cursor cursor = db.rawQuery(selectQue, null);

        if(cursor.moveToFirst()){
            do {
                if(cursor.getString(3 ).equals(dato)){
                    Møte møte = new Møte();
                    møte.setMoete_ID(cursor.getInt(0));
                    møte.setType(cursor.getString(1));
                    møte.setSted(cursor.getString(2));
                    try{
                        møte.setDato(cursor.getString(3));
                    }catch(Exception e){
                        throw new IllegalArgumentException();
                    }
                    møte.setTid(cursor.getString(4));
                    moteList.add(møte);
                }
            } while(cursor.moveToNext()) ;
            cursor.close();
            db.close();
        }
        return moteList;
    }



    //Returnerer data - slutt

    //Tabeller
    public void createTableKontakter(SQLiteDatabase db) {
        String LAG_TABLE_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER
                + "(" + KEY_KONTAKT_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT NOT NULL UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        Log.d("SQL", LAG_TABLE_KONTAKTER);
        db.execSQL(LAG_TABLE_KONTAKTER);
    }

    public void createTableMøter(SQLiteDatabase db) {
        String LAG_TABLE_MOETER = "CREATE TABLE " + TABLE_MOETER
                + "(" + KEY_MOETE_ID + " INTEGER PRIMARY KEY,"
                + KEY_TYPE_MOETE + " TEXT,"
                + KEY_STED + " TEXT,"
                + KEY_DATO + " TEXT,"
                + KEY_TID + " TEXT" + ")";
        Log.d("SQL", LAG_TABLE_MOETER);
        db.execSQL(LAG_TABLE_MOETER);
    }


    public void createTableMøteDeltagelse(SQLiteDatabase db) {
        String LAG_TABLE_MOETEDELTAGELSE = "CREATE TABLE " + TABLE_MOETEDELTAGELSE
                + "(" + KEY_KONTAKT_ID + " INTEGER NOT NULL,"
                + KEY_MOETE_ID + " INTEGER NOT NULL" + ")";
        Log.d("SQL", LAG_TABLE_MOETEDELTAGELSE);
        db.execSQL(LAG_TABLE_MOETEDELTAGELSE);
    }



    //Tabeller slutt

    //Metoder som returner

    //Hjelpemetoder

    public boolean dataLagtTil(long result) {
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Hjelpemetoder slutt

} //DBHandler slutt
