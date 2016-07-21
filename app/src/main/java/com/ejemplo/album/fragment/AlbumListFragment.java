package com.ejemplo.album.fragment;

import android.content.Context;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ejemplo.album.R;
import com.ejemplo.album.adapter.AlbumAdapter;
import com.ejemplo.album.controller.Album.AlbumController;
import com.ejemplo.album.listener.ResultListener;
import com.ejemplo.album.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */

public class AlbumListFragment extends Fragment {

    public static final String RSS_TITLE = "RSS_TITLE";
    public static final String RSS_FEED_ID = "RSS_FEED_ID";
    public static final String RSS_CATEGORY= "RSS_CATEGORY";


    Context context;
    List<Album> albums;
    AlbumController albumController;
    AlbumAdapter adapterAlbum;
    SwipeRefreshLayout swipeContainer;

    private FragmentManager fragmentManager;

    public void setFragmentManager(android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View fragmentView = inflater.inflate(R.layout.albums_recyclerview_fragment, container, false);

        albumController= new AlbumController();

        albums = new ArrayList<Album>();
        context= this.getContext();

        RecyclerView recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_principal);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

        adapterAlbum = new AlbumAdapter(context, albums);

        adapterAlbum.setActivity(this.getActivity());

        recyclerView.setAdapter(adapterAlbum);




        albumController.getAlbums(  context, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result)
            {
                albums.clear();
                albums.addAll(result);
                adapterAlbum.notifyDataSetChanged();
            }
        });



        swipeContainer = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override   public void onRefresh()
            {

                albumController.getAlbums(  context, new ResultListener<List<Album>>() {
                    @Override
                    public void finish(List<Album> result)
                    {
                        albums.clear();
                        albums.addAll(result);
                        adapterAlbum.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);
                    }
                });

            }
        });
        return fragmentView;
    }



}
