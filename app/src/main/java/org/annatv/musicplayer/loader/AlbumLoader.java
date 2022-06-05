package org.annatv.musicplayer.loader;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.util.Log;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.annatv.musicplayer.entity.Album;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.util.PreferenceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class AlbumLoader {

    public static String getSongLoaderSortOrder(Context context) {
        return PreferenceUtil.getInstance(context).getAlbumSortOrder() + ", " + PreferenceUtil.getInstance(context).getAlbumSongSortOrder();
    }

    @NonNull
    public static List<Album> getAllAlbums(@NonNull final Context context) {
        List<Song> songs = SongLoader.getSongs(SongLoader.makeSongCursor(
                context,
                null,
                null,
                getSongLoaderSortOrder(context))
        );
        return splitIntoAlbums(songs);
    }

    @NonNull
    public static List<Album> getAlbums(@NonNull final Context context, String query) {
        List<Song> songs = SongLoader.getSongs(SongLoader.makeSongCursor(
                context,
                AudioColumns.ALBUM + " LIKE ?",
                new String[]{"%" + query + "%"},
                getSongLoaderSortOrder(context))
        );
        return splitIntoAlbums(songs);
    }

    @NonNull
    public static Album getAlbum(@NonNull final Context context, long albumId) {
        List<Song> songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, AudioColumns.ALBUM_ID + "=?", new String[]{String.valueOf(albumId)}, getSongLoaderSortOrder(context)));
        Album album = new Album(songs);
        sortSongsByTrackNumber(album);
        return album;
    }

    @NonNull
    public static List<Album> splitIntoAlbums(@Nullable final List<Song> songs) {
        List<Album> albums = new ArrayList<>();
        if (songs != null) {
            for (Song song : songs) {
                getOrCreateAlbum(albums, song.albumId).songs.add(song);
            }
        }
        for (Album album : albums) {
            sortSongsByTrackNumber(album);
        }
        return albums;
    }

    public static String getAlbumArt(@NonNull Context context, long albumId) {
//        Cursor cursorAlbum = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART}, MediaStore.Audio.Albums._ID + "=" + albumId, null, null);
//        if (cursorAlbum != null && cursorAlbum.moveToFirst()) {
//            @SuppressLint("Range") String uri = cursorAlbum.getString(cursorAlbum.getColumnIndex("album_art"));
//            cursorAlbum.close();
//            Log.d("TAG", "getAlbumArt: " + uri);
//            if (uri != null) return uri;
//        }
//        return null;
        String filePath = null;

        Uri albumArtUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId);
        try {
            Bitmap bitmap = context.getContentResolver().loadThumbnail(albumArtUri, new Size(1024, 1024), null);

            File art = new File(context.getCacheDir(), "albumart" + albumId + ".jpg");
            art.createNewFile();
            FileOutputStream fos = new FileOutputStream(art);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
            filePath = art.getAbsolutePath();
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        }
        return filePath;
    }

    private static Album getOrCreateAlbum(List<Album> albums, long albumId) {
        for (Album album : albums) {
            if (!album.songs.isEmpty() && album.songs.get(0).albumId == albumId) {
                return album;
            }
        }
        Album album = new Album();
        albums.add(album);
        return album;
    }

    private static void sortSongsByTrackNumber(Album album) {
        Collections.sort(album.songs, (o1, o2) -> o1.trackNumber - o2.trackNumber);
    }
}
