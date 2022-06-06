package org.annatv.musicplayer.database;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import org.annatv.musicplayer.dao.*;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.PlaylistSong;
import org.annatv.musicplayer.entity.activitylist.FavouritePlaylistSong;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;
import org.annatv.musicplayer.entity.activitylist.TopPlaylistSong;

@Database(entities = {Playlist.class, PlaylistSong.class,
        HistoryPlaylistSong.class, TopPlaylistSong.class,
        FavouritePlaylistSong.class}, version = 10, exportSchema = false)
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

    public abstract HistoryPlaylistSongDao historyPlaylistSongDao();

    public abstract TopPlaylistSongDao topPlaylistSongDao();

    public abstract FavouritePlaylistSongDao favouritePlaylistDao();
}
