//package org.annatv.musicplayer.ui.player.player;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.MenuItem;
//import android.view.View;
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//import org.annatv.musicplayer.R;
//import org.annatv.musicplayer.entity.Song;
//import org.annatv.musicplayer.helper.MusicPlayerRemote;
//import org.annatv.musicplayer.util.MusicUtil;
//
//public abstract class PlayerFragment extends MusicServiceFragment implements Toolbar.OnMenuItemClickListener {
//
//    private Callbacks callbacks;
//    private static boolean isToolbarShown = true;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            callbacks = (Callbacks) context;
//        } catch (ClassCastException e) {
//            throw new RuntimeException(context.getClass().getSimpleName() + " must implement " + Callbacks.class.getSimpleName());
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        callbacks = null;
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        return false;
//    }
//
//    protected void toggleFavorite(Song song) {
//        MusicUtil.toggleFavorite(getActivity(), song);
//    }
//
//    protected boolean isToolbarShown() {
//        return isToolbarShown;
//    }
//
//    protected void setToolbarShown(boolean toolbarShown) {
//        isToolbarShown = toolbarShown;
//    }
//
//    protected void showToolbar(@Nullable final View toolbar) {
//        if (toolbar == null) return;
//
//        setToolbarShown(true);
//
//        toolbar.setVisibility(View.VISIBLE);
//        toolbar.animate().alpha(1f).setDuration(PlayerAlbumCoverFragment.VISIBILITY_ANIM_DURATION);
//    }
//
//    protected void hideToolbar(@Nullable final View toolbar) {
//        if (toolbar == null) return;
//
//        setToolbarShown(false);
//
//        toolbar.animate().alpha(0f).setDuration(PlayerAlbumCoverFragment.VISIBILITY_ANIM_DURATION).withEndAction(() -> toolbar.setVisibility(View.GONE));
//    }
//
//    protected void toggleToolbar(@Nullable final View toolbar) {
//        if (isToolbarShown()) {
//            hideToolbar(toolbar);
//        } else {
//            showToolbar(toolbar);
//        }
//    }
//
//    protected void checkToggleToolbar(@Nullable final View toolbar) {
//        if (toolbar != null && !isToolbarShown() && toolbar.getVisibility() != View.GONE) {
//            hideToolbar(toolbar);
//        } else if (toolbar != null && isToolbarShown() && toolbar.getVisibility() != View.VISIBLE) {
//            showToolbar(toolbar);
//        }
//    }
//
//    protected String getUpNextAndQueueTime() {
//        final long duration = MusicPlayerRemote.getQueueDurationMillis(MusicPlayerRemote.getPosition());
//
//        return MusicUtil.buildInfoString(
//                getResources().getString(R.string.up_next),
//                MusicUtil.getTotalDurationString(duration / 1000)
//        );
//    }
//
//    public abstract void onShow();
//
//    public abstract void onHide();
//
//    public abstract boolean onBackPressed();
//
//    public Callbacks getCallbacks() {
//        return callbacks;
//    }
//
//    public interface Callbacks {
//        void onPaletteColorChanged();
//    }
//}
