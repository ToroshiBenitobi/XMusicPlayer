package org.annatv.musicplayer.database;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import org.annatv.musicplayer.dao.PlaylistDao;
import org.annatv.musicplayer.dao.PlaylistSongDao;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.PlaylistSong;

@Database(entities = {Playlist.class, PlaylistSong.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Log.d(TAG, "getInstance: getInstance");
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract PlaylistDao playlistDao();

    public abstract PlaylistSongDao playlistSongDao();
}
