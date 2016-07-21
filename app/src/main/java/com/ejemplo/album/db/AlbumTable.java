package com.ejemplo.album.db;


/**
 * Copyright Guillermo M. Zalazar  on 25/06/2016.
 */
public class AlbumTable {

    public static final String TABLE_NAME = "Album";

    public static final String COLUMN_ALBUM_ID = "id";
    public static final String COLUMN_ALBUM_ALBUMID = "albumId";
    public static final String COLUMN_ALBUM_TITLE = "title";
    public static final String COLUMN_ALBUM_URL = "url";
    public static final String COLUMN_ALBUM_THUMBNAILURL = "thumbnailUrl";


    public static final String TABLE_CREATE = " CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " ( "
            + COLUMN_ALBUM_ID  + " INTEGER PRIMARY KEY  , "
            + COLUMN_ALBUM_ALBUMID + " INTEGER, "
            + COLUMN_ALBUM_TITLE + " STRING, "
            + COLUMN_ALBUM_URL + " STRING , "
            + COLUMN_ALBUM_THUMBNAILURL  + " STRING "
            + " );";


}

