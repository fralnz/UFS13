package com.itsrizzoli.wikiava;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationManager {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    public static String getCurrentLocationString(Context context) {
        if (!hasLocationPermission(context)) {
            return "Location permissions not granted";
        }

        android.location.LocationManager locationManager =
                (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return "GPS is disabled";
        }

        Location location = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);

        if (location != null) {
            return location.getLatitude() + "," + location.getLongitude();
        } else {
            return "Location unavailable";
        }
    }

    public static boolean isGPSEnabled(Context context) {
        android.location.LocationManager locationManager =
                (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    public static String getAddressName(Context context, double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Address not found";
    }
}