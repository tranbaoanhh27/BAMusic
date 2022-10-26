package com.example.bamusic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class Music {

    private static final String TAG = Music.class.getSimpleName();

    private String title;
    private String author;
    private int duration;   // in milliseconds
    private Bitmap image;
    private boolean isFavorite;

    private static final String LOCAL_MUSIC_FOLDER =
            Environment.DIRECTORY_MUSIC + File.separator + "BAMusic";

    private static ArrayList<Music> allMusics = new ArrayList<Music>();

    public Music(String title, String author, int duration, Bitmap image) {
        this(title, author, duration, null, false);
    }

    public Music(String title, String author, int duration, Bitmap image, boolean isFavorite) {
        Log.d(TAG, "Now create a Music object: " + title + " " + author +
                " " + duration + " " + image + " " + isFavorite);
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.image = image;
        this.isFavorite = isFavorite;
    }

    public Boolean isExists() {
        for (Music music : allMusics) {
            if (music.title.equals(this.title) && music.author.equals(this.author)
                && music.duration == this.duration)
                return true;
        }
        return false;
    }

    public static ArrayList<Music> getAllMusics() {
        return allMusics;
    }

    public static void updateLocalMusics() {
        allMusics.clear();
        Log.d(TAG, "Local Musics Folder:" + LOCAL_MUSIC_FOLDER);
        File localMusicsFolder = Environment.getExternalStoragePublicDirectory(LOCAL_MUSIC_FOLDER);
        if (!localMusicsFolder.exists()) {
            if (localMusicsFolder.mkdirs()) Log.d(TAG, "Created Local Musics Folder");
            else Log.d(TAG, "Failed to create Local Musics Folder");
        } else {
            File[] allMusicFiles = localMusicsFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".mp3");
                }
            });
            if (allMusicFiles != null) {
                for (File musicFile : allMusicFiles) {
                    String filePath = musicFile.getAbsolutePath();
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(filePath);

                    String songTitle, songAuthor, songDuration;
                    Bitmap image = null;

                    songTitle = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    songAuthor = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    songDuration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                    byte[] imageData = mediaMetadataRetriever.getEmbeddedPicture();
                    if (imageData != null) {
                        image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    } else
                        Log.d(TAG, "imageData is null");

                    Log.d(TAG, songTitle + " " + songAuthor + " " + songDuration);

                    Music newMusic = new Music(songTitle, songAuthor, Integer.parseInt(songDuration), image);
                    if (!newMusic.isExists())
                        allMusics.add(newMusic);
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int length) {
        this.duration = length;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Bitmap getImage() { return image; }

    public static ArrayList<Music> getFavoriteSongs() {
        ArrayList<Music> favMusics = new ArrayList<>();
        for (Music music : allMusics)
            if (music.isFavorite())
                favMusics.add(music);
        return favMusics;
    }
}
