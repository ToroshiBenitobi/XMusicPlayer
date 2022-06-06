package org.annatv.musicplayer.ui.player;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import org.annatv.musicplayer.databinding.FragmentLyricsBinding;
import org.jetbrains.annotations.NotNull;

public class LyricsFragment extends MusicServiceFragment {
    private FragmentLyricsBinding binding;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLyricsBinding.inflate(getLayoutInflater());
    }
}
