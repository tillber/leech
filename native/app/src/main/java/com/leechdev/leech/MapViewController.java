package com.leechdev.leech;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.util.Log;

public class MapViewController {
    private static final double MAP_DEFAULT_ZOOM = 18.0;
    private static Activity parent;

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
        public void onProviderDisabled(String provider) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };


    public MapViewController(Activity parent, MapView mapView) {
        this.parent = parent;

        this.mapView = mapView;
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.mapView.setMultiTouchControls(true);

        this.mapController = this.mapView.getController();
        this.mapController.setZoom(this.MAP_DEFAULT_ZOOM);

        this.locationMgr = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);
    }

    double getLongitude() { return this.longitude; }
    double getLatitude() { return this.latitude; }

    boolean setFollowTarget() { return this.followTarget; }
    void setFollowTarget(boolean follow) { this.followTarget = follow; }

    void onResume() { this.mapView.onResume(); }
    void onPause() { this.mapView.onPause(); }
}
