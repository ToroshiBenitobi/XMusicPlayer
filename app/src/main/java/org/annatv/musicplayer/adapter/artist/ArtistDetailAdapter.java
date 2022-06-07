package org.annatv.musicplayer.adapter.artist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.album.AlbumDetailAdapter;
import org.annatv.musicplayer.databinding.ItemAlbumDetailHeaderBinding;
import org.annatv.musicplayer.databinding.ItemArtistDetailHeaderBinding;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Artist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

public class ArtistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    AppCompatActivity activity;
    Artist artist;
    RecycleViewInterface recycleViewInterface;
    public static final int HEADER = 0;
    public static final int ALBUM = 1;


    public ArtistDetailAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, Artist artist) {
        this.activity = activity;
        this.artist = artist;
        this.recycleViewInterface = recycleViewInterface;
    }

    public Artist getArtist() {
        return artist;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return ALBUM;
        }
    }

    @Override
    public int getItemCount() {
        return artist.albums.size() + 1;
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HEADER) {
            ItemArtistDetailHeaderBinding binding = ItemArtistDetailHeaderBinding.inflate(layoutInflater, parent, false);
            return new ArtistDetailAdapter.HeaderHolder(binding);
        }
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new ArtistDetailAdapter.AlbumHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == HEADER) {
            ItemArtistDetailHeaderBinding binding = ((ArtistDetailAdapter.HeaderHolder) holder).binding;
            binding.artistDetailAlbumCount.setText(String.valueOf(artist.getAlbumCount()));
            binding.artistDetailSongCount.setText(String.valueOf(artist.getSongCount()));
            binding.artistDetailDuration.setText(MusicUtil.getAlbumTotalDurationString(activity, artist.albums));
            binding.albumArtistView.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art));
        } else {
            ItemStandardBinding binding = ((ArtistDetailAdapter.AlbumHolder) holder).binding;
            final Album album = artist.albums.get(position - 1);
            binding.itemStandardTitle.setText(album.getTitle());
            binding.itemStandardText.setText(MusicUtil.getAlbumInfoString(activity, album));

            Bitmap bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(activity, album.getId()));
            if (bitmap == null)
                bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.default_album_art);
            binding.itemStandardImage.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(view -> {
                recycleViewInterface.onItemClick(position - 1);
            });
        }
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        protected ItemArtistDetailHeaderBinding binding;

        public HeaderHolder(@NonNull ItemArtistDetailHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    class AlbumHolder extends RecyclerView.ViewHolder {
        protected ItemStandardBinding binding;

        public AlbumHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
