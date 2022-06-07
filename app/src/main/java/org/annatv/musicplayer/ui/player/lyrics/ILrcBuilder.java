package org.annatv.musicplayer.ui.player.lyrics;

import java.util.List;

//接口有一个List getLrcRows(String rawLrc)方法，该方法用来解析歌词，得到LrcRow的集合
public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}
