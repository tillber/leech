package com.leechdev.leech;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.util.Log;

public class MapViewController {
    private static final double MAP_DEFAULT_ZOOM = 18.0;

    private LocationManager locationMgr;
    private IMapController mapController;
    private MapView mapView;

    private boolean followTarget = true;
    private double longitude, latitude;

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            longitude = loc.getLongitude();
            latitude = loc.getLatitude();

            Log.d("GPS", "long: " + longitude + " lat: " + latitude);

            if (followTarget) {
                GeoPoint pos = new GeoPoint(latitude, longitude);
                mapController.setCenter(pos);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("GPS", "DISABLED");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("GPS", "ENABLED");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("GPS", "CHANGED");
        }
    };


    public MapViewController(Activity act, MapView mapView) {
        this.mapView = mapView;
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.mapView.setMultiTouchControls(true);

        this.mapController = this.mapView.getController();
        this.mapController.setZoom(this.MAP_DEFAULT_ZOOM);

        this.locationMgr = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
    }

    public void requestLocation(final Activity act) {
      //if (!this.locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      //    new AlertDialog.Builder(act.getApplicationContext())
      //        .setTitle("Enable location")
      //        .setMessage("Your GPS is turned off.")
      //        .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
      //                @Override
      //                public void onClick(DialogInterface dialog, int which) {
      //                    Intent gpsSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
      //             parent    act.startActivity(gpsSettings);
      //                }
      //            })
      //        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      //                @Override
      //                public void onClick(DialogInterface dialog, int which) {
      //                    dialog.cancel();
      //                }
      //            })
      //        .show();
      //}

        try {
        	this.locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this.locationListener);
          //Location loc = this.locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
          //this.mapController.setCenter(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
        } catch (SecurityException e) {

        }
    }

    public double getLongitude() { return this.longitude; }
    public double getLatitude() { return this.latitude; }

    public boolean setFollowTarget() { return this.followTarget; }
    public void setFollowTarget(boolean follow) { this.followTarget = follow; }

    public void onResume() {
        this.mapView.onResume();
    }

    public void onPause() {
        this.mapView.onPause();
    }
}
