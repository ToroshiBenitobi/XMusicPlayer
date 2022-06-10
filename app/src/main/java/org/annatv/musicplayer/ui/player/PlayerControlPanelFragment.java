package org.annatv.musicplayer.ui.player;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.FragmentPlayerControlPanelBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.helper.MusicProgressViewUpdateHelper;
import org.annatv.musicplayer.service.MusicService;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class PlayerControlPanelFragment extends MusicServiceFragment implements MusicProgressViewUpdateHelper.Callback {

    //    @BindView(R.id.player_play_pause__button)
//    ImageButton playPauseButton;
//    @BindView(R.id.player_prev_button)
//    ImageButton prevButton;
//    @BindView(R.id.player_next_button)
//    ImageButton nextButton;
//    @BindView(R.id.player_repeat_button)
//    ImageButton repeatButton;
//    @BindView(R.id.player_shuffle_button)
//    ImageButton shuffleButton;
//
//    @BindView(R.id.player_progress_slider)
//    SeekBar progressSlider;
//    @BindView(R.id.player_song_total_time)
//    TextView songTotalTime;
//    @BindView(R.id.player_song_current_progress)
//    TextView songCurrentProgress;
//
//    private PlayPauseDrawable playPauseDrawable;
    private FragmentPlayerControlPanelBinding binding;
    private int lastPlaybackControlsColor;
    private int lastDisabledPlaybackControlsColor;

    private MusicProgressViewUpdateHelper progressViewUpdateHelper;

    private AnimatorSet musicControllerAnimationSet;

    private boolean hidden = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressViewUpdateHelper = new MusicProgressViewUpdateHelper(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayerControlPanelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpMusicControllers();
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

    @Override
    public void onServiceConnected() {
        updatePlayPauseDrawableState(false);
        updateRepeatState();
        updateShuffleState();
    }

    @Override
    public void onPlayStateChanged() {
        updatePlayPauseDrawableState(true);
    }

    @Override
    public void onRepeatModeChanged() {
        updateRepeatState();
    }

    @Override
    public void onShuffleModeChanged() {
        updateShuffleState();
    }

    private void setUpPlayPauseButton() {
        binding.playerPlayButton.setOnClickListener(v -> {
            if (MusicPlayerRemote.isPlaying()) {
                binding.playerPlayButton.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp, null));
                MusicPlayerRemote.pauseSong();
            } else {
                binding.playerPlayButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_white_24dp, null));
                MusicPlayerRemote.resumePlaying();
            }
        });
    }

    protected void updatePlayPauseDrawableState(boolean animate) {
        if (MusicPlayerRemote.isPlaying()) {
            binding.playerPlayButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_white_24dp, null));
        } else {
            binding.playerPlayButton.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_white_24dp, null));
        }
    }

    private void setUpMusicControllers() {
        setUpPlayPauseButton();
        setUpPrevNext();
        setUpRepeatButton();
        setUpShuffleButton();
        setUpProgressSlider();
    }

    private void setUpPrevNext() {
        binding.playerNextSongButton.setOnClickListener(v -> MusicPlayerRemote.playNextSong());
        binding.playerPreSongButton.setOnClickListener(v -> MusicPlayerRemote.back());
    }

    private void setUpShuffleButton() {
        binding.playerShuffleButton.setOnClickListener(v -> MusicPlayerRemote.toggleShuffleMode());
    }

    private void updateShuffleState() {
        switch (MusicPlayerRemote.getShuffleMode()) {
            case MusicService.SHUFFLE_MODE_SHUFFLE:
                binding.playerShuffleButton.setBackground(getResources().getDrawable(R.drawable.ic_shuffle_white_24dp));
                binding.playerShuffleButton.setAlpha(1f);
                break;
            default:
                binding.playerShuffleButton.setBackground(getResources().getDrawable(R.drawable.ic_shuffle_white_24dp));
                binding.playerShuffleButton.setAlpha(0.2f);
                break;
        }
    }

    private void setUpRepeatButton() {
        binding.playerRepeatModeButton.setOnClickListener(v -> MusicPlayerRemote.cycleRepeatMode());
    }

    private void updateRepeatState() {
        switch (MusicPlayerRemote.getRepeatMode()) {
            case MusicService.REPEAT_MODE_NONE:
                binding.playerRepeatModeButton.setBackground(getResources().getDrawable(R.drawable.ic_repeat_white_24dp, null));
                binding.playerShuffleButton.setAlpha(0.2f);
                break;
            case MusicService.REPEAT_MODE_ALL:
                binding.playerRepeatModeButton.setBackground(getResources().getDrawable(R.drawable.ic_repeat_white_24dp, null));
                binding.playerShuffleButton.setAlpha(1f);
                break;
            case MusicService.REPEAT_MODE_THIS:
                binding.playerRepeatModeButton.setBackground(getResources().getDrawable(R.drawable.ic_repeat_one_white_24dp, null));
                binding.playerShuffleButton.setAlpha(1f);
                break;
        }
    }

    private void setUpProgressSlider() {

        binding.playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MusicPlayerRemote.seekTo(progress);
                    onUpdateProgressViews(MusicPlayerRemote.getSongProgressMillis(), MusicPlayerRemote.getSongDurationMillis());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onUpdateProgressViews(int progress, int total) {
        binding.playerSeekBar.setMax(total);
        binding.playerSeekBar.setProgress(progress);
    }
}
