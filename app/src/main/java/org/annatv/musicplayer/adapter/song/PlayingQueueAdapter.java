package org.annatv.musicplayer.adapter.song;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class PlayingQueueAdapter extends RecyclerView.Adapter<PlayingQueueAdapter.ViewHolder> {

    private static final int HISTORY = 0;
    private static final int CURRENT = 1;
    private static final int UP_NEXT = 2;

    AppCompatActivity activity;
    List<Song> songList = new ArrayList<>();
    RecycleViewInterface recycleViewInterface;
    private int current;

    public List<Song> getSongList() {
        return songList;
    }

    public PlayingQueueAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Song> songList, int current) {
        this.activity = activity;
        this.songList = songList;
        this.recycleViewInterface = recycleViewInterface;
        this.current = current;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStandardBinding binding = ItemStandardBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayingQueueAdapter.ViewHolder holder, int position) {
        final Song song = songList.get(position);
        holder.binding.itemStandardTitle.setText(song.getTitle());
        holder.binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));

//        final int padding = activity.getResources().getDimensionPixelSize(R.dimen.default_item_margin) / 2;
//        holder.binding.itemStandardImage.setPadding(padding, padding, padding, padding);

        holder.binding.itemStandardImage.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        holder.binding.itemStandardImage.setImageBitmap(BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, song.albumId)));

        holder.binding.itemStandardMenu.setOnClickListener(view -> {
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
        });

        holder.itemView.setOnClickListener(view -> {
            if (recycleViewInterface != null) {
                recycleViewInterface.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (position < current) {
            return HISTORY;
        } else if (position > current) {
            return UP_NEXT;
        }
        return CURRENT;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void swapDataSet(List<Song> dataSet, int position) {
        this.songList = dataSet;
        current = position;
        notifyDataSetChanged();
    }

    public void setCurrent(int current) {
        this.current = current;
        notifyDataSetChanged();
    }

    public class ViewHolder extends StandardViewHolder {
        protected ItemStandardBinding binding;

        public ViewHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public int getMenuRes() {
            return R.menu.item_song_menu;
        }
    }
}
