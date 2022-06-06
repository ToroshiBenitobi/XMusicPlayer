package org.annatv.musicplayer.ui;

import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.adapter.playlist.PlaylistDetailAdapter;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;
import org.annatv.musicplayer.databinding.ActivityPlaylistDetailBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.ui.library.LibraryViewModel;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;

import java.util.ArrayList;

public class PlaylistDetailActivity extends MusicServiceActivity implements RecycleViewInterface {
    private ActivityPlaylistDetailBinding binding;
    private PlaylistSongRepository playlistSongRepository;
    private PlaylistDetailViewModel viewModel;
    private PlaylistDetailAdapter adapter;
    public static final String PLAYLIST_ID = "playlist_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaylistDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel =
                new ViewModelProvider(this).get(PlaylistDetailViewModel.class);

        viewModel.setSongList(getIntent().getIntExtra(PLAYLIST_ID, PlaylistSongRepository.FAVOURITE_PLAYLIST));
        adapter = new PlaylistDetailAdapter(this, this, new ArrayList<>());

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleViewPlaylistDetail.setLayoutManager(layoutManager);
        binding.recycleViewPlaylistDetail.setAdapter(adapter);
        viewModel.getSongList().observe(this, songs -> {
            adapter.swapDataSet(songs);
        });


        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(.getTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }
}