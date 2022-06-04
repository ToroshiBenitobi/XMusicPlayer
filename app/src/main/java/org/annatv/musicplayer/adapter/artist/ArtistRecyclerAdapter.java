package org.annatv.musicplayer.adapter.artist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.adapter.album.AlbumRecyclerAdapter;
import org.annatv.musicplayer.adapter.holder.StandardViewHolder;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Artist;
import org.annatv.musicplayer.ui.RecycleViewInterface;
import org.annatv.musicplayer.util.MusicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArtistRecyclerAdapter extends RecyclerView.Adapter<ArtistRecyclerAdapter.ViewHolder> {
    AppCompatActivity activity;
    List<Artist> artistList = new ArrayList<>();
    RecycleViewInterface recycleViewInterface;

    public ArtistRecyclerAdapter(AppCompatActivity activity, RecycleViewInterface recycleViewInterface, List<Artist> artistList) {
        this.activity = activity;
        this.artistList = artistList;
        this.recycleViewInterface = recycleViewInterface;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    @NonNull
    @NotNull
    @Override
    public ArtistRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStandardBinding binding = ItemStandardBinding.inflate(layoutInflater, parent, false);
        return new ArtistRecyclerAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ArtistRecyclerAdapter.ViewHolder holder, int position) {
        final Artist artist = artistList.get(position);
        holder.binding.itemStandardTitle.setText(artist.getName());
        holder.binding.itemStandardText.setText(MusicUtil.getArtistInfoString(activity, artist));
//            holder.textView.setVisibility(View.GONE);

        final int padding = activity.getResources().getDimensionPixelSize(R.dimen.default_item_margin) / 2;
        holder.binding.itemStandardImage.setPadding(padding, padding, padding, padding);
//        holder.binding.itemStandardImage.setImageResource(R.drawable.ic_timer_white_24dp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    recycleViewInterface.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    static class ViewHolder extends StandardViewHolder {
        protected ItemStandardBinding binding;

        public ViewHolder(@NonNull ItemStandardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public int getMenuRes() {
            return 0;
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