package com.carbranders.carbranders;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jacques on 01/02/2016.
 */
public class GeoLoc implements LocationListener{

    double latitude = -1.0, longitude = -1.0;
    boolean pos_updated = false;

    @Override
    public void onLocationChanged(Location locFromGps) {
        // called when the listener is notified with a location update from the GPS
        latitude = locFromGps.getLatitude();
        longitude = locFromGps.getLongitude();
        pos_updated = true;
    }

    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
    }


    public void updateMyCurrentLoc(Location location) {


    }

    public double latitude_read()
    {
        return latitude;
    }

    public double longitude_read()
    {
        return longitude;
    }

    public void position_get(double []val)
    {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyMMddHHmmss");
        val[0] = Double.parseDouble(f.format(d));
        val[1] = latitude;
        val[2] = longitude;
        pos_updated = false;
    }

    public boolean position_updated()
    {
        return pos_updated;
    }

}
