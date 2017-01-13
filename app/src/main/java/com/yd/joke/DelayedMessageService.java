package com.yd.joke;

import android.app.IntentService;
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
    }
}
