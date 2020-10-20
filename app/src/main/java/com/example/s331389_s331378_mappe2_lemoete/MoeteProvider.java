package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MoeteProvider extends ContentProvider {
    static String TABLE_MOETER = "Moeter";
    static String KEY_MOETE_ID = "Moete_ID";
    public final static String PROVIDER = "com.example.moeter";
    private static final int MOETE = 1;
    private static final int MMOETE = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/moete");
    private static final UriMatcher uriMatcher;
    DBHandler dbHelper;
    SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "moete", MMOETE);
        uriMatcher.addURI(PROVIDER, "moete/#", MOETE);
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
        if(uriMatcher.match(uri)== MOETE){
            cursor = db.query(TABLE_MOETER, strings, KEY_MOETE_ID + " = " + uri.getPathSegments().get(1),
                    strings1, null, null, s1);
        }else{
            cursor = db.query(TABLE_MOETER,null,null,null,null,null,null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MMOETE:
                return "vnd.android.cursor.dir/vnd.example.moete";
            case MOETE:
                return "vnd.android.cursor.item/vnd.example.moete";
            default:
                throw new IllegalArgumentException("Ugyldig URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase DB = dbHelper.getWritableDatabase();
        db.insert(TABLE_MOETER, null, contentValues);
        Cursor cursor = db.query(TABLE_MOETER, null,null,null,null,null,null);
        cursor.moveToLast();
        int id = cursor.getInt(0);
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)== MOETE){
            db.delete(TABLE_MOETER, KEY_MOETE_ID +  " = " + uri.getPathSegments().get(1), strings);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MMOETE){
            db.delete(TABLE_MOETER, null,null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)== MOETE){
            db.update(TABLE_MOETER,contentValues, KEY_MOETE_ID +  " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MMOETE){
            db.update(TABLE_MOETER, null,null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

}
