package com.example.bamusic;

import java.util.ArrayList;
import java.util.Arrays;

public class Song {

    private String title;
    private String author;
    private String length;
    private boolean isFavorite;

    public static ArrayList<Song> allSongs = new ArrayList<Song>(
            Arrays.asList(
                    new Song("Em của ngày hôm qua", "Sơn Tùng MTP", "3:03"),
                    new Song("Nắng ấm xa dần", "Sơn Tùng MTP", "3:08"),
                    new Song("Lạc trôi", "Sơn Tùng MTP", "4:16"),
                    new Song("Ánh nắng của anh", "Đức Phúc", "4:16"),
                    new Song("Nàng thơ", "Hoàng Dũng", "4:16"),
                    new Song("Đi về nhà", "Đen", "4:16"),
                    new Song("Hãy trao cho anh", "Sơn Tùng MTP", "4:16"),
                    new Song("Chiếc ố thần kì", "Chi Pu", "1 triệu năm"),
                    new Song("Ánh sao và bầu trời", "Không biết", "4:16"),
                    new Song("Bảo Anh đẹp trai", "Bảo Anh", "4:16")
            )
    );

    public Song(String title, String author, String length) {
        this(title, author, length, false);
    }

    public Song(String title, String author, String length, boolean isFavorite) {
        this.title = title;
        this.author = author;
        this.length = length;
        this.isFavorite = isFavorite;
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

    public static ArrayList<Song> getFavoriteSongs() {
        ArrayList<Song> favSongs = new ArrayList<>();
        for (Song song : allSongs)
            if (song.isFavorite())
                favSongs.add(song);
        return favSongs;
    }
}
