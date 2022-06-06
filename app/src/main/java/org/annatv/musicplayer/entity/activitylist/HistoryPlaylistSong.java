package org.annatv.musicplayer.entity.activitylist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.annatv.musicplayer.entity.AbsPlaylistSong;

@Entity(tableName = "history_playlist_song")
public class HistoryPlaylistSong implements AbsPlaylistSong {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "song_id")
    public long songId;

    public HistoryPlaylistSong(long songId) {
        this.songId = songId;
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
