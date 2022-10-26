package com.example.bamusic;

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
    private String length;
    private boolean isFavorite;

    private static final String LOCAL_MUSIC_FOLDER =
            Environment.DIRECTORY_MUSIC + File.separator + "BAMusic";

    private static ArrayList<Music> allMusics = new ArrayList<Music>(
            Arrays.asList(
                    new Music("Em của ngày hôm qua", "Sơn Tùng MTP", "3:03"),
                    new Music("Nắng ấm xa dần", "Sơn Tùng MTP", "3:08"),
                    new Music("Lạc trôi", "Sơn Tùng MTP", "4:16"),
                    new Music("Ánh nắng của anh", "Đức Phúc", "4:16"),
                    new Music("Nàng thơ", "Hoàng Dũng", "4:16"),
                    new Music("Đi về nhà", "Đen", "4:16"),
                    new Music("Hãy trao cho anh", "Sơn Tùng MTP", "4:16"),
                    new Music("Chiếc ố thần kì", "Chi Pu", "1 triệu năm"),
                    new Music("Ánh sao và bầu trời", "Không biết", "4:16"),
                    new Music("Bảo Anh đẹp trai", "Bảo Anh", "4:16")
            )
    );

    public Music(String title, String author, String length) {
        this(title, author, length, false);
    }

    public Music(String title, String author, String length, boolean isFavorite) {
        this.title = title;
        this.author = author;
        this.length = length;
        this.isFavorite = isFavorite;
    }

    public static ArrayList<Music> getAllMusics() {
        return allMusics;
    }

    public static void updateLocalMusics() {
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
                    Log.d(TAG, musicFile.getName());
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
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static ArrayList<Music> getFavoriteSongs() {
        ArrayList<Music> favMusics = new ArrayList<>();
        for (Music music : allMusics)
            if (music.isFavorite())
                favMusics.add(music);
        return favMusics;
    }
}
