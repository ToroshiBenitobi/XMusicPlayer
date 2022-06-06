package org.annatv.musicplayer.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.ui.home.AlbumFragment;
import org.annatv.musicplayer.ui.home.ArtistFragment;
import org.annatv.musicplayer.ui.home.SongFragment;
import org.annatv.musicplayer.ui.panel.MusicServiceActivity;
import org.annatv.musicplayer.ui.player.LyricsFragment;
import org.annatv.musicplayer.ui.player.PlayingQueueFragment;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerActivity extends MusicServiceActivity implements RecycleViewInterface {
    PlaylistSongRepository playlistSongRepository;
    MutableLiveData<Boolean> isFavourite;
    private ActivityPlayerBinding binding;
    ActionBar actionBar;
    Menu optionsMenu;
    ExecutorService isFavouriteExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        isFavourite = new MutableLiveData<>();
        isFavourite.setValue(false);

        isFavouriteExecutor = Executors.newSingleThreadExecutor();

        playlistSongRepository = new PlaylistSongRepository(this);

        ViewPager2 homeViewPager = binding.playerPageView;
        homeViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                Log.d(TAG, "createFragment: createFragment " + position);
                switch (position) {
                    case 0:
                        return new PlayingQueueFragment();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        optionsMenu = menu;
        isFavourite.observe(this, isFavourite -> {
            updateIsFavoritePost();
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddToFavourite:
                toggleSongInFavourites();
                return true;
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
        updateIsFavoriteAsync();
        updateActionBar();
    }

    @Override
    public void onPlayingMetaChanged() {
        super.onPlayingMetaChanged();
        updateIsFavoriteAsync();
        updateActionBar();
    }

    @Override
    public void onQueueChanged() {
    }

    @Override
    public void onMediaStoreChanged() {
        updateIsFavoriteAsync();
        updateActionBar();
    }


    private void updateIsFavoriteAsync() {
        long songId = MusicPlayerRemote.getCurrentSong().id;
        isFavouriteExecutor.execute(() -> {
            isFavourite.postValue(playlistSongRepository.isSongInFavourites(songId));
        });
    }

    private void updateIsFavoritePost() {
        boolean b = false;
        if (isFavourite != null)
            b = isFavourite.getValue();
        int res = b ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp;
        Drawable drawable = getResources().getDrawable(res, null);
        optionsMenu.findItem(R.id.actionAddToFavourite).setIcon(drawable)
                .setTitle(b ? getString(R.string.action_remove_from_favourites) : getString(R.string.action_add_to_favorites));
    }

    private void toggleSongInFavourites() {
        playlistSongRepository.toggleSongInFavouritesAsync(MusicPlayerRemote.getCurrentSong().getId());
        isFavourite.setValue(!isFavourite.getValue());
        updateIsFavoritePost();
    }

    private void updateActionBar() {
        actionBar.setTitle(MusicPlayerRemote.getCurrentSong().title);
    }
}
