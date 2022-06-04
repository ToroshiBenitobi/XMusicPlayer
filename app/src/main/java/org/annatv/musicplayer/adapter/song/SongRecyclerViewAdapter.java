package org.annatv.musicplayer.adapter.song;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.jetbrains.annotations.NotNull;
import org.annatv.musicplayer.util.MusicUtil;

import java.util.ArrayList;
import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Song> songList = new ArrayList<>();
    RecycleViewInterface recycleViewInterface;

    public SongRecyclerViewAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Song> songList) {
        this.activity = activity;
        this.songList = songList;
        this.recycleViewInterface = recycleViewInterface;
    }

    public List<Song> getSongList() {
        return songList;
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
        holder.binding.itemStandardTitle.setText(song.getTitle());
        holder.binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));

        final int padding = activity.getResources().getDimensionPixelSize(R.dimen.default_item_margin) / 2;
        holder.binding.itemStandardImage.setPadding(padding, padding, padding, padding);
        holder.binding.itemStandardImage.setImageBitmap(BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, song.albumId)));

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
            return R.menu.item_song_menu;
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
