package org.annatv.musicplayer.adapter.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.adapter.song.SongRecyclerViewAdapter;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Album> albumList = new ArrayList<>();
    private final RecycleViewInterface recycleViewInterface;

    public AlbumRecyclerAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Album> albumList) {
        this.activity = activity;
        this.recycleViewInterface = recycleViewInterface;
        this.albumList = albumList;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    @NonNull
    @NotNull
    @Override
    public AlbumRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new AlbumRecyclerAdapter.ViewHolder(binding, recycleViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumRecyclerAdapter.ViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.binding.itemStandardTitle.setText(album.getTitle());
        holder.binding.itemStandardText.setText(MusicUtil.getAlbumInfoString(activity, album));
//            holder.textView.setVisibility(View.GONE);

        Bitmap bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, album.getId()));
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
        holder.binding.itemStandardImage.setImageBitmap(bitmap);

        // todo: menu
//        holder.binding.itemStandardMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.popupMenu = new PopupMenu(activity, view);
//                holder.popupMenu.inflate(holder.getMenuRes());
//                holder.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        return recycleViewInterface.onItemMenuClick(menuItem.getItemId(), position);
//                    }
//                });
//                holder.popupMenu.show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class ViewHolder extends StandardViewHolder {
        protected ItemStandardBinding binding;

        public ViewHolder(@NonNull ItemStandardBinding binding, RecycleViewInterface recycleViewInterface) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(view -> {
                if (recycleViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recycleViewInterface.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getMenuRes() {
            return 0;
        }
    }
}