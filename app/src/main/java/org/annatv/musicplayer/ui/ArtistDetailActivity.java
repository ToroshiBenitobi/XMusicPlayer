package org.annatv.musicplayer.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.adapter.artist.ArtistDetailAdapter;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;
import org.annatv.musicplayer.databinding.ActivityArtistDetailBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Artist;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.ArtistLoader;
import org.annatv.musicplayer.ui.panel.MusicPanelActivity;
import org.annatv.musicplayer.util.NavigationUtil;

public class ArtistDetailActivity extends MusicPanelActivity implements RecycleViewInterface {
    private ActivityArtistDetailBinding binding;
    ArtistDetailAdapter adapter;
    public static final String ARTIST_ID = "artist_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtistDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Artist artist = ArtistLoader.getArtist(this, getIntent().getLongExtra(ARTIST_ID, -1));
        adapter = new ArtistDetailAdapter(this, this, artist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleViewArtistDetail.setLayoutManager(layoutManager);
        binding.recycleViewArtistDetail.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(artist.getName());
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
        NavigationUtil.goToAlbum(this, adapter.getArtist().albums.get(position).getId());
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }
}
