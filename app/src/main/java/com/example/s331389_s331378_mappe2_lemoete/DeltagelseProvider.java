package com.example.s331389_s331378_mappe2_lemoete;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DeltagelseProvider extends ContentProvider {
    static String TABLE_MOETEDELTAGELSE = "Moetedeltagelse";
    static String KEY_MOETE_ID = "Moete_ID";
    public final static String PROVIDER = "com.example.moetedeltagelser";
    private static final int MOETEDELTAGELSE = 1;
    private static final int MMOETEDELTAGELSE = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/moetedeltagelser");
    private static final UriMatcher uriMatcher;
    DBHandler dbHelper;
    SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "moete", MMOETEDELTAGELSE);
        uriMatcher.addURI(PROVIDER, "moete/#", MOETEDELTAGELSE);
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
        if(uriMatcher.match(uri)== MOETEDELTAGELSE){
            cursor = db.query(TABLE_MOETEDELTAGELSE, strings, KEY_MOETE_ID + " = " + uri.getPathSegments().get(1),
                    strings1, null, null, s1);
        }else{
            cursor = db.query(TABLE_MOETEDELTAGELSE,null,null,null,null,null,null);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case MMOETEDELTAGELSE:
                return "vnd.android.cursor.dir/vnd.example.moetedeltagelse";
            case MOETEDELTAGELSE:
                return "vnd.android.cursor.item/vnd.example.moetedeltagelse";
            default:
                throw new IllegalArgumentException("Ugyldig URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase DB = dbHelper.getWritableDatabase();
        db.insert(TABLE_MOETEDELTAGELSE, null, contentValues);
        Cursor cursor = db.query(TABLE_MOETEDELTAGELSE, null,null,null,null,null,null);
        cursor.moveToLast();
        int id = cursor.getInt(1);
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)== MOETEDELTAGELSE){
            db.delete(TABLE_MOETEDELTAGELSE, KEY_MOETE_ID +  " = " + uri.getPathSegments().get(1), strings);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MMOETEDELTAGELSE){
            db.delete(TABLE_MOETEDELTAGELSE, null,null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        if(uriMatcher.match(uri)== MOETEDELTAGELSE){
            db.update(TABLE_MOETEDELTAGELSE,contentValues, KEY_MOETE_ID +  " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if(uriMatcher.match(uri) == MMOETEDELTAGELSE){
            db.update(TABLE_MOETEDELTAGELSE, null,null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }
        return 0;
    }

}
