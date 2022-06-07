package org.annatv.musicplayer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import org.annatv.musicplayer.entity.Playlist;

import java.util.List;

@Dao
public abstract class PlaylistDao {
    @Query("SELECT * FROM playlist")
    public abstract LiveData<List<Playlist>> getAll();

    @Query("SELECT * FROM playlist WHERE pid = :pid")
    public abstract Playlist loadByIds(int pid);

    @Query("SELECT * FROM playlist WHERE pid = :name")
    public abstract Playlist loadByName(String name);

    @Query("SELECT * FROM playlist WHERE name LIKE '%' || :name || '%' ESCAPE '$'")
    public abstract List<Playlist> getByNameLike(String name);

    @Insert
    public abstract void insertAll(Playlist... playlists);

    @Query("DELETE FROM playlist WHERE pid = :pid")
    public abstract void delete(int pid);

    @Query("DELETE FROM playlistsong WHERE playlist_id = :pid")
    public abstract void clear(int pid);

    @Transaction
    public void safeDelete(int pid) {
        delete(pid);
        clear(pid);
    }
}
