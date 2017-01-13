package com.yd.joke;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivityNew extends Activity {

    private OdometrService mOdometrService;
    private boolean mIsBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        checkMiles();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(this, OdometrService.class);
        //create if not exist and BIND
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            OdometrService.OdometrBinder odometrBinder = (OdometrService.OdometrBinder) iBinder;
            mOdometrService = odometrBinder.getOdometr();
            mIsBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsBind = false;
        }
    };

    private void checkMiles(){
        final TextView textView = (TextView) findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if(mOdometrService != null){
                    distance = mOdometrService.getMiles();
                }

                String string = String.format(Locale.getDefault(), "%1$,.2f miles", distance);
                textView.setText(string);
                handler.postDelayed(this, 1000); //every second check distance
            }
        });
    }

    @Override
    public void onStop(){
        super.onStop();

        if(mIsBind){
            unbindService(mServiceConnection);
            mIsBind = false;
        }
    }
}
