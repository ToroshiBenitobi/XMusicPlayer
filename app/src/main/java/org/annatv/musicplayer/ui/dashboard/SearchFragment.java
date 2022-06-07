package org.annatv.musicplayer.ui.dashboard;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.search.SearchAdapter;
import org.annatv.musicplayer.databinding.FragmentSearchBinding;
import org.annatv.musicplayer.databinding.FragmentSongBinding;
import org.annatv.musicplayer.entity.*;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.ArtistLoader;
import org.annatv.musicplayer.loader.PlaylistRepository;
import org.annatv.musicplayer.loader.SongLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.annatv.musicplayer.util.NavigationUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment implements RecycleViewInterface {
    private FragmentSearchBinding binding;
    private SearchAdapter adapter;
    private SearchViewModel searchViewModel;
    private PlaylistRepository playlistRepository;
    private MutableLiveData<List<Searchable>> dataset;
    private ExecutorService executor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        dataset = new MutableLiveData<>();
        dataset.setValue(new ArrayList<>());
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playlistRepository = new PlaylistRepository(getActivity());

        adapter = new SearchAdapter((AppCompatActivity) getActivity(), this, new ArrayList<>());

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.searchRecyclerView.setLayoutManager(layoutManager);
        binding.searchRecyclerView.setAdapter(adapter);

        // Add listener.
        executor = Executors.newSingleThreadExecutor();
        binding.searchInputText.addTextChangedListener(new TextWatcher());
        binding.searchHintText.setVisibility(View.GONE);
        dataset.observe(this, dataset -> {
            adapter.swapDataset(dataset);
        });
        dataset.observe(this, dataset -> {
            if (dataset.isEmpty())
                binding.searchHintText.setVisibility(View.VISIBLE);
            else
                binding.searchHintText.setVisibility(View.GONE);
        });
    }

    @Override
    public void onItemClick(int position) {
        Searchable item = adapter.getDataset().get(position);

        switch (item.getCategory()) {
            case Searchable.SONG:
                Song song = (Song) item;
                List<Song> songList = new ArrayList<>();
                songList.add(song);
                MusicPlayerRemote.openQueue(songList, 0, true);
                break;
            case Searchable.ALBUM:
                Album album = (Album) item;
                NavigationUtil.goToAlbum(getActivity(), album.getId());
                break;
            case Searchable.ARTIST:
                Artist artist = (Artist) item;
                NavigationUtil.goToArtist(getActivity(), artist.getId());
                break;
            case Searchable.PLAYLIST:
                Playlist playlist = (Playlist) item;
                NavigationUtil.goToPlaylist(getActivity(), playlist.getPid());
                break;
        }

    }

    @Override
    public boolean onItemMenuClick(int id, int position) {
        return false;
    }

    class TextWatcher implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String query = charSequence.toString();
            if (query.isEmpty()) {
                dataset.setValue(new ArrayList<>());
                return;
            } else {
                executor.execute(() -> {
                    List<Searchable> newDataset = new ArrayList<>();
                    newDataset.addAll(SongLoader.getSongs(getContext(), query));
                    newDataset.addAll(AlbumLoader.getAlbums(getContext(), query));
                    newDataset.addAll(ArtistLoader.getArtists(getContext(), query));
                    newDataset.addAll(playlistRepository.getByNameLike(query));
                    dataset.postValue(newDataset);
                });
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}