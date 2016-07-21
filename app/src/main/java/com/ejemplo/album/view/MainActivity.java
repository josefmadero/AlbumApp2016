package com.ejemplo.album.view;

/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */

import android.content.Context;
import android.net.Uri;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ejemplo.album.R;

import com.ejemplo.album.controller.Album.AlbumController;
import com.ejemplo.album.fragment.AlbumListFragment;
import com.ejemplo.album.fragment.FullScreenAlbumsFragment;
import com.ejemplo.album.listener.AlbumListener;
import com.ejemplo.album.model.Album;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements AlbumListener {

    FragmentTransaction fragmentTransaction;
    Context context;
    private GoogleApiClient client;


    @Override
    public void onBackPressed() {

        Integer stackSize = getSupportFragmentManager().getBackStackEntryCount();

        getSupportFragmentManager().popBackStack();

        if (stackSize < 2) super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        AlbumListFragment aFragment = new AlbumListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_place, aFragment);

        fragmentTransaction.addToBackStack("AlbumsListFragment");  // para el backbutton

        fragmentTransaction.commit();

    }

    @Override
    public void onAlbumSelected(String title ,String url, String thumbnailUrl ) {

        FragmentManager fragmentManager;

        AlbumController albumController = new AlbumController();

        Bundle aDataBundle = new Bundle();

        aDataBundle.putString(FullScreenAlbumsFragment.ALBUM_TITLE, title);
        aDataBundle.putString(FullScreenAlbumsFragment.ALBUM_URL, url);
        aDataBundle.putString(FullScreenAlbumsFragment.ALBUM_THUMBNAILURL, thumbnailUrl);

        FullScreenAlbumsFragment aFragment = new FullScreenAlbumsFragment();

        aFragment.setArguments(aDataBundle);

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_place, aFragment);

        fragmentTransaction.addToBackStack("FullScreenAlbumsFragment");

        fragmentTransaction.commit();

    }


}











