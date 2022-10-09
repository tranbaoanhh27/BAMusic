package com.example.bamusic;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private ArrayList<Song> songs;

    public SongsAdapter(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView songImage;
        public TextView songTitle;
        public TextView songAuthor;
        public TextView songLength;
        public ImageButton likeButton;
        public ConstraintLayout songItemContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            songImage = (CircleImageView) itemView.findViewById(R.id.song_item_image);
            songTitle = (TextView) itemView.findViewById(R.id.song_item_title);
            songAuthor = (TextView) itemView.findViewById(R.id.song_item_author);
            songLength = (TextView) itemView.findViewById(R.id.song_item_length);
            likeButton = (ImageButton) itemView.findViewById(R.id.song_item_like_button);
            songItemContainer = (ConstraintLayout) itemView.findViewById(R.id.song_item_container);
        }
    }

    @NonNull
    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View songView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_song, parent, false);
        return new ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.ViewHolder holder, int position) {
        Song song = songs.get(position);

        if (song != null) {
            holder.songTitle.setText(song.getTitle());
            holder.songAuthor.setText(song.getAuthor());
            holder.songLength.setText(song.getLength());
            holder.likeButton.setImageResource(song.isFavorite() ? R.drawable.liked_heart_vector : R.drawable.empty_heart_vector);

            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (song.isFavorite()) {
                        song.setFavorite(false);
                        holder.likeButton.setImageResource(R.drawable.empty_heart_vector);
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.removed_from_fav), Toast.LENGTH_SHORT).show();
                    } else {
                        song.setFavorite(true);
                        holder.likeButton.setImageResource(R.drawable.liked_heart_vector);
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.added_to_fav), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.songItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Clicked on the song", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
