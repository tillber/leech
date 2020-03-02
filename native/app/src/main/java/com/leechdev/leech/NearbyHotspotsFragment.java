package com.leechdev.leech;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NearbyHotspotsFragment extends Fragment {
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_nearby_hotspots, container, false);
        final ListView listView = (ListView)view.findViewById(R.id.listView);
        ArrayList<HotspotItem> hotspots = getHotspots();
        CustomAdapter itemsAdapter =
                new CustomAdapter(R.layout.hotspot_item, hotspots, getActivity().getApplicationContext());
        listView.setAdapter(itemsAdapter);
        return view;
    }

    //Retrieves information about nearby hotspots
    private ArrayList<HotspotItem> getHotspots(){
        WifiManager wmgr = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
