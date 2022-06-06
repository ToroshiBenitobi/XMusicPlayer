package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;
import org.annatv.musicplayer.entity.activitylist.TopPlaylistSong;

import java.util.List;

@Dao
public interface TopPlaylistSongDao {
    @Query("SELECT * FROM top_playlist_song ORDER BY plays DESC")
    List<TopPlaylistSong> getAll();

    @Query("SELECT * FROM top_playlist_song WHERE song_id = :songId")
    TopPlaylistSong getBySongId(long songId);

    @Insert
    void insert(TopPlaylistSong song);

    @Query("SELECT * FROM top_playlist_song WHERE song_id=:id")
    TopPlaylistSong getBySongId(int id);

    @Query("DELETE FROM top_playlist_song WHERE song_id=:songId")
    void deleteBySongId(long songId);

    @Query("DELETE FROM top_playlist_song")
    void clear();

    @Update
    void update(TopPlaylistSong song);
}
