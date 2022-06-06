package org.annatv.musicplayer.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.song.PlayingQueueAdapter;
import org.annatv.musicplayer.databinding.ActivityPlayerBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.ui.home.AlbumFragment;
import org.annatv.musicplayer.ui.home.ArtistFragment;
import org.annatv.musicplayer.ui.home.SongFragment;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;
import org.annatv.musicplayer.ui.player.LyricsFragment;
import org.jetbrains.annotations.NotNull;

public class PlayerActivity extends MusicServiceActivity implements RecycleViewInterface {
    private ActivityPlayerBinding binding;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager2 homeViewPager = binding.playerPageView;
        homeViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new SongFragment();
                    default:
                        return new LyricsFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });



        actionBar = getSupportActionBar();
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

    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        updateIsFavorite();
        updateActionBar();
    }

    @Override
    public void onPlayingMetaChanged() {
        super.onPlayingMetaChanged();
        updateIsFavorite();
        updateActionBar();
    }

    @Override
    public void onQueueChanged() {

    }

    @Override
    public void onMediaStoreChanged() {

        updateIsFavorite();
        updateActionBar();
    }



    private void updateIsFavorite() {

    }

    private void updateActionBar() {
        actionBar.setTitle(MusicPlayerRemote.getCurrentSong().title);
    }
}
