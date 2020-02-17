package com.leechdev.leech;

import android.app.Activity;
import android.app.AlertDialog;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Build;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;

//import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final int PERMISSION_MULTI_REQUEST = 0xBEEF;
    private static String[] permissions;
    private static boolean permissionGranted = false;

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

        Context ctx = getApplicationContext();

        this.permissions = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        // Request all permissions
        if (Build.VERSION.SDK_INT >= 23)
            this.requestPermissions(this.permissions, this.PERMISSION_MULTI_REQUEST);

        //inflate and create the map
        setContentView(R.layout.activity_main);
    }

    public void onResume() {
        super.onResume();
        //map.onResume();
    }

    public void onPause() {
        super.onPause();
        //map.onPause();
    }
}
