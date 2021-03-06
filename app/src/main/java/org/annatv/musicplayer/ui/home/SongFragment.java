package org.annatv.musicplayer.ui.home;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.song.SongRecyclerViewAdapter;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.loader.SongLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.ui.dialog.AddToPlaylistDialogFragment;
import org.annatv.musicplayer.util.NavigationUtil;
import org.jetbrains.annotations.NotNull;

/**
 * A fragment representing a list of Items.
 */
public class SongFragment extends Fragment implements RecycleViewInterface, AddToPlaylistDialogFragment.AddToPlaylistDialogListener {
    private static final String TAG = "SongFragment";
    RecyclerView recyclerView;
    SongRecyclerViewAdapter adapter;
    private int position;
    PlaylistSongRepository playlistSongRepository;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistSongRepository = new PlaylistSongRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listRecycleView);
        adapter = new SongRecyclerViewAdapter((AppCompatActivity) getActivity(), this, SongLoader.getAllSongs(getActivity()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        if (position != RecyclerView.NO_POSITION) {
            MusicPlayerRemote.openQueue(adapter.getSongList(), position, true);
        }
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        this.position = position;
        switch (id) {
            case R.id.actionSongAddToQueue:
                MusicPlayerRemote.enqueue(adapter.getSongList().get(position));
                return true;
            case R.id.actionSongAddToPlaylist:
                openAddToPlaylistDialog(position);
                return true;
            case R.id.actionSongGoToAlbum:
                NavigationUtil.goToAlbum(getActivity(), adapter.getSongList().get(position).getAlbumId());
                return true;
            case R.id.actionSongGoToArtist:
                NavigationUtil.goToArtist(getActivity(), adapter.getSongList().get(position).getArtistId());
                return true;
        }
        return false;

    }

    private void openAddToPlaylistDialog(int position) {
        AddToPlaylistDialogFragment fragment = new AddToPlaylistDialogFragment(this);
        fragment.show(getActivity().getSupportFragmentManager(), fragment.getClass().getName());
    }

    @Override
    public void onDialogListItemClick(int playlistId) {
        playlistSongRepository.insertPlaylistSongAsync(playlistId, adapter.getSongList().get(position).getId());
        Log.d(TAG, "onDialogListItemClick: " + "id: " + position + "playlistId: " + playlistId);
    }


//    private static class AsyncSongLoader extends WrappedAsyncTaskLoader<List<Song>> {
//        public AsyncSongLoader(Context context) {
//            super(context);
//        }
//
//        @Override
//        public List<Song> loadInBackground() {
//            return SongLoader.getAllSongs(getContext());
//        }
//    }
}