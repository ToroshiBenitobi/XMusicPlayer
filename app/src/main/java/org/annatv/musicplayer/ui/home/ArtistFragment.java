package org.annatv.musicplayer.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.album.AlbumRecyclerAdapter;
import org.annatv.musicplayer.adapter.artist.ArtistRecyclerAdapter;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.ArtistLoader;
import org.jetbrains.annotations.NotNull;

public class ArtistFragment extends Fragment {
    RecyclerView recyclerView;
    ArtistRecyclerAdapter adapter;
    private ArtistViewModel mViewModel;

    public static ArtistFragment newInstance() {
        return new ArtistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.listRecycleView);
        adapter = new ArtistRecyclerAdapter((AppCompatActivity) getActivity(), ArtistLoader.getAllArtists(getActivity()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}