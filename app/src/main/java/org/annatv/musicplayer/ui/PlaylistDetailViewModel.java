package org.annatv.musicplayer.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.loader.PlaylistRepository;
import org.annatv.musicplayer.loader.PlaylistSongRepository;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailViewModel extends AndroidViewModel {
    private PlaylistSongRepository repository;
    MutableLiveData<List<Song>> songList;

    public PlaylistDetailViewModel(Application application) {
        super(application);
        songList = new MutableLiveData<>();
        songList.setValue(new ArrayList<>());
        repository = new PlaylistSongRepository(application);
    }

    public MutableLiveData<List<Song>> getSongList() {
        return songList;
    }

    public void setSongList(int pid) {
        repository.getPlaylistSongs(songList, pid);
    }

    public PlaylistSongRepository getRepository() {
        return repository;
    }
}
