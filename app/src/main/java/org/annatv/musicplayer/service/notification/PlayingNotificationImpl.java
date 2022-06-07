package org.annatv.musicplayer.service.notification;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import org.annatv.musicplayer.MainActivity;
import org.annatv.musicplayer.entity.Song;
import org.annatv.musicplayer.R;
import org.annatv.musicplayer.loader.AlbumLoader;
import org.annatv.musicplayer.loader.SongLoader;
import org.annatv.musicplayer.service.MusicService;

import static android.app.PendingIntent.FLAG_MUTABLE;

public class PlayingNotificationImpl extends PlayingNotification {

    @Override
    public synchronized void update() {
        stopped = false;

        final Song song = service.getCurrentSong();

        final boolean isPlaying = service.isPlaying();

        final int playButtonResId = isPlaying
                ? R.drawable.ic_pause_white_24dp : R.drawable.ic_play_arrow_white_24dp;

        Intent action = new Intent(service, MainActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent clickIntent = PendingIntent.getActivity(service, 0, action, FLAG_MUTABLE);

        final ComponentName serviceName = new ComponentName(service, MusicService.class);
        Intent intent = new Intent(MusicService.ACTION_QUIT);
        intent.setComponent(serviceName);
        final PendingIntent deleteIntent = PendingIntent.getService(service, 0, intent, FLAG_MUTABLE);

        final int bigNotificationImageSize = service.getResources().getDimensionPixelSize(R.dimen.notification_big_image_size);

        Bitmap bitmap = BitmapFactory.decodeFile(AlbumLoader.getAlbumArt(service, song.albumId));
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(service.getResources(), R.drawable.default_album_art);
        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(playButtonResId,
                service.getString(R.string.action_play_pause),
                retrievePlaybackAction(MusicService.ACTION_TOGGLE_PAUSE));
        NotificationCompat.Action previousAction = new NotificationCompat.Action(R.drawable.ic_skip_previous_white_24dp,
                service.getString(R.string.action_previous),
                retrievePlaybackAction(MusicService.ACTION_REWIND));
        NotificationCompat.Action nextAction = new NotificationCompat.Action(R.drawable.ic_skip_next_white_24dp,
                service.getString(R.string.action_next),
                retrievePlaybackAction(MusicService.ACTION_SKIP));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setSubText(song.albumName)
                .setLargeIcon(bitmap)
                .setContentIntent(clickIntent)
                .setDeleteIntent(deleteIntent)
                .setContentTitle(song.title)
                .setContentText(song.artistName)
                .setOngoing(isPlaying)
                .setShowWhen(false)
                .addAction(previousAction)
                .addAction(playPauseAction)
                .addAction(nextAction)
                .setStyle(new MediaStyle().setMediaSession(service.getMediaSession().getSessionToken()).setShowActionsInCompactView(0, 1, 2))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if (stopped)
            return; // notification has been stopped before loading was finished
        updateNotifyModeAndPostNotification(builder.build());
    }

    private PendingIntent retrievePlaybackAction(final String action) {
        final ComponentName serviceName = new ComponentName(service, MusicService.class);
        Intent intent = new Intent(action);
        intent.setComponent(serviceName);
        return PendingIntent.getService(service, 0, intent, FLAG_MUTABLE);
    }
}
