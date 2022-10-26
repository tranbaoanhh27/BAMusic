package com.example.bamusic;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AllMusicsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView songsRecyclerView;
    private MusicsAdapter musicsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);

        LinearLayout reloadMusicContainer = (LinearLayout) view.findViewById(R.id.container_reload_musics);
        reloadMusicContainer.setOnClickListener(this);

        songsRecyclerView = (RecyclerView) view.findViewById(R.id.all_songs_recyclerview);
        musicsAdapter = new MusicsAdapter(Music.getAllMusics());
        songsRecyclerView.setAdapter(musicsAdapter);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.container_reload_musics) {
            Music.updateLocalMusics();
            musicsAdapter.notifyDataSetChanged();
            Utilities.showToast(getContext(), "All local songs reloaded", Toast.LENGTH_SHORT);
        }
    }
}