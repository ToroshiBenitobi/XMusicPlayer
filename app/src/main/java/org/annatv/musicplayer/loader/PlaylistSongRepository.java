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
import org.annatv.musicplayer.entity.PlaylistSong;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.entity.activitylist.FavouritePlaylistSong;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;
import org.annatv.musicplayer.entity.activitylist.TopPlaylistSong;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<Song> getPlaylistSongs(int playlistId) {
        List<Song> songs = null;
        switch (playlistId) {
            case HISTORY_PLAYLIST:
                List<HistoryPlaylistSong> historyPlaylistSongs = historyPlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(historyPlaylistSongs);
                break;
            case TOP_PLAYLIST:
                List<TopPlaylistSong> topPlaylistSongs = topPlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(topPlaylistSongs);
                break;
            case FAVOURITE_PLAYLIST:
                List<FavouritePlaylistSong> favouritePlaylistSongs = favouritePlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(favouritePlaylistSongs);
                break;
            default:
                List<PlaylistSong> playlistSongs = playlistSongDao.findAllByPlaylistId(playlistId);
                songs = getSongsFromPlaylistSongs(playlistSongs);
                break;
        }
        if (songs == null)
            return new ArrayList<>();
        else
            return songs;
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

    public void insertHistoryPlaylistSongsAsync(long songId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            historyPlaylistSongDao.deleteBySongId(songId);
            historyPlaylistSongDao.insertAll(new HistoryPlaylistSong(songId));
        });
    }

    public void addTopSongCountAsync(long songId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            TopPlaylistSong song = topPlaylistSongDao.getBySongId(songId);
            if (song == null) {
                topPlaylistSongDao.insert(new TopPlaylistSong(songId, 1));
            } else {
                song.setPlays(song.getPlays() + 1);
                topPlaylistSongDao.update(song);
            }
        });
    }

    public void insertPlaylistSongAsync(int playlistId, long songId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (playlistSongDao.findByPlaylistIdAndSongId(playlistId, songId) == null) {
                PlaylistSong song = new PlaylistSong(playlistId, songId);
                playlistSongDao.insertAll(song);
            }
        });
    }

    // Return new list.
    public List<Song> deleteFromList(int playlistId, long songId) {
        List<Song> songs = null;
        switch (playlistId) {
            case HISTORY_PLAYLIST:
                historyPlaylistSongDao.deleteBySongId(songId);
                List<HistoryPlaylistSong> historyPlaylistSongs = historyPlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(historyPlaylistSongs);
                break;
            case TOP_PLAYLIST:
                topPlaylistSongDao.deleteBySongId(songId);
                List<TopPlaylistSong> topPlaylistSongs = topPlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(topPlaylistSongs);
                break;
            case FAVOURITE_PLAYLIST:
                favouritePlaylistSongDao.deleteBySongId(songId);
                List<FavouritePlaylistSong> favouritePlaylistSongs = favouritePlaylistSongDao.getAll();
                songs = getSongsFromPlaylistSongs(favouritePlaylistSongs);
                break;
            default:
                playlistSongDao.deleteByPlaylistIdAndSongId(playlistId, songId);
                List<PlaylistSong> playlistSongs = playlistSongDao.findAllByPlaylistId(playlistId);
                songs = getSongsFromPlaylistSongs(playlistSongs);
                break;
        }
        if (songs == null)
            return new ArrayList<>();
        else
            return songs;
    }

    public void clearPlaylistAsync(int playlistId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            switch (playlistId) {
                case HISTORY_PLAYLIST:
                    historyPlaylistSongDao.clear();
                    break;
                case TOP_PLAYLIST:
                    topPlaylistSongDao.clear();
                    break;
                case FAVOURITE_PLAYLIST:
                    favouritePlaylistSongDao.clear();
                    break;
                default:
                    playlistSongDao.clear(playlistId);
                    break;
            }
        });
    }

    public boolean isSongInFavourites(long songId) {
        return null != favouritePlaylistSongDao.getBySongId(songId);
    }

    public void toggleSongInFavouritesAsync(long songId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (isSongInFavourites(songId)) {
                favouritePlaylistSongDao.deleteBySongId(songId);
            } else {
                favouritePlaylistSongDao.insert(new FavouritePlaylistSong(songId));
            }
        });
    }
}
