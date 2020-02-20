package com.leechdev.leech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<HotspotItem>{
    private ArrayList<HotspotItem> nearbyHotspots;
    private int resourceLayout;
    Context context;

    private static class ViewHolder{
        TextView txtSSID;
        TextView txtLevel;
        TextView txtSecurity;
    }

    public CustomAdapter(int resourceLayout, ArrayList<HotspotItem> nearbyHotspots, Context context){
        super(context, R.layout.hotspot_item, nearbyHotspots);
        this.nearbyHotspots = nearbyHotspots;
        this.resourceLayout = resourceLayout;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(resourceLayout, null);
        }

        HotspotItem hotspotItem = getItem(position);

        if (hotspotItem != null) {
            TextView ssidText = (TextView) view.findViewById(R.id.ssid);
            ImageView levelImage = (ImageView) view.findViewById(R.id.level);
            TextView securityText = (TextView) view.findViewById(R.id.security);

            if (ssidText != null) {
                ssidText.setText(hotspotItem.getSsid());
            }

            if (levelImage != null) {
                switch(hotspotItem.getLevel()){
                    case 0:
                        levelImage.setImageResource(R.drawable.ic_wifi_level_100);
                        break;
                    case 1:
                        levelImage.setImageResource(R.drawable.ic_wifi_level_75);
                        break;
                    case 2:
                        levelImage.setImageResource(R.drawable.ic_wifi_level_50);
                        break;
                    case 3:
                        levelImage.setImageResource(R.drawable.ic_wifi_level_25);
                        break;
                    case 4:
                        levelImage.setImageResource(R.drawable.ic_wifi_level_0);
                        break;
                }
            }

            if (securityText != null) {
                securityText.setText(hotspotItem.getSecurity());
            }
        }

        return view;
    }
}
