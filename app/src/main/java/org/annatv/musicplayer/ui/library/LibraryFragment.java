package org.annatv.musicplayer.ui.library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.artist.ArtistRecyclerAdapter;
import org.annatv.musicplayer.adapter.playlist.PlaylistRecyclerAdapter;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.ArtistLoader;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.ui.dialog.AddPlaylistDialogFragment;
import org.annatv.musicplayer.util.NavigationUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryFragment extends Fragment implements RecycleViewInterface, AddPlaylistDialogFragment.AddPlaylistDialogListener {
    RecyclerView recyclerView;
    PlaylistRecyclerAdapter adapter;
    PlaylistSongRepository playlistSongRepository;

    private LibraryViewModel libraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playlistSongRepository = new PlaylistSongRepository(getActivity());
        recyclerView = view.findViewById(R.id.recycleViewLibrary);
        LiveData<List<Playlist>> a = libraryViewModel.getPlaylists();
        List<Playlist> b = a.getValue();
        adapter = new PlaylistRecyclerAdapter((AppCompatActivity) getActivity(), this, new ArrayList<>());
        libraryViewModel.getPlaylists().observe(this, playlists -> adapter.swapDataSet(playlists));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        if (position == adapter.getItemCount() - 1) {
            openAddPlaylistDialog();
        } else if (position == 0) {
            NavigationUtil.goToPlaylist(getActivity(), PlaylistSongRepository.HISTORY_PLAYLIST);
        } else if (position == 1) {
            NavigationUtil.goToPlaylist(getActivity(), PlaylistSongRepository.TOP_PLAYLIST);
        } else if (position == 2) {
            NavigationUtil.goToPlaylist(getActivity(), PlaylistSongRepository.FAVOURITE_PLAYLIST);
        } else {
            NavigationUtil.goToPlaylist(getActivity(), adapter.getPlaylists().get(position - 3).getPid());
        }
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        switch (id) {
            case R.id.actionAddToQueue:
                enqueueAsync(position);
                return true;
            case R.id.actoinDeletePlaylist:
                libraryViewModel.deletePlaylistById(adapter.getPlaylists().get(position - 3).getPid());
                return true;
        }

        return false;
    }

    @Override
    public void onDialogPositiveClick(AppCompatDialogFragment dialog) {
        String name = ((AddPlaylistDialogFragment) dialog).getEditText().getText().toString();
        if (name.isEmpty()) return;
        Playlist playlist = new Playlist(name);
        libraryViewModel.insertPlaylists(playlist);
    }

    @Override
    public void onDialogNegativeClick(AppCompatDialogFragment dialog) {

    }

    private void openAddPlaylistDialog() {
        AddPlaylistDialogFragment fragment = new AddPlaylistDialogFragment(this);
        fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());
    }

    private void enqueueAsync(int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            int playlistId;
            if (position == 0) {
                playlistId = PlaylistSongRepository.HISTORY_PLAYLIST;
            } else if (position == 1) {
                playlistId = PlaylistSongRepository.TOP_PLAYLIST;
            } else if (position == 2) {
                playlistId = PlaylistSongRepository.FAVOURITE_PLAYLIST;
            } else {
                playlistId = adapter.getPlaylists().get(position - 3).getPid();
            }
            handler.post(() -> {
                MusicPlayerRemote.clearQueue();
                MusicPlayerRemote.openQueue(playlistSongRepository.getPlaylistSongs(playlistId),0,true);
            });
        });
    }
}