package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;

import java.util.List;

@Dao
public interface HistoryPlaylistSongDao {
    @Query("SELECT * FROM history_playlist_song")
    List<HistoryPlaylistSong> getAll();

    @Insert
    void insertAll(HistoryPlaylistSong... songs);

    @Delete
    void delete(HistoryPlaylistSong songs);
}
