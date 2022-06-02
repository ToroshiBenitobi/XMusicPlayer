package org.annatv.musicplayer.adapter.song;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Song;
import org.jetbrains.annotations.NotNull;
import org.annatv.musicplayer.util.MusicUtil;

import java.util.ArrayList;
import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Song> songList = new ArrayList<>();

    public SongRecyclerViewAdapter(AppCompatActivity activity, List<Song> songList) {
        this.activity = activity;
        this.songList = songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @NotNull
    @Override
    public SongRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View itemView = layoutInflater.inflate(R.layout.item_standard, parent, false);
//        SongRecyclerViewAdapter.ViewHolder viewHolder = new SongRecyclerViewAdapter.ViewHolder(itemView);
//        viewHolder.binding = ItemStandardBinding.bind(itemView);
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new SongRecyclerViewAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull SongRecyclerViewAdapter.ViewHolder holder, int position) {
        final Song song = songList.get(position);
        if (holder.binding.itemStandardTitle != null) {
            holder.binding.itemStandardTitle.setText(song.getTitle());
        }
        if (holder.binding.itemStandardText != null) {
            holder.binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));
//            holder.textView.setVisibility(View.GONE);
        }
        if (holder.binding.itemStandardMenu != null) {

        }
        if (holder.binding.itemStandardImage != null) {
            final int padding = activity.getResources().getDimensionPixelSize(R.dimen.default_item_margin) / 2;
            holder.binding.itemStandardImage.setPadding(padding, padding, padding, padding);
            holder.binding.itemStandardImage.setImageResource(R.drawable.ic_timer_white_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class ViewHolder extends StandardViewHolder {
        protected ItemStandardBinding binding;

        public ViewHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

//        @Override
//        protected int getSongMenuRes() {
//            return R.menu.menu_item_cannot_delete_single_songs_playlist_song;
//        }
//
//        @Override
//        protected boolean onSongMenuItemClick(MenuItem item) {
//            if (item.getItemId() == R.id.action_go_to_album) {
//                Pair[] albumPairs = new Pair[]{
//                        Pair.create(image, activity.getString(R.string.transition_album_art))
//                };
//                NavigationUtil.goToAlbum(activity, dataSet.get(getAdapterPosition() - 1).albumId, albumPairs);
//                return true;
//            }
//            return super.onSongMenuItemClick(item);
//        }
    }
}
