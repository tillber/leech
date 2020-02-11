package com.leechdev.leech;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class WifiTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_testing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView myTextView = (TextView)findViewById(R.id.textView2);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myTextView.setText(getInformation());
            }
        });
    }

    //Retrieves information about nearby hotspots
    private String getInformation(){
        WifiManager wmgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> availNetworks = wmgr.getScanResults();
        String info = "";

        if (availNetworks.size() > 0) {

            for (int i=0; i<availNetworks.size();i++) {
                info += availNetworks.get(i).toString() + "\n\n";
            }

        }

        return info;
    }
}
