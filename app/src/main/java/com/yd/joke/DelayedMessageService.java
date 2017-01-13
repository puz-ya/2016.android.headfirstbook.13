package com.yd.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {
     public static final String EXTRA_MESSAGE = "intentService";
    private Handler mHandler;
    private static final int NOTIFICATION_ID = 5000;

    public DelayedMessageService(){
        super("DelayedMessageService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        synchronized (this){
            try{
                wait(5000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        String str = intent.getStringExtra(EXTRA_MESSAGE);
        showText(str);
    }

    private void showText(final String str){
        Log.v("DelayedService", str);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Service is running", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(this, MainActivity.class);

        //creating builder for PendingIntent and Back-button
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)             //icon
                .setContentTitle(getString(R.string.app_name))  //title
                .setAutoCancel(true)                            //disappear on click
                .setPriority(Notification.PRIORITY_MAX)         //show this
                .setDefaults(Notification.DEFAULT_VIBRATE)      //vibrate
                .setContentIntent(pIntent)                      //delayed intent on click
                .setContentText("TEXT")                         //text inside (full view)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}
