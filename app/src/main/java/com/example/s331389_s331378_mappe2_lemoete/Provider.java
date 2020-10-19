package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Provider extends ContentProvider {
    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_KONTAKT_ID = "Kontakt_ID";
    static String KEY_USER_NAME = "brukernavn";
    static String KEY_NAME = "navn";
    static String KEY_PH_NO = "telefon";
    private static final int DB_VERSION = 1;
    public final static String PROVIDER = "com.example.s331389_s331378_mappe2_lemoete";
    private static final int KONTAKT = 1;
    private static final int MKONTAKT = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/kontakt");
    private static final UriMatcher uriMatcher;
    DBHandler dbHelper;
    SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "kontakt", MKONTAKT);
        uriMatcher.addURI(PROVIDER, "kontakt/#",KONTAKT);
    }




    @Override
    public boolean onCreate() {
        dbHelper = new DBHandler(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = null;
        if(uriMatcher.match(uri)==KONTAKT){
            cursor = db.query(TABLE_KONTAKTER, strings, KEY_KONTAKT_ID + " = " + uri.getPathSegments().get(1),
                    strings1, null, null, s1);
        }else{
            cursor = db.query(TABLE_KONTAKTER,null,null,null,null,null,null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MKONTAKT:
                return "vnd.android.cursor.dir/vnd.example.kontakt";
            case KONTAKT:
                return "vnd.android.cursor.item/vnd.example.kontakt";
            default:
                throw new IllegalArgumentException("Ugyldig URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase DB = dbHelper.getWritableDatabase();
        db.insert(TABLE_KONTAKTER, null, contentValues);
        Cursor cursor = db.query(TABLE_KONTAKTER, null,null,null,null,null,null);
        cursor.moveToLast();
        long id = cursor.getLong(0);
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)==KONTAKT){
            db.delete(TABLE_KONTAKTER, KEY_KONTAKT_ID +  " = " + uri.getPathSegments().get(1), strings);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MKONTAKT){
            db.delete(TABLE_KONTAKTER, null,null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)==KONTAKT){
            db.update(TABLE_KONTAKTER,contentValues, KEY_KONTAKT_ID +  " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MKONTAKT){
            db.update(TABLE_KONTAKTER, null,null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }
}
