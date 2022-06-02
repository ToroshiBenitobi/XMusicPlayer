package org.annatv.musicplayer.entity.activitylist;

import android.content.Context;
import androidx.annotation.NonNull;
import org.annatv.musicplayer.entity.Playlist;
import org.annatv.musicplayer.entity.Song;

import java.util.List;

public abstract class ActivityList extends Playlist {
    public ActivityList(String name) {
        super(name);
    }

    @NonNull
    public abstract List<Song> getSongList(Context context);
}
