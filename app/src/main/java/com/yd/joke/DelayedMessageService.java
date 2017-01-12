package com.yd.joke;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {
     public static final String EXTRA_MESSAGE = "intentService";

    public DelayedMessageService(){
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        synchronized (this){
            try{
                wait(10000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        String str = intent.getStringExtra(EXTRA_MESSAGE);
        showText(str);
    }

    private void showText(final String str){
        Log.v("DelayedService", str);
    }
}
