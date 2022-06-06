package org.annatv.musicplayer.adapter.playlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.ItemAlbumDetailHeaderBinding;
import org.annatv.musicplayer.databinding.ItemPlaylistBinding;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Playlist> playlists;
    RecycleViewInterface recycleViewInterface;

    public PlaylistRecyclerAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Playlist> playlists) {
        this.activity = activity;
        this.playlists = playlists;
        this.recycleViewInterface = recycleViewInterface;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    @NonNull
    @NotNull
    @Override
    public PlaylistRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPlaylistBinding binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlaylistRecyclerAdapter.ViewHolder holder, int position) {
        int menuRes;
        if (position == 0) {
            holder.binding.itemPlaylistTitle.setText(R.string.recent_played);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_restore_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.VISIBLE);
            menuRes = R.menu.item_activity_list_menu;
        } else if (position == 1) {
            holder.binding.itemPlaylistTitle.setText(R.string.top_played);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_trending_up_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.VISIBLE);
            menuRes = R.menu.item_activity_list_menu;
        } else if (position == 2) {
            holder.binding.itemPlaylistTitle.setText(R.string.my_favourite);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_favorite_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.VISIBLE);
            menuRes = R.menu.item_activity_list_menu;
        } else if (position == getItemCount() - 1) {
            holder.binding.itemPlaylistTitle.setText(R.string.add_playlist);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_playlist_add_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.GONE);
            menuRes = 0;
        } else {
            Playlist playlist = playlists.get(position - 3);
            holder.binding.itemPlaylistTitle.setText(playlist.getName());
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_queue_music_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.VISIBLE);
            menuRes = R.menu.item_playlist_menu;
        }
        holder.itemView.setOnClickListener(view -> {
            recycleViewInterface.onItemClick(position);
        });

        holder.binding.itemPlaylistMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.popupMenu = new PopupMenu(activity, view);
                holder.popupMenu.inflate(menuRes);
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

    }

    public void swapDataSet(List<Playlist> dataSet) {
        this.playlists = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playlists.size() + 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected ItemPlaylistBinding binding;
        public PopupMenu popupMenu = null;

        public ViewHolder(@NonNull ItemPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
