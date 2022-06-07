package org.annatv.musicplayer.entity;

import android.os.Parcel;
import android.os.Parcelable;
import org.annatv.musicplayer.R;

public class Song implements Parcelable, Searchable {
    public static final Song EMPTY_SONG = new Song(-1, "", -1, -1, -1, "", -1, -1, "", -1, "");

    public final long id;
    public final String title;
    public final int trackNumber;
    public final int year;
    public final long duration;
    public final String data;
    public final long dateModified;
    public final long albumId;
    public final String albumName;
    public final long artistId;
    public final String artistName;


    public Song(long id, String title, int trackNumber, int year, long duration, String data, long dateModified, long albumId, String albumName, long artistId, String artistName) {
        this.id = id;
        this.title = title;
        this.trackNumber = trackNumber;
        this.year = year;
        this.duration = duration;
        this.data = data;
        this.dateModified = dateModified;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;

    }

    public static Song getEmptySong() {
        return EMPTY_SONG;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getYear() {
        return year;
    }

    public long getDuration() {
        return duration;
    }

    public String getData() {
        return data;
    }

    public long getDateModified() {
        return dateModified;
    }

    public long getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.trackNumber);
        dest.writeInt(this.year);
        dest.writeLong(this.duration);
        dest.writeString(this.data);
        dest.writeLong(this.dateModified);
        dest.writeLong(this.albumId);
        dest.writeString(this.albumName);
        dest.writeLong(this.artistId);
        dest.writeString(this.artistName);
    }

    protected Song(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.trackNumber = in.readInt();
        this.year = in.readInt();
        this.duration = in.readLong();
        this.data = in.readString();
        this.dateModified = in.readLong();
        this.albumId = in.readLong();
        this.albumName = in.readString();
        this.artistId = in.readLong();
        this.artistName = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public String getName() {
        return getTitle();
    }

    @Override
    public int getMenuRes() {
        return R.menu.item_song_menu;
    }

    @Override
    public int getCategory() {
        return Searchable.SONG;
    }
}
