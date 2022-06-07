package org.annatv.musicplayer.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import org.annatv.musicplayer.R;

@Entity(tableName = "playlist")
public class Playlist implements Searchable {
    @PrimaryKey(autoGenerate = true)
    public int pid;
    @ColumnInfo(name = "name")
    public String name;

    public Playlist(String name) {
        this.name = name;
    }

    @Ignore
    public Playlist(int pid, String name) {
        this.pid = pid;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMenuRes() {
        return R.menu.item_playlist_menu;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCategory() {
        return Searchable.PLAYLIST;
    }
}
