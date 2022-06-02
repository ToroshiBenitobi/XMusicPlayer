package org.annatv.musicplayer.ui.home;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.song.SongRecyclerViewAdapter;
import org.annatv.musicplayer.loader.SongLoader;
import org.jetbrains.annotations.NotNull;

/**
 * A fragment representing a list of Items.
 */
public class SongFragment extends Fragment {
    RecyclerView recyclerView;
    SongRecyclerViewAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new SongRecyclerViewAdapter(DummyContent.ITEMS));
//        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listRecycleView);
        adapter = new SongRecyclerViewAdapter((AppCompatActivity)getActivity(),SongLoader.getAllSongs(getActivity()));
        Log.d("TAG", SongLoader.getAllSongs(getActivity()).toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


//    private static class AsyncSongLoader extends WrappedAsyncTaskLoader<List<Song>> {
//        public AsyncSongLoader(Context context) {
//            super(context);
//        }
//
//        @Override
//        public List<Song> loadInBackground() {
//            return SongLoader.getAllSongs(getContext());
//        }
//    }
}