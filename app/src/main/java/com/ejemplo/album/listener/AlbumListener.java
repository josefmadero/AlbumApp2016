package com.ejemplo.album.listener;

import com.ejemplo.album.model.Album;

/**
 * Copyright Guillermo M. Zalazar  on 25/06/2016.
 */

public interface AlbumListener{
    void onAlbumSelected(String title,String url, String thumbnailUrl );
}
