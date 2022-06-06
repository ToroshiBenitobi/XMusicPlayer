package org.annatv.musicplayer.loader;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.annatv.musicplayer.dao.FavouritePlaylistSongDao;
import org.annatv.musicplayer.dao.HistoryPlaylistSongDao;
import org.annatv.musicplayer.dao.PlaylistSongDao;
import org.annatv.musicplayer.dao.TopPlaylistSongDao;
import org.annatv.musicplayer.database.AppDatabase;
import org.annatv.musicplayer.entity.AbsPlaylistSong;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.entity.activitylist.FavouritePlaylistSong;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlaylistSongRepository {
    //    private MutableLiveData<List<Song>> historySongs;
//    private MutableLiveData<List<Song>> topSongs;
//    private MutableLiveData<List<Song>> favouriteSongs;

    public static final int HISTORY_PLAYLIST = -1;
    public static final int TOP_PLAYLIST = -2;
    public static final int FAVOURITE_PLAYLIST = -3;

    private HistoryPlaylistSongDao historyPlaylistSongDao;
    private TopPlaylistSongDao topPlaylistSongDao;
    private FavouritePlaylistSongDao favouritePlaylistSongDao;
    private PlaylistSongDao playlistSongDao;

    private Context context;

    public PlaylistSongRepository(Context context) {
        this.context = context;
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());

//        historySongs = new MutableLiveData<>();
//        topSongs = new MutableLiveData<>();
//        favouriteSongs = new MutableLiveData<>();
//
//        historySongs.setValue(new ArrayList<>());
//        topSongs.setValue(new ArrayList<>());
//        favouriteSongs.setValue(new ArrayList<>());

        historyPlaylistSongDao = appDatabase.historyPlaylistSongDao();
        topPlaylistSongDao = appDatabase.topPlaylistSongDao();
        favouritePlaylistSongDao = appDatabase.favouritePlaylistDao();
        playlistSongDao = appDatabase.playlistSongDao();

//        ExecutorService executor1 = Executors.newSingleThreadExecutor();
//        Handler handler1 = new Handler(Looper.getMainLooper());
//        executor1.execute(() -> {
//            List<HistoryPlaylistSong> historyPlaylistSongs = historyPlaylistSongDao.getAll();
//            List<Song> songs = getSongsFromPlaylistSongs(historyPlaylistSongs);
//            handler1.post(() -> {
//                historySongs.postValue(songs);
//            });
//        });

//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            playlistDao.insertAll(playlists);
//        });
//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            playlistDao.insertAll(playlists);
//        });
    }

    public void getPlaylistSongs(MutableLiveData<List<Song>> songList, int playlistId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Song> songs = null;
            switch (playlistId) {
                case HISTORY_PLAYLIST:
                    List<HistoryPlaylistSong> historyPlaylistSongs = historyPlaylistSongDao.getAll();
                    songs = getSongsFromPlaylistSongs(historyPlaylistSongs);
                    songList.postValue(songs);
                    break;
                case TOP_PLAYLIST:
                    break;
                case FAVOURITE_PLAYLIST:
                    break;
                default:
                    break;
            }
        });
    }

    public List<Song> getHistorySongs() {
        List<HistoryPlaylistSong> historyPlaylistSongs = historyPlaylistSongDao.getAll();
        return getSongsFromPlaylistSongs(historyPlaylistSongs);
    }

//    public List<Song> getTopSongs() {
//        return topSongs;
//    }

    public PlaylistSongDao getPlaylistSongDao() {
        return playlistSongDao;
    }

    public List<Song> getSongsFromPlaylistSongs(List playlistSongs) {
        List<Song> songs = new ArrayList<>();
        for (Object playlistSong : playlistSongs) {
            Song song = SongLoader.getSong(context.getApplicationContext(), ((AbsPlaylistSong) playlistSong).getSongId());
            if (song != Song.EMPTY_SONG)
                songs.add(song);
        }
        return songs;
    }

    public Song getSongFromPlaylistSong(AbsPlaylistSong playlistSong) {
        return SongLoader.getSong(context.getApplicationContext(), playlistSong.getSongId());
    }

    public void insertHistoryPlaylistSongs(HistoryPlaylistSong historyPlaylistSong) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            historyPlaylistSongDao.deleteBySongId(historyPlaylistSong.getSongId());
            historyPlaylistSongDao.insertAll(historyPlaylistSong);
        });

    }

    public void insert() {

    }
}
