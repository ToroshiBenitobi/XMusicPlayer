package org.annatv.musicplayer.ui.dialog;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.loader.PlaylistRepository;

import java.util.List;

public class AddToPlaylistDialogFragmentViewModel extends AndroidViewModel {
    private PlaylistRepository playlistRepository;

    public AddToPlaylistDialogFragmentViewModel(Application application) {
        super(application);
        playlistRepository = new PlaylistRepository(application);
    }

    public LiveData<List<Playlist>> getPlaylists() {
        return playlistRepository.getPlaylists();
    }

    void insertPlaylists(Playlist... playlists) {
        playlistRepository.insertPlaylistsAsync(playlists);
    }
}
