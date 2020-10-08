package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "_ID";
    static String KEY_USER_NAME = "brukernavn";
    static String KEY_NAME = "navn";
    static String KEY_PH_NO = "telefon";

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

    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, kontakt.getBrukernavn());
        values.put(KEY_NAME, kontakt.getNavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

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

} //DBHandler slutt
