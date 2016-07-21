package com.ejemplo.album.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejemplo.album.R;
import com.ejemplo.album.listener.AlbumListener;
import com.ejemplo.album.model.Album;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright Guillermo M. Zalazar  on 28/06/2016.
 */

public class AlbumAdapter extends RecyclerView.Adapter implements AlbumListener {
    private List<Album> albumList;
    private Context context;
    private AlbumListener albumListener;


    @Override
    public void onAlbumSelected(String title,String url, String thumbnailUrl) {
        if (this.albumListener != null) {


            this.albumListener.onAlbumSelected(title,url,thumbnailUrl);
        }
    }


    public AlbumAdapter(Context context, List<Album> listAlbum) {
        this.context = context;
        this.albumList = listAlbum;
    }

    public void setActivity(Activity activity) {
        this.albumListener = (AlbumListener) activity;
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(itemView);
        albumViewHolder.setListener(this);

        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Album album = albumList.get(position);
        AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
        albumViewHolder.bindAlbum(album,context);
    }

    private static class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textviewTitle;
        //private int idImage;
        private ImageView albumIdImageView ;
        private String itemUrl;
        private String itemThumbnailUrl;

        Context context;

        private AlbumListener listener;

        public void setListener(AlbumListener listener) {
            this.listener = listener;
        }

        public void onClick(View view) {

            String title = textviewTitle.getText().toString();

            if (this.listener != null) {
                this.listener.onAlbumSelected(title,itemUrl,itemThumbnailUrl);
            }

        }

        public AlbumViewHolder(View itemView) {
            super(itemView);

            textviewTitle    = (TextView) itemView.findViewById(R.id.item_recycler_view_textview_album_title);
            albumIdImageView = (ImageView) itemView.findViewById(R.id.item_recycler_view_album_image_view);
            itemView.setOnClickListener(this);
        }

        public void bindAlbum(Album album,Context contextIn) {



            textviewTitle.setText(album.getTitle());
            itemUrl = album.getUrl();
            itemThumbnailUrl= album.getThumbnailUrl();
            this.context = contextIn;


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

        }
    }
}

