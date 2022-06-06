package org.annatv.musicplayer.entity.activitylist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.annatv.musicplayer.entity.AbsPlaylistSong;

@Entity(tableName = "top_playlist_song")
public class TopPlaylistSong implements AbsPlaylistSong {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "song_id")
    public long songId;
    @ColumnInfo(name = "plays")
    public int plays;

    public TopPlaylistSong(long songId, int plays) {
        this.songId = songId;
        this.plays = plays;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
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
}
