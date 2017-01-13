package com.yd.joke;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class OdometrService extends Service {

    //interface Binder
    public final IBinder mIBinder = new OdometrBinder();

    private static double mDistance;
    private static Location mLastLocation = null;

    public class OdometrBinder extends Binder {
        OdometrService getOdometr(){
            return OdometrService.this;
        }
    }

    public OdometrService() {
    }

    @Override
    public void onCreate(){
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(mLastLocation == null){
                    mLastLocation = location;
                }
                mDistance += location.distanceTo(mLastLocation);
                mLastLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //don't use
            }

            @Override
            public void onProviderEnabled(String s) {
                //don't use
            }

            @Override
            public void onProviderDisabled(String s) {
                //don't use
            }
        };

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //here we NEED TO CHECK PERMISSIONS API 21+
        try{
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
        }catch (SecurityException ex){
            Log.v("OdometrService", "Error: " + ex.getMessage());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public double getMiles() {
        return mDistance / 1609.344;    //retard units (miles)
    }
}
