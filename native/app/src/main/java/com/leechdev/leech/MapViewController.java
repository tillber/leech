package com.leechdev.leech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.Bundle;
import android.provider.Settings;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import android.util.Log;

public class MapViewController implements LocationListener {
    private static final double MAP_DEFAULT_ZOOM = 18.0;

    private IMapController mapController;
    private org.osmdroid.views.MapView mapView;
    private Context ctx;

    private boolean permissionGranted = false;
    private boolean followTarget = true;
    private GeoPoint location = new GeoPoint(0d,0d);
    private Marker locationMarker;



    public MapViewController(Context ctx, org.osmdroid.views.MapView mapView) {
        this.ctx = ctx;
        this.mapView = mapView;
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.mapView.setMultiTouchControls(true);

        this.mapController = this.mapView.getController();
        this.mapController.setZoom(this.MAP_DEFAULT_ZOOM);

        this.locationMarker = new Marker(this.mapView);
        this.locationMarker.setIcon(this.ctx.getResources().getDrawable(R.drawable.ic_location_marker, null));
        this.locationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
        this.mapView.getOverlays().add(this.locationMarker);
    }

    public void setPermissionGranted(boolean permissionGranted) {
        this.permissionGranted = permissionGranted;
    }

    public void startLocationUpdates() {
        if (!this.permissionGranted)
            return;

        LocationManager lm = (LocationManager) this.ctx.getSystemService(this.ctx.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
        } catch (SecurityException e) {
            Log.e(this.getClass().getName(), e.toString());
        } catch (IllegalArgumentException e) {
            Log.e(this.getClass().getName(), e.toString());
        }
    }

    public void stopLocationUpdates() {
        if (!this.permissionGranted)
            return;

        LocationManager lm = (LocationManager) this.ctx.getSystemService(this.ctx.LOCATION_SERVICE);
        try {
            lm.removeUpdates(this);
        } catch (SecurityException e) {
            Log.e(this.getClass().getName(), e.toString());
        } catch (IllegalArgumentException e) {
            Log.e(this.getClass().getName(), e.toString());
        }
    }

    public void getLastKnownLocation() {
        LocationManager lm = (LocationManager) this.ctx.getSystemService(this.ctx.LOCATION_SERVICE);

        try {
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.onLocationChanged(loc);
        } catch (SecurityException e)  {
            Log.e(this.getClass().getName(), e.toString());
        } catch (IllegalArgumentException e) {
            Log.e(this.getClass().getName(), e.toString());
        }
    }

    public double getLongitude() { return this.location.getLongitude(); }
    public double getLatitude() { return this.location.getLatitude(); }

    public boolean setFollowTarget() { return this.followTarget; }
    public void setFollowTarget(boolean follow) { this.followTarget = follow; }

    public void onResume() {
        this.startLocationUpdates();
        this.mapView.onResume();
    }

    public void onPause() {
        this.stopLocationUpdates();
        this.mapView.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Log.d("GPS-LOCATION", "null");
            return;
        }

        Log.d("GPS-LOCATION", "lat: " + location.getLatitude() + " long: " + location.getLongitude());
        
        this.location.setCoords(location.getLatitude(), location.getLongitude());
        this.locationMarker.setPosition(this.location);
        this.mapController.animateTo(this.location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("GPS-LOCATION", provider + ": " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GPS-LOCATION", provider + ": enabled");
        this.getLastKnownLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("GPS-LOCATION", provider + ": disabled");
    }

}
