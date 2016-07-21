package com.ejemplo.album.controller.Album;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ejemplo.album.dao.AlbumDAO;
import com.ejemplo.album.listener.ResultListener;
import com.ejemplo.album.model.Album;

import java.util.List;

/**
 * Copyright Guillermo M. Zalazar on 28/06/2016.
 */
public class AlbumController {


    private ResultListener<List<Album>> listener;
    Context context;


    public void getAlbums(Context contextIn, ResultListener<List<Album>> resultListenerFromView)
    {


        this.listener = resultListenerFromView;
        this.context = contextIn;



        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context. CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = cm.getActiveNetworkInfo();
        Boolean isOnline = (activeConnection != null) && activeConnection.isConnected();

        if (isOnline == true)
        {

            AlbumDAO newsDAO = new AlbumDAO();

            newsDAO.getAllAlbums(  context, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> result)
                {
                    for (Album album:result)
                    {
                        if (null == getOfflineAlbumById(context,album.getId()))
                        {
                            addOfflineAlbum(context,album);
                        }
                    }
                    listener.finish(result);
                }
            });
        }
        else
        {
            List<Album> result = this.getOfflineAlbums(context);
            this.listener.finish(result);
        }



    }


    public List<Album> getOfflineAlbums(Context context) {
        AlbumDAO albumDAO = new AlbumDAO();
        return albumDAO.getOfflineAlbums(context);
    }

    public boolean addOfflineAlbum(Context context, Album album) {
        AlbumDAO albumDAO = new AlbumDAO();

        return albumDAO.addOfflineAlbum(context,
                album.getId(),
                album.getAlbumId(),
                album.getTitle(),
                album.getUrl(),
                album.getThumbnailUrl()
        );
    }



    public Album getOfflineAlbumById(Context context, Integer id) {
        AlbumDAO albumsDAO = new AlbumDAO();
        return albumsDAO.getOfflineAlbumById(context, id);
    }


}
