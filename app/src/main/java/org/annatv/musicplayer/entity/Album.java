package org.annatv.musicplayer.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.loader.AlbumLoader;

import java.util.ArrayList;
import java.util.List;

public class Album implements Parcelable, Searchable {
    public final List<Song> songs;

    public Album(List<Song> songs) {
        this.songs = songs;
    }

    public Album() {
        this.songs = new ArrayList<>();
    }

    public long getId() {
        return safeGetFirstSong().albumId;
    }

    public String getTitle() {
        return safeGetFirstSong().albumName;
    }

    public long getArtistId() {
        return safeGetFirstSong().artistId;
    }

    public String getArtistName() {
        return safeGetFirstSong().artistName;
    }

    public int getYear() {
        return safeGetFirstSong().year;
    }

    public long getDateModified() {
        return safeGetFirstSong().dateModified;
    }

    public int getSongCount() {
        return songs.size();
    }

    @NonNull
    public Song safeGetFirstSong() {
        return songs.isEmpty() ? Song.EMPTY_SONG : songs.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album that = (Album) o;

        return songs != null ? songs.equals(that.songs) : that.songs == null;

    }

    @Override
    public int hashCode() {
        return songs != null ? songs.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Album{" +
                "songs=" + songs +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(songs);
    }

    protected Album(Parcel in) {
        this.songs = in.createTypedArrayList(Song.CREATOR);
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public String getName() {
        return getTitle();
    }

    @Override
    public int getMenuRes() {
        return R.menu.item_album_menu;
    }

    @Override
    public int getCategory() {
        return Searchable.ALBUM;
    }
}
