package com.example.bamusic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class Music {

    private static final String TAG = Music.class.getSimpleName();

    private File file;
    private boolean isFavorite;
    private String title, artist;
    private int durationMilliseconds;
    private Bitmap image;

    private static final String LOCAL_MUSIC_FOLDER =
            Environment.DIRECTORY_MUSIC + File.separator + "BAMusic";

    private static ArrayList<Music> allMusics = new ArrayList<>();

    public Music(File file) {
        this.file = file;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(file.getAbsolutePath());

        isFavorite = false;
        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        durationMilliseconds = Integer.parseInt(
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        byte[] data = retriever.getEmbeddedPicture();
        if (data != null) {
            image = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else
            image = null;
    }

    public Boolean isExists() {
        for (Music music : allMusics) {
            if (music.getFilePath().equals(this.getFilePath()))
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
            File[] allMusicFiles = localMusicsFolder.listFiles((dir, name)
                    -> name.toLowerCase().endsWith(".mp3"));
            if (allMusicFiles != null) {
                for (File musicFile : allMusicFiles) {
                    Music newMusic = new Music(musicFile);
                    if (!newMusic.isExists())
                        allMusics.add(newMusic);
                }
            }
        }
    }

    public File getFile() {
        return file;
    }

    public String getFilePath() {
        return file.getAbsolutePath();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDurationMilliseconds() {
        return durationMilliseconds;
    }

    public Bitmap getImage() {
        return image;
    }

    public static ArrayList<Music> getFavoriteSongs() {
        ArrayList<Music> favMusics = new ArrayList<>();
        for (Music music : allMusics)
            if (music.isFavorite())
                favMusics.add(music);
        return favMusics;
    }
}
