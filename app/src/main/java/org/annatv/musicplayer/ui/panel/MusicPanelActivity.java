package org.annatv.musicplayer.ui.panel;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.PathInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.helper.MusicPlayerRemote;
import org.annatv.musicplayer.ui.player.player.MiniPlayerFragment;
import org.annatv.musicplayer.ui.player.player.NowPlayingScreen;
import org.annatv.musicplayer.util.PreferenceUtil;

public abstract class MusicPanelActivity extends MusicServiceActivity {
    public static final String TAG = "MusicPanelActivity";
//    @BindView(R.id.sliding_layout)
//    SlidingUpPanelLayout slidingUpPanelLayout;

    private int navigationbarColor;
    private int taskColor;
    private boolean lightStatusbar;

    protected NowPlayingScreen currentNowPlayingScreen;
    protected MiniPlayerFragment miniPlayerFragment;
    protected FragmentContainerView fragmentContainerView;

    private ValueAnimator navigationBarColorAnimator;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //noinspection ConstantConditions
//        miniPlayerFragment.getView().setOnClickListener(v -> goToPanel());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (currentNowPlayingScreen != PreferenceUtil.getInstance(this).getNowPlayingScreen()) {
//            postRecreate();
//        }
    }

    public void setAntiDragView(View antiDragView) {
//        slidingUpPanelLayout.setAntiDragView(antiDragView);
    }

    protected abstract View createContentView();

    @Override
    public void onQueueChanged() {
        super.onQueueChanged();
        hideBottomBar(MusicPlayerRemote.getPlayingQueue().isEmpty());
    }


    public void hideBottomBar(final boolean hide) {
//        if (hide) {
//            fragmentContainerView.
//        } else {
//            slidingUpPanelLayout.setPanelHeight(getResources().getDimensionPixelSize(R.dimen.mini_player_height));
//        }
    }

//    @Override
//    public void onBackPressed() {
//        if (!handleBackPress())
//            super.onBackPressed();
//    }

//    public boolean handleBackPress() {
//        if (slidingUpPanelLayout.getPanelHeight() != 0 && playerFragment.onBackPressed())
//            return true;
//        if (getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
//            collapsePanel();
//            return true;
//        }
//        return false;
//    }
    public void goToPanel() {
        Log.d(TAG, "goToPanel");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}