package com.example.binaya.kuclassroom;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;


public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0){
            Map<String, String> payload = remoteMessage.getData();

            showNotification(payload);
        }
    }


    //    This can occur when an app's servers send a bunch of non-collapsible messages to FCM servers while the device is offline.
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(Map<String, String> payload) {
        String GROUP_KEY = "com.example.binaya.classroomnotifier";
        String CHANNEL_ID = "NOTICE_UPDATES";
        int numMessages = 0;
        int notificationID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Notice")
                .setContentText("Category: " + payload.get("category"))
                .setNumber(++numMessages)   //Number display
                .setGroup(GROUP_KEY) //Group Notification
                .setGroupSummary(true) //Group Notification Summary
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) //Visibility on the Locked Home Screen
                .setLights(Color.MAGENTA, 500,500)  //Led Flash
                .setStyle(new NotificationCompat.BigTextStyle().bigText(payload.get("title") + "\n" + "Category: " + payload.get("category")))
                .setDefaults(Notification.DEFAULT_VIBRATE) //Default Vibrate Pattern
                .setDefaults(Notification.DEFAULT_SOUND);   //Default Notification Sound

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(notificationID, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, mBuilder.build());
    }


}
