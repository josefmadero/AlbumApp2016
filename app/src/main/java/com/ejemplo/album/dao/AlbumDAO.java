package com.ejemplo.album.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ejemplo.album.db.AlbumTable;
import com.ejemplo.album.db.DbManager;
import com.ejemplo.album.listener.ResultListener;
import com.ejemplo.album.model.Album;
import com.ejemplo.album.task.RetrieveAlbumTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */
public class AlbumDAO {

    private final String TAG = "AlbumDAO";

    public void getAllAlbums(Context context, final ResultListener<List<Album>> listener) {

        RetrieveAlbumTask retrieveAlbumTask = new RetrieveAlbumTask(context,listener);
        retrieveAlbumTask.execute();
    }

    public boolean addOfflineAlbum(Context context,Integer id,Integer albumId, String title, String url, String thumbnailUrl)
    {
        DbManager dbManager = null;
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(AlbumTable.COLUMN_ALBUM_ID, id  );
            contentValues.put(AlbumTable.COLUMN_ALBUM_ALBUMID, albumId  );
            contentValues.put(AlbumTable.COLUMN_ALBUM_TITLE, title );
            contentValues.put(AlbumTable.COLUMN_ALBUM_URL, url );
            contentValues.put(AlbumTable.COLUMN_ALBUM_THUMBNAILURL, thumbnailUrl );

            Album news = this.getOfflineAlbumById(context,id);
            dbManager = new DbManager(context);

            if (news == null) {
                    dbManager.insert(AlbumTable.TABLE_NAME, contentValues);
            } else {
                    ContentValues whereValues = new ContentValues();
                    whereValues.put(AlbumTable.COLUMN_ALBUM_ID, id);
                    dbManager.update(AlbumTable.TABLE_NAME, contentValues, whereValues);
            }
            dbManager.close();
            return true;
        } catch (Exception ex) {
            String a;
            a = ex.toString();
            Log.e(TAG, "Error on addOfflineAlbums", ex);
            if (dbManager!=null) dbManager.close();
            return false;
        }
    }


    public List<Album> getOfflineAlbums(Context context)  {

        List<Album> offlineAlbumList = new ArrayList<>();
        Cursor cursor = null;
        DbManager dbManager = null;

        try {
            dbManager = new DbManager(context);
            cursor=dbManager.query("SELECT * FROM " + AlbumTable.TABLE_NAME);

        } catch (Exception ex) {
            Log.e(TAG, "Error on getOfflineNews", ex);
            if (dbManager!=null) dbManager.close();
        }

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_ID));
                Integer albumId = cursor.getInt(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_ALBUMID));
                String title = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_TITLE));
                String url = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_URL));
                String thumbnailurl = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_THUMBNAILURL));

                offlineAlbumList.add ( new Album(id,albumId,title,url,thumbnailurl));
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbManager.close();
        return offlineAlbumList;
    }






    public Album getOfflineAlbumById(Context context, Integer id)  {

        Album album = null;
        Cursor cursor = null;
        DbManager dbManager = null;

        try {
            dbManager = new DbManager(context);
            cursor = dbManager.query("SELECT * FROM " + AlbumTable.TABLE_NAME + " Where "+AlbumTable.COLUMN_ALBUM_ID+" ='"+id+"' ");
        } catch (Exception ex) {
            Log.e(TAG, "Error on getOfflineNews", ex);
            if (dbManager!=null) dbManager.close();
        }

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                    id              = cursor.getInt(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_ID));
                    Integer albumId = cursor.getInt(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_ALBUMID));
                    String title    = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_TITLE));
                    String url      = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_URL));
                    String thumbnailurl = cursor.getString(cursor.getColumnIndex(AlbumTable.COLUMN_ALBUM_THUMBNAILURL));
                    album = new Album(id, albumId, title, url, thumbnailurl);
            }
        }
        else
        {
            album = null;
        }
        cursor.close();
        dbManager.close();
        return album ;
    }
}

