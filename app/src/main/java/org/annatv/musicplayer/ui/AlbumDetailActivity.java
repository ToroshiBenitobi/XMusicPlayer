package org.annatv.musicplayer.ui;

import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.service.MusicService;
import org.annatv.musicplayer.ui.panel.MusicPanelActivity;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;

public class AlbumDetailActivity extends MusicPanelActivity implements RecycleViewInterface {
    private ActivityAlbumDetailBinding binding;
    AlbumDetailAdapter adapter;
    public static final String ALBUM_ID = "album_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlbumDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Album album = AlbumLoader.getAlbum(this, getIntent().getLongExtra(ALBUM_ID, 0));
        adapter = new AlbumDetailAdapter(this, this, album);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleViewAlbumDetail.setLayoutManager(layoutManager);
        binding.recycleViewAlbumDetail.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(album.getTitle());
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
        if (position != RecyclerView.NO_POSITION) {
            MusicPlayerRemote.openQueue(adapter.getAlbum().songs, position, true);
        }
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }
}