package com.ejemplo.album.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import com.ejemplo.album.listener.ResultListener;
import com.ejemplo.album.model.Album;
import com.ejemplo.album.model.AlbumContainer;
import com.google.gson.Gson;

import java.util.List;

import util.DAOException;
import util.HTTPConnectionManager;


/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */


public class RetrieveAlbumTask extends AsyncTask<String, String, List<Album>> {

    private ResultListener<List<Album>> listener;

    private Context context;

    public RetrieveAlbumTask(Context context,ResultListener<List<Album>> listener) {
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected List<Album> doInBackground(String... params) {

        HTTPConnectionManager connectionManager = new HTTPConnectionManager();
        String input = null;

        try {
            input = connectionManager.getRequestString("https://api.myjson.com/bins/25Hip");
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        AlbumContainer postContainer = gson.fromJson(input, AlbumContainer.class);

        return postContainer.getAlbums();
    }



    @Override
    protected void onPostExecute(List<Album> productList) {

        this.listener.finish(productList);
    }



    @Override
    protected void onPreExecute() {


    }


}



