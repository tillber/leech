package com.leechdev.leech;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WifiTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_testing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listView = (ListView)findViewById(R.id.listView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ArrayList<HotspotItem> hotspots = getHotspots();
                CustomAdapter itemsAdapter =
                        new CustomAdapter(R.layout.hotspot_item, hotspots, getApplicationContext());
                listView.setAdapter(itemsAdapter);
            }
        });
    }


    private String getConnectionInfo(){
        WifiManager wmgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wmgr.getConnectionInfo();
        return info.toString();
    }

    //Retrieves information about nearby hotspots
    private ArrayList<HotspotItem> getHotspots(){
        WifiManager wmgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> availNetworks = wmgr.getScanResults();
        ArrayList<HotspotItem> hotspots = new ArrayList<>();

        for (ScanResult result : availNetworks) {
            String capabilities = result.capabilities;
            int level = WifiManager.calculateSignalLevel(result.level, 5);
            String security = "";

            for(HotspotItem item : hotspots){
                if(item.getSsid().equals(result.SSID)){
                    hotspots.remove(item);
                    break;
                }
            }

            if (capabilities.toUpperCase().contains("WEP")) {
                // WEP Network
                security = "Secured (WEP)";
            } else if (capabilities.toUpperCase().contains("WPA")
                    || capabilities.toUpperCase().contains("WPA2")) {
                // WPA or WPA2 Network
                security = "Secured (WPA/WPA2)";
            } else {
                // Open Network
                security = "Open";
            }

            HotspotItem hotspot = new HotspotItem(result.SSID, level, security);
            hotspots.add(hotspot);
        }

        Collections.sort(hotspots, new NameSorter());
        return hotspots;
    }
}
