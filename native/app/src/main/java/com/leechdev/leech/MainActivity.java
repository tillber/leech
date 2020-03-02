package com.leechdev.leech;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_MULTI_REQUEST = 0xBEEF;
    private static final String[] permissions = new String[] {
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static boolean permissionGranted = false;
    private static MapViewController mapViewController;

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] grants) {
        if (reqCode != this.PERMISSION_MULTI_REQUEST)
            return;

        // interaction canceled
        if (permissions.length == 0)
            return;

        // shall the world burn to ashes if this happens
        if (permissions.length != grants.length)
            return;

        for (int i = 0; i < grants.length; ++i)
            if (grants[i] != PackageManager.PERMISSION_GRANTED)
                return;

        this.permissionGranted = true;

        this.mapViewController.setPermissionGranted(true);
        this.mapViewController.startLocationUpdates();

        return;
    }

    private void checkPermissionsGranted() {
        if (this.permissionGranted)
            return;

        new AlertDialog.Builder(this.getApplicationContext())
            .setTitle("O-oh...")
            .setMessage("Leech needs permissions to function.")
            .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        settings.setData(Uri.parse("package:" + getPackageName()));

                        startActivity(settings);
                    }
                })
            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
            .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set OSMDroid configuration before the view is inflated.
        Context ctx = this.getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);

        // Request all permissions
        if (Build.VERSION.SDK_INT >= 23)
            this.requestPermissions(this.permissions, this.PERMISSION_MULTI_REQUEST);

        //create the bottom menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //create listener for clicked items in the menu
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Create the map
        this.mapViewController = new MapViewController(this, (org.osmdroid.views.MapView)findViewById(R.id.map));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case(R.id.nav_add_hotspot):
                    GeoPoint curLoc = mapViewController.getCurrentLocation();
                    selectedFragment = new AddHotspotFragment(curLoc.getLatitude(), curLoc.getLongitude());
                    break;
                case(R.id.nav_map):
                    selectedFragment = new MapBackFragment();
                    mapViewController.loadHotspotPois();
                    mapViewController.homeOnLocation();
                    break;

                case(R.id.nav_nearby):
                    selectedFragment = new NearbyHotspotsFragment();
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    public void onResume() {
        super.onResume();
        mapViewController.onResume();
    }

    public void onPause() {
        super.onPause();
        mapViewController.onPause();
    }
}
