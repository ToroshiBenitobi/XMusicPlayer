package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylist;
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

    @Query("SELECT * FROM history_playlist_song WHERE song_id=:id")
    HistoryPlaylistSong getBySongId(int id);

    @Query("DELETE FROM history_playlist_song WHERE song_id = :songId")
    void deleteBySongId(long songId);
}
