package com.example.bamusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicsAdapter extends RecyclerView.Adapter<MusicsAdapter.ViewHolder> {

    private ArrayList<Music> musics;

    public MusicsAdapter(ArrayList<Music> musics) {
        this.musics = musics;
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
    public MusicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View songView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_song, parent, false);
        return new ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicsAdapter.ViewHolder holder, int position) {
        Music music = this.musics.get(position);

        if (music != null) {
            if (music.getImage() != null) holder.songImage.setImageBitmap(music.getImage());
            holder.songTitle.setText(music.getTitle());
            holder.songAuthor.setText(music.getAuthor());
            holder.songLength.setText(Utilities.milisecToMinute(String.valueOf(music.getDuration())));
            holder.likeButton.setImageResource(music.isFavorite() ? R.drawable.liked_heart_vector : R.drawable.empty_heart_vector);

            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (music.isFavorite()) {
                        music.setFavorite(false);
                        holder.likeButton.setImageResource(R.drawable.empty_heart_vector);
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.removed_from_fav), Toast.LENGTH_SHORT).show();
                    } else {
                        music.setFavorite(true);
                        holder.likeButton.setImageResource(R.drawable.liked_heart_vector);
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.added_to_fav), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.songItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Clicked on the music", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
}
