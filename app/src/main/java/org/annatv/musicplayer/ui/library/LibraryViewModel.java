package org.annatv.musicplayer.ui.library;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.loader.PlaylistRepository;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {

    private PlaylistRepository playlistRepository;

    public LibraryViewModel(Application application) {
        super(application);
        playlistRepository = new PlaylistRepository(application);
    }

    public LiveData<List<Playlist>> getPlaylists() {
        return playlistRepository.getPlaylists();
    }

    void insertPlaylists(Playlist... playlists) {
        playlistRepository.insertPlaylists(playlists);
    }
}