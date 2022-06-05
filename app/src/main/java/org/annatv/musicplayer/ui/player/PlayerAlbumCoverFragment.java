package org.annatv.musicplayer.ui.player.player;

import android.animation.Animator;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.FragmentPlayerAlbumCoverBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.helper.MusicProgressViewUpdateHelper;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.player.MusicServiceFragment;


/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class PlayerAlbumCoverFragment extends MusicServiceFragment implements MusicProgressViewUpdateHelper.Callback {
    private FragmentPlayerAlbumCoverBinding binding;
    private MusicProgressViewUpdateHelper progressViewUpdateHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayerAlbumCoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressViewUpdateHelper = new MusicProgressViewUpdateHelper(this, 500, 1000);
        progressViewUpdateHelper.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressViewUpdateHelper.stop();
    }


    private void updateSongImage() {
        String path = AlbumLoader.getAlbumArt(getActivity(), MusicPlayerRemote.getCurrentSong().getAlbumId());
        if (path != null) {
            binding.playerAlbumCoverImageView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public void onServiceConnected() {
        updateSongImage();
    }

    @Override
    public void onPlayingMetaChanged() {
        updateSongImage();
    }

    @Override
    public void onUpdateProgressViews(int progress, int total) {

    }
}
