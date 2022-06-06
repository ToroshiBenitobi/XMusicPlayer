package org.annatv.musicplayer.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.loader.PlaylistRepository;
import org.annatv.musicplayer.loader.PlaylistSongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PlaylistDetailViewModel extends AndroidViewModel {
    private PlaylistSongRepository repository;
    private PlaylistRepository playlistRepository;
    MutableLiveData<Playlist> playlist;
    MutableLiveData<List<Song>> songList;

    public PlaylistDetailViewModel(Application application) {
        super(application);
        songList = new MutableLiveData<>();
        songList.setValue(new ArrayList<>());
        playlist = new MutableLiveData<>();
        playlist.setValue(new Playlist("N/A"));
        repository = new PlaylistSongRepository(application);
        playlistRepository = new PlaylistRepository(application);
    }

    public MutableLiveData<Playlist> getPlaylist() {
        return playlist;
    }

    public MutableLiveData<List<Song>> getSongList() {
        return songList;
    }

    public void initPlaylist(int pid) {
        Executors.newSingleThreadExecutor().execute(() -> {
            songList.postValue(repository.getPlaylistSongs(pid));
        });
        Executors.newSingleThreadExecutor().execute(() -> {
            playlist.postValue(playlistRepository.getPlaylist(pid));
        });
    }

    public void deleteFromList(long songId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            songList.postValue(repository.deleteFromList(playlist.getValue().getPid(), songId));
        });
    }

    public void clearPlaylist() {
        repository.clearPlaylistAsync(playlist.getValue().getPid());
        songList.setValue(new ArrayList<>());
    }

    public PlaylistSongRepository getRepository() {
        return repository;
    }
}
