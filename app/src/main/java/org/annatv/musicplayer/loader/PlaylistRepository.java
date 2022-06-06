package org.annatv.musicplayer.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
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

    public void insertPlaylists(Playlist... playlists) {
        Executors.newSingleThreadExecutor().execute(() -> {
            playlistDao.insertAll(playlists);
        });
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//        executor.execute(() -> {
//            //Background work here
//            handler.post(() -> {
//                //UI Thread work here
//            });
//        });
    }

}
