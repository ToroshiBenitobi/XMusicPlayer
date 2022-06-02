package org.annatv.musicplayer.ui;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;

public class AlbumDetailActivity extends AppCompatActivity {
    private ActivityAlbumDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlbumDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}