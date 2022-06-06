package org.annatv.musicplayer.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlaylistSong implements AbsPlaylistSong {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "song_id")
    public long songId;
    @ColumnInfo(name = "playlist_id")
    public int playlistId;

    public PlaylistSong(int playlistId, long songId) {
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}
