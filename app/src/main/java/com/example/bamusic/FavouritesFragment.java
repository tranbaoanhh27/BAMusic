package com.example.bamusic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        RecyclerView songsRecyclerView = (RecyclerView) view.findViewById(R.id.fav_song_recyclerview);
        ArrayList<Song> favoriteSongs = Song.getFavoriteSongs();
        SongsAdapter songsAdapter = new SongsAdapter(favoriteSongs);
        songsRecyclerView.setAdapter(songsAdapter);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }
}