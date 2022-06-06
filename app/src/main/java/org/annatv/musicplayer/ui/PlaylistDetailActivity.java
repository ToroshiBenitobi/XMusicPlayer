package org.annatv.musicplayer.ui;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.adapter.playlist.PlaylistDetailAdapter;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;
import org.annatv.musicplayer.databinding.ActivityPlaylistDetailBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.ui.dialog.AddToPlaylistDialogFragment;
import org.annatv.musicplayer.ui.library.LibraryViewModel;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;
import org.annatv.musicplayer.util.NavigationUtil;

import java.util.ArrayList;

public class PlaylistDetailActivity extends MusicServiceActivity implements RecycleViewInterface, AddToPlaylistDialogFragment.AddToPlaylistDialogListener {
    private ActivityPlaylistDetailBinding binding;
    private PlaylistSongRepository playlistSongRepository;
    private PlaylistDetailViewModel viewModel;
    private PlaylistDetailAdapter adapter;
    public static final String PLAYLIST_ID = "playlist_id";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaylistDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel =
                new ViewModelProvider(this).get(PlaylistDetailViewModel.class);
        playlistSongRepository = viewModel.getRepository();
        viewModel.initPlaylist(getIntent().getIntExtra(PLAYLIST_ID, PlaylistSongRepository.FAVOURITE_PLAYLIST));
        adapter = new PlaylistDetailAdapter(this, this, new ArrayList<>());

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleViewPlaylistDetail.setLayoutManager(layoutManager);
        binding.recycleViewPlaylistDetail.setAdapter(adapter);
        viewModel.getSongList().observe(this, songs -> {
            adapter.swapDataSet(songs);
        });

        ActionBar actionBar = getSupportActionBar();
        viewModel.getPlaylist().observe(this, playlist -> {
            switch (playlist.pid) {
                case PlaylistSongRepository.HISTORY_PLAYLIST:
                    actionBar.setTitle(getString(R.string.recent_played));
                case PlaylistSongRepository.TOP_PLAYLIST:
                    actionBar.setTitle(getString(R.string.top_played));
                case PlaylistSongRepository.FAVOURITE_PLAYLIST:
                    actionBar.setTitle(getString(R.string.my_favourite));
                default:
                    actionBar.setTitle(playlist.getName());
            }

        });
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playlist_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.actionClearPlaylist:
                viewModel.clearPlaylist();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.actionPlaylistSongDeleteFromPlaylist:
                viewModel.deleteFromList(adapter.getSongList().get(position).getId());
                return true;
            case R.id.actionSongAddToQueue:
                MusicPlayerRemote.enqueue(adapter.getSongList().get(position));
                return true;
            case R.id.actionSongAddToPlaylist:
                openAddToPlaylistDialog(position);
                return true;
            case R.id.actionSongGoToAlbum:
                NavigationUtil.goToAlbum(this, adapter.getSongList().get(position).getAlbumId());
                return true;
            case R.id.actionSongGoToArtist:
                NavigationUtil.goToArtist(this, adapter.getSongList().get(position).getArtistId());
                return true;
        }
        return false;
    }

    private void openAddToPlaylistDialog(int position) {
        AddToPlaylistDialogFragment fragment = new AddToPlaylistDialogFragment(this);
        fragment.show(getSupportFragmentManager(), fragment.getClass().getName());
    }

    @Override
    public void onDialogListItemClick(int playlistId) {
        playlistSongRepository.insertPlaylistSongAsync(playlistId, adapter.getSongList().get(position).getId());
    }
}