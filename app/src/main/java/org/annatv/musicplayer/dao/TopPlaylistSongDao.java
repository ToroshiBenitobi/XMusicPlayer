package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;
import org.annatv.musicplayer.entity.activitylist.TopPlaylistSong;

import java.util.List;

@Dao
public interface TopPlaylistSongDao {
    @Query("SELECT * FROM top_playlist_song")
    List<TopPlaylistSong> getAll();

    @Insert
    void insertAll(TopPlaylistSong... songs);

    @Delete
    void delete(TopPlaylistSong song);
}
