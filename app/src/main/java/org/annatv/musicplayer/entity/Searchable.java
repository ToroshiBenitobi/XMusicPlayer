package org.annatv.musicplayer.entity;

public interface Searchable {
    public static final int SONG = 0;
    public static final int ALBUM = 1;
    public static final int ARTIST = 2;
    public static final int PLAYLIST = 3;

    String getName();

    int getMenuRes();

    int getCategory();
}
