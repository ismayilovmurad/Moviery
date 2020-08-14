package com.muradismayilov.martiandeveloper.moviesaverapp.common.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.muradismayilov.martiandeveloper.moviesaverapp.R;
import com.muradismayilov.martiandeveloper.moviesaverapp.common.activity.SplashActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("unchecked")
class NotificationHelper extends ContextWrapper {
    private static final String channelID = "channelID";
    private static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        ArrayList randomNotificationList = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.random_notifications)));

        Random random = new Random();
        int i = random.nextInt(randomNotificationList.size());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Moviery")
                .setContentText("" + randomNotificationList.get(i))
                .setSmallIcon(R.drawable.ic_notification)
                .setOngoing(true);

        Intent notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setOngoing(false);

        return builder;
    }
}
