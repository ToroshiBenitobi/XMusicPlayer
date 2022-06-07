package org.annatv.musicplayer.adapter.playlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.adapter.song.SongRecyclerViewAdapter;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailAdapter extends RecyclerView.Adapter<PlaylistDetailAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Song> songList = new ArrayList<>();
    RecycleViewInterface recycleViewInterface;

    public PlaylistDetailAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Song> songList) {
        this.activity = activity;
        this.songList = songList;
        this.recycleViewInterface = recycleViewInterface;
    }

    public List<Song> getSongList() {
        return songList;
    }

    @NonNull
    @NotNull
    @Override
    public PlaylistDetailAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new PlaylistDetailAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlaylistDetailAdapter.ViewHolder holder, int position) {
        final Song song = songList.get(position);
        holder.binding.itemStandardTitle.setText(song.getTitle());
        holder.binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));

        Bitmap bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, song.albumId));
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
        holder.binding.itemStandardImage.setImageBitmap(bitmap);

        holder.binding.itemStandardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.popupMenu = new PopupMenu(activity, view);
                holder.popupMenu.inflate(holder.getMenuRes());
                holder.popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (recycleViewInterface != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            return recycleViewInterface.onItemMenuClick(menuItem.getItemId(), position);
                        }
                    }
                    return false;
                });
                holder.popupMenu.show();
            }
        });

        holder.itemView.setOnClickListener(view -> {
            if (recycleViewInterface != null) {
                if (position != RecyclerView.NO_POSITION) {
                    MusicPlayerRemote.openQueue(songList, position, true);
                }
            }
        });
    }

    public void swapDataSet(List<Song> dataSet) {
        this.songList = dataSet;
        notifyDataSetChanged();
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

        @Override
        public int getMenuRes() {
            return R.menu.item_playlist_song_menu;
        }
    }
}
