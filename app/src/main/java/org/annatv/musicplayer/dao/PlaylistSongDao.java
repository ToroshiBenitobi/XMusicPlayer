package org.annatv.musicplayer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.PlaylistSong;
import org.annatv.musicplayer.entity.Song;

import java.util.List;

@Dao
public interface PlaylistSongDao {
    @Query("SELECT * FROM playlistsong WHERE playlist_id = :playlistId")
    List<PlaylistSong> loadAllByPlaylistId(int playlistId);

    @Insert
    void insertAll(PlaylistSong... PlaylistSong);

    @Delete
    void delete(PlaylistSong PlaylistSong);
}
