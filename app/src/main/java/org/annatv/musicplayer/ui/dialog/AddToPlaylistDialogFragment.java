package org.annatv.musicplayer.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.ItemDialogPlaylistBinding;
import org.annatv.musicplayer.entity.Playlist;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddToPlaylistDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = "AddToPlaylistDialogFragment";
    AddToPlaylistDialogListener listener;
    AddToPlaylistDialogFragmentViewModel viewModel;

    public AddToPlaylistDialogFragment(AddToPlaylistDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddToPlaylistDialogFragmentViewModel.class);

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.add_playlist).setItems(Playlist)
//        // Create the AlertDialog object and return it
//        AlertDialog dialog = builder.create();

        ListView playList = new ListView(getActivity());
        PlayListAdapter adapter = new PlayListAdapter(getActivity().getApplicationContext(), 0, new ArrayList<>());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        viewModel.getPlaylists().observe(this, playlists -> {
            adapter.swapDataSet(playlists);
        });
        playList.setOnItemClickListener((adapterView, view, i, l) -> {
            listener.onDialogListItemClick(adapter.getItem(i).getPid());
            dismiss();
        });
        playList.setAdapter(adapter);
        builder.setTitle(R.string.add_playlist);
        builder.setView(playList);
        return builder.create();
    }

    public interface AddToPlaylistDialogListener {
        public void onDialogListItemClick(int playlistId);
    }

    public class PlayListAdapter extends ArrayAdapter<Playlist> {
        private LayoutInflater inflater;


        public PlayListAdapter(Context context, int resource, List<Playlist> dataset) {
            super(context, resource, dataset);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            Playlist item = getItem(position);
            if (null == v) v = inflater.inflate(R.layout.item_dialog_playlist, null);
            TextView title = v.findViewById(R.id.playlistDialogTitle);
            title.setText(item.getName());
            return v;
        }

        public void swapDataSet(List<Playlist> dataSet) {
            Log.d(TAG, "swapDataSet: ");
            clear();
            addAll(dataSet);
            notifyDataSetChanged();
        }
    }
}
