package org.annatv.musicplayer.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.dao.PlaylistDao;
import org.annatv.musicplayer.database.AppDatabase;
import org.annatv.musicplayer.entity.Playlist;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlaylistRepository {
    private LiveData<List<Playlist>> playlists;
    private PlaylistDao playlistDao;

    public PlaylistRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        playlistDao = appDatabase.playlistDao();
        this.playlists = playlistDao.getAll();
    }

    public LiveData<List<Playlist>> getPlaylists() {
        return playlists;
    }

    public Playlist getPlaylist(int pid) {
        switch (pid) {
            case PlaylistSongRepository.HISTORY_PLAYLIST:
                return new Playlist(PlaylistSongRepository.HISTORY_PLAYLIST, "Recent played");
            case PlaylistSongRepository.TOP_PLAYLIST:
                return new Playlist(PlaylistSongRepository.TOP_PLAYLIST, "Top played");
            case PlaylistSongRepository.FAVOURITE_PLAYLIST:
                return new Playlist(PlaylistSongRepository.FAVOURITE_PLAYLIST, "My favourite");
            default:
                return playlistDao.loadByIds(pid);
        }
    }

    public void deletePlaylistByIdAsync(int playlistId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            playlistDao.safeDelete(playlistId);
        });
    }


    public void insertPlaylistsAsync(Playlist... playlists) {
        Executors.newSingleThreadExecutor().execute(() -> {
            playlistDao.insertAll(playlists);
        });
    }

}
