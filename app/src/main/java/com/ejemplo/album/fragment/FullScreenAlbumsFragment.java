package com.ejemplo.album.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejemplo.album.R;
import com.ejemplo.album.model.Album;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */

public class FullScreenAlbumsFragment extends Fragment {
    public static final String ALBUM_TITLE = "ALBUM_TITLE";
    public static final String ALBUM_URL = "ALBUM_TEXT";
    public static final String ALBUM_THUMBNAILURL = "ALBUM_THUMBNAILURL";

    Context context;



    private FragmentManager fragmentManager;

    public void setFragmentManager(android.support.v4.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.full_screen_album_fragment, container, false);
        Bundle aBundle = getArguments();

        String title =  aBundle.getString(ALBUM_TITLE );
        final String itemUrl = aBundle.getString(ALBUM_URL);
        String itemThumbnailUrl  =aBundle.getString(ALBUM_THUMBNAILURL);

        context= this.getContext();

        TextView textviewTitle = (TextView) fragmentView.findViewById(R.id.full_screen_album_title_textview);

        textviewTitle.setText(title);

        final ImageView albumIdImageView = (ImageView) fragmentView.findViewById(R.id.full_screen_album_image_view);



        Picasso.with(context)
                .load(itemThumbnailUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.errorloading)
                .into(albumIdImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(context)
                                .load(itemUrl)
                                .placeholder(albumIdImageView.getDrawable())
                                .into(albumIdImageView);
                    }
                    @Override
                    public void onError() {

                    }
                });

        return fragmentView;
    }
}
