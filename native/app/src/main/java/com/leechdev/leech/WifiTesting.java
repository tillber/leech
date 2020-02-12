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
import java.util.List;

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
                List<String> hotspots = getHotspots();
                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, hotspots);
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
    private ArrayList<String> getHotspots(){
        WifiManager wmgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> availNetworks = wmgr.getScanResults();
        ArrayList<String> hotspots = new ArrayList<>();
        String info = "";

        if (availNetworks.size() > 0) {
            for (int i=0; i<availNetworks.size();i++) {
                String capabilities = availNetworks.get(i).capabilities;
                int level = WifiManager.calculateSignalLevel(availNetworks.get(i).level, 5);
                String security = "";

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

                hotspots.add(availNetworks.get(i).SSID + ", level: " + level + ", security: " + security + "\n\n");
            }
        }

        return hotspots;
    }
}
