package org.annatv.musicplayer.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Artist;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.R;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class MusicUtil {

    private static final String TAG = "MusicUtil";


    public static Uri getSongFileUri(long songId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
    }

    @NonNull
    public static String getArtistInfoString(@NonNull final Context context, @NonNull final Artist artist) {
        int albumCount = artist.getAlbumCount();
        int songCount = artist.getSongCount();

        return MusicUtil.buildInfoString(
                MusicUtil.getAlbumCountString(context, albumCount),
                MusicUtil.getSongCountString(context, songCount)
        );
    }

    @NonNull
    public static String getAlbumInfoString(@NonNull final Context context, @NonNull final Album album) {
        int songCount = album.getSongCount();

        return MusicUtil.buildInfoString(
                album.getArtistName(),
                MusicUtil.getSongCountString(context, songCount)
        );
    }

    @NonNull
    public static String getPlaylistInfoString(@NonNull final Context context, @NonNull List<Song> songs) {
        final long duration = getTotalDuration(context, songs);

        return MusicUtil.buildInfoString(
                MusicUtil.getSongCountString(context, songs.size()),
                MusicUtil.getTotalDurationString(duration)
        );
    }

    @NonNull
    public static String getSongCountString(@NonNull final Context context, int songCount) {
        final String songString = songCount == 1 ? context.getResources().getString(R.string.song) : context.getResources().getString(R.string.songs);
        return songCount + " " + songString;
    }

    @NonNull
    public static String getAlbumCountString(@NonNull final Context context, int albumCount) {
        final String albumString = albumCount == 1 ? context.getResources().getString(R.string.album) : context.getResources().getString(R.string.albums);
        return albumCount + " " + albumString;
    }

    public static long getTotalDuration(@NonNull final Context context, @NonNull List<Song> songs) {
        long duration = 0;
        for (int i = 0; i < songs.size(); i++) {
            duration += songs.get(i).duration;
        }
        return duration;
    }

    public static String getTotalDurationString(@NonNull final Context context, @NonNull List<Song> songs) {
        long duration = getTotalDuration(context, songs);
        return getTotalDurationString(duration);
    }

    public static String getAlbumTotalDurationString(@NonNull final Context context, @NonNull List<Album> albums) {
        long duration = 0;
        for (Album album : albums) {
            duration += getTotalDuration(context, album.songs);
        }
        return getTotalDurationString(duration);
    }

    //
    public static String getTotalDurationString(long duration) {
        duration = duration / 1000;
        long minutes = (duration) / 60;
        long seconds = (duration) % 60;
        if (minutes < 60) {
            return String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds);
        } else {
            long hours = minutes / 60;
            minutes = minutes % 60;
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        }
    }

    public static boolean isArtistNameUnknown(@Nullable String artistName) {
        if (TextUtils.isEmpty(artistName)) return false;
        if (artistName.equals(Artist.DEFAULT_NAME)) return true;
        artistName = artistName.trim().toLowerCase();
        return artistName.equals("unknown") || artistName.equals("<unknown>");
    }

    @NonNull
    public static String getSongInfoString(@NonNull final Song song) {
        return MusicUtil.buildInfoString(
                song.artistName,
                song.albumName
        );
    }

    @NonNull
    public static String buildInfoString(@Nullable final String string1, @Nullable final String string2) {
        // Skip empty strings
        if (TextUtils.isEmpty(string1)) {
            //noinspection ConstantConditions
            return TextUtils.isEmpty(string2) ? "" : string2;
        }
        if (TextUtils.isEmpty(string2)) {
            //noinspection ConstantConditions
            return TextUtils.isEmpty(string1) ? "" : string1;
        }

        return string1 + "  •  " + string2;
    }
}
