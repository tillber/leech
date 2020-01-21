package com.leechdev.leech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 0xDEADBEEF;
    private static final double DEFAULT_ZOOM_LEVEL = 18.0;

    private MapView map = null;
    private IMapController mapController = null;
    private LocationManager locationManager = null;
    private boolean locationAccess = false;
    private double usrLongitude = 0.0;
    private double usrLatitude = 0.0;

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            usrLatitude = location.getLatitude();
            usrLongitude = location.getLongitude();

            GeoPoint usrLocation = new GeoPoint(usrLatitude, usrLongitude);

            if (mapController == null)
                mapController = map.getController();

            mapController.setZoom(DEFAULT_ZOOM_LEVEL);
            mapController.setCenter(usrLocation);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            return;
        }

        @Override
        public void onProviderEnabled(String provider) {
            return;
        }

        @Override
        public void onProviderDisabled(String provider) {
            return;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        /* TODO: load offline map */
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main);

        this.map = (MapView) findViewById(R.id.map);
        this.map.setTileSource(TileSourceFactory.MAPNIK);

        this.map.setMultiTouchControls(true);
        this.map.setBuiltInZoomControls(false); /* TODO: deprecated, so look for another way! */

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /* Request location permission unless already granted */
        this.locationService();

        if (!this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.showLocationAlert();
        }
    }

    public void onResume() {
        super.onResume();
        map.onResume();
    }

    public void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * Check if location access permission is granted, if not request, otherwise subscribe
     * for location changes.
     */
    private void locationService() {
        /* TODO: for now it has to be manually enabled, otherwise the app crashes for now... */
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    this.PERMISSION_REQUEST_CODE);
        } else {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, 10, this.locationListener);
        }
    }

    /**
     * Show location service alert dialog that takes the user directly to the
     * GPS settings.
     */
    private void showLocationAlert() {
        final AlertDialog.Builder locDialog = new AlertDialog.Builder(this);

        locDialog.setTitle("Enable location service")
                .setMessage("Your location settings is set to off.")
                .setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(settings);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { return; }
                });

        locDialog.show();
    }
}
