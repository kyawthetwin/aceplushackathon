package hvc.com.aceplushackathonproject.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import hvc.com.aceplushackathonproject.MainActivity;
import hvc.com.aceplushackathonproject.R;

/**
 * Created by kyawthetwin on 9/20/18.
 */

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private int numMessages;
    private FirebaseAuth auth;

    @Override
    public void onCreate() {
        super.onCreate();
        auth = FirebaseAuth.getInstance();
    }

    ArrayList<String> newMessage = new ArrayList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (auth.getCurrentUser() != null) {
            getNotification(remoteMessage);
        }
    }

    public void getNotification(RemoteMessage message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(message.getNotification().getTitle());
        builder.setNumber(++numMessages);
        builder.setShowWhen(true);
        builder.setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(message.getNotification().getBody()));
        builder.setContentText(message.getNotification().getBody());

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("message",message.getNotification().getBody());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(0, builder.build());
        }
    }
}
