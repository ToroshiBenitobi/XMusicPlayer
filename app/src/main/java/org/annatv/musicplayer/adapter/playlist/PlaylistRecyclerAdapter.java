package org.annatv.musicplayer.adapter.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.ItemAlbumDetailHeaderBinding;
import org.annatv.musicplayer.databinding.ItemPlaylistBinding;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaylistRecyclerAdapter extends RecyclerView.Adapter<PlaylistRecyclerAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Playlist> playlists;
    RecycleViewInterface recycleViewInterface;

    public PlaylistRecyclerAdapter(AppCompatActivity activity,RecycleViewInterface recycleViewInterface, List<Playlist> playlists) {
        this.activity = activity;
        this.playlists = playlists;
        this.recycleViewInterface = recycleViewInterface;
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
        if (position == 0) {
            holder.binding.itemPlaylistTitle.setText(R.string.recent_played);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_restore_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);

        } else if (position == 1) {
            holder.binding.itemPlaylistTitle.setText(R.string.top_played);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_trending_up_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
        } else if (position == 2) {
            holder.binding.itemPlaylistTitle.setText(R.string.my_favourite);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_favorite_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
        } else if (position == getItemCount() - 1) {
            holder.binding.itemPlaylistTitle.setText(R.string.add_playlist);
            holder.binding.itemPlaylistImage.setImageResource(R.drawable.ic_playlist_add_white_24dp);
            holder.binding.itemPlaylistImage.setColorFilter(R.color.purple_700);
            holder.binding.itemPlaylistMenu.setVisibility(View.GONE);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return playlists.size() + 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        protected ItemPlaylistBinding binding;

        public ViewHolder(@NonNull ItemPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
