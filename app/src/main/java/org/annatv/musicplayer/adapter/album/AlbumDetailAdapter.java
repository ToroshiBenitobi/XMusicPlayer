package org.annatv.musicplayer.adapter.album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.databinding.ItemAlbumDetailHeaderBinding;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

public class AlbumDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    AppCompatActivity activity;
    private Album album;
    RecycleViewInterface recycleViewInterface;
    public static final int HEADER = 0;
    public static final int SONG = 1;


    public AlbumDetailAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, Album album) {
        this.activity = activity;
        this.album = album;
        this.recycleViewInterface = recycleViewInterface;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return SONG;
        }
    }

    public Album getAlbum() {
        return album;
    }

    @Override
    public int getItemCount() {
        return album.songs.size() + 1;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER) {
            ItemAlbumDetailHeaderBinding binding = ItemAlbumDetailHeaderBinding.inflate(layoutInflater, parent, false);
            return new HeaderHolder(binding);
        }
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new SongHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == HEADER) {
            ItemAlbumDetailHeaderBinding binding = ((HeaderHolder) holder).binding;
            binding.albumDetailArtist.setText(album.getArtistName());
            binding.albumDetailCount.setText(String.valueOf(album.getSongCount()));
            binding.albumDetailDuration.setText(MusicUtil.getTotalDurationString(activity, album.songs));
            Bitmap bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, album.getId()));
            if (bitmap == null)
                bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
            binding.albumDetailView.setImageBitmap(bitmap);
        } else {
            ItemStandardBinding binding = ((SongHolder) holder).binding;
            final Song song = album.songs.get(position - 1);
            binding.itemStandardTitle.setText(song.getTitle());
            binding.itemStandardText.setText(MusicUtil.getSongInfoString(song));

            binding.itemStandardImage.setImageResource(R.drawable.ic_music_note_white_24dp);
            binding.itemStandardImage.setColorFilter(R.color.black);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycleViewInterface.onItemClick(position - 1);
                }
            });
        }
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        protected ItemAlbumDetailHeaderBinding binding;

        public HeaderHolder(@NonNull ItemAlbumDetailHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    class SongHolder extends RecyclerView.ViewHolder {
        protected ItemStandardBinding binding;

        public SongHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
