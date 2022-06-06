package org.annatv.musicplayer.ui.player;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.adapter.song.PlayingQueueAdapter;
import org.annatv.musicplayer.databinding.ActivityAlbumDetailBinding;
import org.annatv.musicplayer.databinding.ActivityPlayerBinding;
import org.annatv.musicplayer.databinding.FragmentPlayingQueueBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.jetbrains.annotations.NotNull;

public class PlayingQueueFragment extends MusicServiceFragment implements RecycleViewInterface {
    PlayingQueueAdapter adapter;
    private FragmentPlayingQueueBinding binding;


    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentPlayingQueueBinding.inflate(getLayoutInflater());
        adapter = new PlayingQueueAdapter((AppCompatActivity) getActivity(), this, MusicPlayerRemote.getPlayingQueue(), MusicPlayerRemote.getPosition());
        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        binding.playerRecyclerView.setLayoutManager(layoutManager);
        binding.playerRecyclerView.setAdapter(adapter);
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
    public void onPlayingMetaChanged() {
        super.onPlayingMetaChanged();
        updateQueuePosition();
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        updateQueue();
    }

    @Override
    public void onQueueChanged() {
        super.onQueueChanged();
        updateQueue();
    }

    @Override
    public void onMediaStoreChanged() {
        super.onMediaStoreChanged();
        updateQueue();
    }

    private void updateQueue() {
        adapter.swapDataSet(MusicPlayerRemote.getPlayingQueue(), MusicPlayerRemote.getPosition());
    }

    private void updateQueuePosition() {
        adapter.setCurrent(MusicPlayerRemote.getPosition());
    }
}
