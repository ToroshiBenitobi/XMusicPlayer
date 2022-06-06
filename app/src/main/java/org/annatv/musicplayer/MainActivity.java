package org.annatv.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.entity.activitylist.HistoryPlaylistSong;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.PlaylistRepository;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.loader.SongLoader;
import org.annatv.musicplayer.ui.panel.MusicPanelActivity;
import org.annatv.musicplayer.ui.player.MiniPlayerFragment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends MusicPanelActivity {
    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_local, R.id.navigation_library)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        getSupportFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//                .add(R.id.fragmentViewMiniPlayer, MiniPlayerFragment.class, null)
//                .commit();
        fragmentContainerView = findViewById(R.id.fragmentViewMiniPlayer);
        miniPlayerFragment = (MiniPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentViewMiniPlayer);

        //todo: test
//        PlaylistSongRepository repository = new PlaylistSongRepository(getApplicationContext());
//        List<Song> songList = SongLoader.getAllSongs(getApplicationContext());
//
//        repository.insertHistoryPlaylistSongs(new HistoryPlaylistSong(songList.get(0).getId()));
//        repository.insertHistoryPlaylistSongs(new HistoryPlaylistSong(songList.get(1).getId()));
//        repository.insertHistoryPlaylistSongs(new HistoryPlaylistSong(songList.get(0).getId()));
//
//        repository.getHistorySongs().observe(this, songs -> {
//            Log.d(TAG, "!!!onCreate: " + songs.size() + " " + songs.size());
//        });
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        } else {
            Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            }
        }
    }
}