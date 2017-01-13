package com.yd.joke;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class OdometrService extends Service {

    //interface Binder
    private final IBinder mIBinder = new OdometrBinder();

    public class OdometrBinder extends Binder {
        OdometrService getOdometr(){
            return OdometrService.this;
        }
    }

    public OdometrService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
