package org.annatv.musicplayer.adapter.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.*;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.PlaylistSongRepository;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private AppCompatActivity activity;
    protected List<Searchable> dataset;
    private RecycleViewInterface recycleViewInterface;
    private PlaylistSongRepository playlistSongRepository;

    public SearchAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Searchable> dataset) {
        this.activity = activity;
        this.dataset = dataset;
        this.recycleViewInterface = recycleViewInterface;
        playlistSongRepository = new PlaylistSongRepository(activity);
    }

    public List<Searchable> getDataset() {
        return dataset;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStandardBinding binding = ItemStandardBinding.inflate(inflater, parent, false);
        return new SearchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final Searchable item = dataset.get(position);
        holder.binding.itemStandardTitle.setText(item.getName());
        holder.binding.itemStandardMenu.setOnClickListener(view -> {
            holder.popupMenu = new PopupMenu(activity, view);
            holder.popupMenu.inflate(item.getMenuRes());
            holder.popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (recycleViewInterface != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        return recycleViewInterface.onItemMenuClick(menuItem.getItemId(), position);
                    }
                }
                return false;
            });
            holder.popupMenu.show();
        });
        holder.itemView.setOnClickListener(view -> {
            if (recycleViewInterface != null) {
                if (position != RecyclerView.NO_POSITION) {
                    recycleViewInterface.onItemClick(position);
                }
            }
        });

        // switch
        Bitmap bitmap;
        holder.binding.itemStandardImage.setColorFilter(null);
        switch (item.getCategory()) {
            case Searchable.SONG:
                Song song = (Song) item;
                holder.binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));
                bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, song.albumId));
                if (bitmap == null)
                    bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
                holder.binding.itemStandardImage.setImageBitmap(bitmap);
                break;
            case Searchable.ALBUM:
                Album album = (Album) item;
                holder.binding.itemStandardText.setText(MusicUtil.getAlbumInfoString(activity, album));
                bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, album.getId()));
                if (bitmap == null)
                    bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
                holder.binding.itemStandardImage.setImageBitmap(bitmap);
                break;
            case Searchable.ARTIST:
                Artist artist = (Artist) item;
                holder.binding.itemStandardText.setText(MusicUtil.getArtistInfoString(activity, artist));
                bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_artist_image);
                holder.binding.itemStandardImage.setImageBitmap(bitmap);
                break;
            case Searchable.PLAYLIST:
                Playlist playlist = (Playlist) item;
                holder.binding.itemStandardText.setText(
                        MusicUtil.getPlaylistInfoString(activity, playlistSongRepository.getPlaylistSongs(playlist.getPid())));
                holder.binding.itemStandardImage.setImageResource(R.drawable.ic_queue_music_white_24dp);
                holder.binding.itemStandardImage.setColorFilter(R.color.black);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void swapDataset(List<Searchable> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    class ViewHolder extends StandardViewHolder {
        protected ItemStandardBinding binding;

        public ViewHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public int getMenuRes() {
            return 0;
        }
    }
}
