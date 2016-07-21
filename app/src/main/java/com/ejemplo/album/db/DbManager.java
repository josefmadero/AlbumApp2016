package com.ejemplo.album.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Set;


/**
 * Copyright Hernan Carrozzo  and Guillermo Zalazar on 28/06/2016.
 */
public class DbManager extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "entregable_app_data.db";
    private static final Integer DATABASE_VERSION = 1;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(AlbumTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DbManager", String.format("Upgrade DB from %d to %d", oldVersion, newVersion));


        if (!isTableExists(db, AlbumTable.TABLE_NAME)) {
            db.execSQL(AlbumTable.TABLE_CREATE);
        }


    }

    public long insert(String tableName, ContentValues cv) throws Exception {
        try {
            return this.getWritableDatabase().insert(tableName, null, cv);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public long update(String tableName, ContentValues cv, ContentValues whereValues) throws Exception {

        try {
            String[] arrwhereArgs = new String[whereValues.size() ];

            Set<String> keySet = whereValues.keySet();
            String whereClause = "";
            String andString = "";
            Integer i=0;
            for (String key: keySet) {
                String value = whereValues.getAsString(key);
                whereClause = whereClause+ andString + key + " = ? ";
                arrwhereArgs[i]=value;
                andString = " AND ";
                i++;
            }

            long count = this.getWritableDatabase().update(tableName,cv,whereClause,arrwhereArgs  );
            return count ;
        } catch (Exception ex) {
            Log.d("DbManager update", "Error on addToSavedNews", ex);
            throw ex;
        }
    }


    public boolean delete(String tableName, String idColumnName, Object idValue) throws Exception {
        try {
            int count = this.getWritableDatabase().delete(
                    tableName,
                    idColumnName + " = ?",
                    new String[]{String.valueOf(idValue)}
            );

            return (count > 0);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Cursor query(String query) throws Exception {
        try {
            return this.getWritableDatabase().rawQuery(query, null);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void close(){
        super.close();
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});

        if (cursor.getCount() == 0) {
            return false;
        }
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

}
