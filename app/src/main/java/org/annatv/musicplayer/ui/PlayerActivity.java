package org.annatv.musicplayer.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import org.annatv.musicplayer.adapter.song.PlayingQueueAdapter;
import org.annatv.musicplayer.databinding.ActivityPlayerBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;

public class PlayerActivity extends MusicServiceActivity implements RecycleViewInterface {
    private ActivityPlayerBinding binding;
    PlayingQueueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new PlayingQueueAdapter(this, this, MusicPlayerRemote.getPlayingQueue(), MusicPlayerRemote.getPosition());
        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        binding.playerRecyclerView.setLayoutManager(layoutManager);
        binding.playerRecyclerView.setAdapter(adapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(MusicPlayerRemote.getCurrentSong().title);
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
            MusicPlayerRemote.openQueue(adapter.getSongList(), position, true);
        }
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }

    @Override
    public void onServiceConnected() {
        updateQueue();
        updateIsFavorite();
    }

    @Override
    public void onPlayingMetaChanged() {
        updateIsFavorite();
        updateQueuePosition();
    }

    @Override
    public void onQueueChanged() {
        updateQueue();
    }

    @Override
    public void onMediaStoreChanged() {
        updateQueue();
        updateIsFavorite();
    }

    private void updateQueue() {
        adapter.swapDataSet(MusicPlayerRemote.getPlayingQueue(), MusicPlayerRemote.getPosition());
    }

    private void updateQueuePosition() {
        adapter.setCurrent(MusicPlayerRemote.getPosition());
    }

    private void updateIsFavorite() {

    }
}
