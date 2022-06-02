package org.annatv.musicplayer.adapter.album;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.adapter.song.SongRecyclerViewAdapter;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Album> albumList = new ArrayList<>();

    public AlbumRecyclerAdapter(AppCompatActivity activity, List<Album> albumList) {
        this.activity = activity;
        this.albumList = albumList;
    }

    @NonNull
    @NotNull
    @Override
    public AlbumRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View itemView = layoutInflater.inflate(R.layout.item_standard, parent, false);
//        SongRecyclerViewAdapter.ViewHolder viewHolder = new SongRecyclerViewAdapter.ViewHolder(itemView);
//        viewHolder.binding = ItemStandardBinding.bind(itemView);
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new AlbumRecyclerAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumRecyclerAdapter.ViewHolder holder, int position) {
        final Album album = albumList.get(position);
        if (holder.binding.itemStandardTitle != null) {
            holder.binding.itemStandardTitle.setText(album.getTitle());
        }
        if (holder.binding.itemStandardText != null) {
            holder.binding.itemStandardText.setText(MusicUtil.getAlbumInfoString(activity, album));
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
        return albumList.size();
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