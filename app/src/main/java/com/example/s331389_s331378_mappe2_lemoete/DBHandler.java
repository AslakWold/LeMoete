package com.example.s331389_s331378_mappe2_lemoete;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "_ID"; //Eller skal vi bruke et username?
    static String KEY_NAME = "navn";
    static String KEY_PH_NO = "telefon";

    static String TABLE_MØTER = "Møter";
    static String STED = "sted";
    static String TYPE_MØTE = "type";
    //tidspunkt
    //ID-felt
    //Oversikt over kontakter i møte

    //Møtedeltagelse
    //ikke sikker på hvilke felter vi trenger.

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "MøteDatabase";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
