package org.annatv.musicplayer.ui.home;

import android.content.Intent;
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
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.adapter.album.AlbumRecyclerAdapter;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.AlbumDetailActivity;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.NavigationUtil;
import org.jetbrains.annotations.NotNull;

public class AlbumFragment extends Fragment implements RecycleViewInterface {
    RecyclerView recyclerView;
    AlbumRecyclerAdapter adapter;
    private AlbumViewModel mViewModel;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.listRecycleView);
        adapter = new AlbumRecyclerAdapter((AppCompatActivity) getActivity(), this, AlbumLoader.getAllAlbums(getActivity()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(int position) {
        NavigationUtil.goToAlbum(getActivity(), adapter.getAlbumList().get(position).getId());
    }

    @Override
    public boolean onItemMenuClick(int id, int position) {

        return false;
    }


}