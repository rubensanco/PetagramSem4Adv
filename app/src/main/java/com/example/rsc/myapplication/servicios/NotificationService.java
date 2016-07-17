package com.example.rsc.myapplication.servicios;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.util.Log;
import android.view.Gravity;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService {
    public static final String TAG = "FIREBASE";
    public static final int NOTIFICATION_ID = 001;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Message Data: " + remoteMessage.getData().get("From"));



        //Intent i = new Intent(this, MainActivity.class);
        Intent iVerMiPerfil = new Intent();
        iVerMiPerfil.setAction("VER_MI_PERFIL");
        PendingIntent pendingIntentVerMiPerfil = PendingIntent.getBroadcast(this, 0, iVerMiPerfil, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent iDarFollow = new Intent();
        iDarFollow.setAction("DAR_FOLLOW");
        iDarFollow.putExtra("usuario",remoteMessage.getData().get("From"));
        PendingIntent pendingIntentDarFollow = PendingIntent.getBroadcast(this, 0, iDarFollow, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent iVerUsuario = new Intent();
        iVerUsuario.setAction("VER_USUARIO");
        iVerUsuario.putExtra("usuario",remoteMessage.getData().get("From"));
        PendingIntent pendingIntentVerUsuario = PendingIntent.getBroadcast(this, 0, iVerUsuario, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Action actionVerMiPerfil =
                new NotificationCompat.Action.Builder(R.drawable.ic_mimascota_aw,
                        getString(R.string.verMiPerfil), pendingIntentVerMiPerfil)
                        .build();

        NotificationCompat.Action actionDarFollow =
                new NotificationCompat.Action.Builder(R.drawable.ic_user_follow_aw,
                        getString(R.string.darFollow), pendingIntentDarFollow)
                        .build();

        NotificationCompat.Action actionVerUsuario =
                new NotificationCompat.Action.Builder(R.drawable.ic_ver_usuario_aw,
                        getString(R.string.verUsuario), pendingIntentVerUsuario)
                        .build();

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),
                                R.drawable.bk_androidwear_notification))
                        .setGravity(Gravity.CENTER_VERTICAL)
                ;





        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.dog_footprint_48)
                .setContentTitle("Notificación")
                .setContentText(remoteMessage.getNotification().getBody())
                .setSound(sonido)
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .extend(wearableExtender.addAction(actionVerMiPerfil))
                .extend(wearableExtender.addAction(actionDarFollow))
                .extend(wearableExtender.addAction(actionVerUsuario));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notification.build());
    }
}
