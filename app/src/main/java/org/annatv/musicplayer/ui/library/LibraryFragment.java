package org.annatv.musicplayer.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.artist.ArtistRecyclerAdapter;
import org.annatv.musicplayer.adapter.playlist.PlaylistRecyclerAdapter;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.loader.ArtistLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements RecycleViewInterface {
    RecyclerView recyclerView;
    PlaylistRecyclerAdapter adapter;

    private LibraryViewModel libraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleViewLibrary);
        adapter = new PlaylistRecyclerAdapter((AppCompatActivity) getActivity(), this, new ArrayList<>());

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }
}