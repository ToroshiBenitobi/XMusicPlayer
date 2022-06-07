package org.annatv.musicplayer.ui.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.annatv.musicplayer.databinding.FragmentLyricsBinding;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.helper.MusicProgressViewUpdateHelper;
import org.annatv.musicplayer.ui.player.lyrics.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LyricsFragment extends MusicServiceFragment implements MusicProgressViewUpdateHelper.Callback {
    private FragmentLyricsBinding binding;
    public final static String TAG = "LyricsFragment";

    /**
     * 自定义LrcView，用来展示歌词
     */
    ILrcView mLrcView;
    /**
     * 更新歌词的频率，每100ms更新一次
     */
    private int mPlayerTimerDuration = 100;
    /**
     * 更新歌词的定时器
     */
    private Timer mTimer;

    String lrc;
    /**
     * 更新歌词的定时任务
     */
    private TimerTask mTask;

    private MusicProgressViewUpdateHelper progressViewUpdateHelper;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressViewUpdateHelper = new MusicProgressViewUpdateHelper(this);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentLyricsBinding.inflate(inflater, container, false);
        mLrcView = binding.lrcView;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLyricsBinding.inflate(getLayoutInflater());
        mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSought(int newPosition, LrcRow row) {
                if (MusicPlayerRemote.musicService != null) {
                    Log.d(TAG, "onLrcSought:" + row.startTime);
                    MusicPlayerRemote.seekTo((int) row.startTime);
                }
            }
        });

        getFromAssets(getFilePath(MusicPlayerRemote.getCurrentSong().getId()));
        setUpLrcView();
        beginLrcPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        progressViewUpdateHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        progressViewUpdateHelper.stop();
    }

    /**
     * 从assets目录下读取歌词文件内容
     *
     * @param fileName
     * @return
     */
    public void getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            lrc = result;
        } catch (Exception e) {
            lrc = null;
            Log.d(TAG, "getFromAssets: null");
        }
    }

    private void setUpLrcView() {
        ILrcBuilder builder = new DefaultLrcBuilder();
        List<LrcRow> rows = builder.getLrcRows(lrc);
        mLrcView.setLrc(rows);
    }

    public String getFilePath(long songId) {
        Log.d(TAG, "getFilePath: " + songId + ".lrc");
        return songId + ".lrc";
    }

    public void beginLrcPlay() {
        try {
            //准备播放歌曲监听
//            mTimer = new Timer();
//            mTimer.scheduleAtFixedRate(, 0, mPlayerTimerDuration);
            //歌曲播放完毕监听
            //准备播放歌曲
            //开始播放歌曲
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止展示歌曲
     */
    public void stopLrcPlay() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onUpdateProgressViews(int progress, int total) {
        Log.d(TAG, "onUpdateProgressViews: ");
        mLrcView.seekLrcToTime(progress);
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected: ");
        getFromAssets(getFilePath(MusicPlayerRemote.getCurrentSong().getId()));
        setUpLrcView();
        beginLrcPlay();
    }

    @Override
    public void onPlayingMetaChanged() {
        super.onPlayingMetaChanged();
        Log.d(TAG, "onPlayingMetaChanged: ");
        stopLrcPlay();
        getFromAssets(getFilePath(MusicPlayerRemote.getCurrentSong().getId()));
        setUpLrcView();
        beginLrcPlay();
    }

    @Override
    public void onPlayStateChanged() {
        Log.d(TAG, "onPlayStateChanged: ");
        super.onPlayStateChanged();
    }

    //
//    /**
//     * 展示歌曲的定时任务
//     */
//    class LrcTask extends TimerTask{
//        @Override
//        public void run() {
//            //获取歌曲播放的位置
//            final long timePassed = MusicPlayerRemote.getSongProgressMillis();
//            MainActivity.this.runOnUiThread(new Runnable() {
//                public void run() {
//                    //滚动歌词
//                    mLrcView.seekLrcToTime(timePassed);
//                }
//            });
//
//        }
//    };
}
