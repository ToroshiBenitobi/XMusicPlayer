package org.annatv.musicplayer.ui.player;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.annatv.musicplayer.databinding.FragmentMiniPlayerBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.helper.MusicProgressViewUpdateHelper;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.util.MusicUtil;
import org.annatv.musicplayer.util.NavigationUtil;

public class MiniPlayerFragment extends MusicServiceFragment implements MusicProgressViewUpdateHelper.Callback {

    private static final String TAG = "MiniPlayerFragment";
    public FragmentMiniPlayerBinding binding;
//    private Unbinder unbinder;

//    @BindView(R.id.mini_player_title)
//    TextView miniPlayerTitle;
//    @BindView(R.id.mini_player_play_pause_button)
//    ImageView miniPlayerPlayPauseButton;
//    @BindView(R.id.progress_bar)
//    MaterialProgressBar progressBar;

//    private PlayPauseDrawable miniPlayerPlayPauseDrawable;

    private MusicProgressViewUpdateHelper progressViewUpdateHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressViewUpdateHelper = new MusicProgressViewUpdateHelper(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMiniPlayerBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: create");


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new FlingPlayBackController(getActivity()));

        binding.miniPlayerButton.setOnClickListener(v -> {
            if (MusicPlayerRemote.isPlaying()) {
                binding.miniPlayerButton.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp, null));
                MusicPlayerRemote.pauseSong();
            } else {
                binding.miniPlayerButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_white_24dp, null));
                MusicPlayerRemote.resumePlaying();
            }
        });

        view.setOnClickListener(v -> {
            NavigationUtil.goToPlayer(getActivity());
        });

    }

    private void updateButton() {
        if (MusicPlayerRemote.isPlaying()) {
            binding.miniPlayerButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_white_24dp, null));
        } else {
            binding.miniPlayerButton.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp, null));
        }
    }

    private void updateSongTitle() {
        binding.miniPlayerTitle.setText(MusicPlayerRemote.getCurrentSong().title);
    }

    private void updateSongImage() {
        String path = AlbumLoader.getAlbumArt(getActivity(), MusicPlayerRemote.getCurrentSong().getAlbumId());
        if (path != null) {
            binding.miniplayerImageView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public void onServiceConnected() {
        updateSongTitle();
        updateSongImage();
        updateButton();
        updatePlayPauseDrawableState(false);
    }

    @Override
    public void onPlayingMetaChanged() {
        updateSongTitle();
        updateSongImage();
        updateButton();
    }

    @Override
    public void onPlayStateChanged() {
        updatePlayPauseDrawableState(true);
    }

    @Override
    public void onUpdateProgressViews(int progress, int total) {
        binding.miniPlayerProgressBar.setMax(total);
        binding.miniPlayerProgressBar.setProgress(progress);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressViewUpdateHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        progressViewUpdateHelper.stop();
    }

    private static class FlingPlayBackController implements View.OnTouchListener {

        GestureDetector flingPlayBackController;

        public FlingPlayBackController(Context context) {
            flingPlayBackController = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    if (Math.abs(velocityX) > Math.abs(velocityY)) {
                        if (velocityX < 0) {
                            MusicPlayerRemote.playNextSong();
                            return true;
                        } else if (velocityX > 0) {
                            MusicPlayerRemote.playPreviousSong();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return flingPlayBackController.onTouchEvent(event);
        }
    }

    protected void updatePlayPauseDrawableState(boolean animate) {
        if (MusicPlayerRemote.isPlaying()) {

        } else {

        }
    }
}
