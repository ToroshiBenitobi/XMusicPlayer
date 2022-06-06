package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import org.annatv.musicplayer.entity.activitylist.FavouritePlaylistSong;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;

import java.util.List;

@Dao
public interface FavouritePlaylistSongDao {
    @Query("SELECT * FROM favourite_playlist_song")
    List<FavouritePlaylistSong> getAll();

    @Query("SELECT * FROM favourite_playlist_song WHERE song_id=:songId")
    FavouritePlaylistSong getBySongId(long songId);

    @Insert
    void insertAll(FavouritePlaylistSong... songs);

    @Insert
    void insert(FavouritePlaylistSong song);

    @Query("DELETE FROM favourite_playlist_song WHERE song_id=:songId")
    void deleteBySongId(long songId);

    @Query("DELETE FROM favourite_playlist_song")
    void clear();
}
