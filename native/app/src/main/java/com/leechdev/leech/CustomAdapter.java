package com.leechdev.leech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            TextView tt1 = (TextView) view.findViewById(R.id.ssid);
            TextView tt2 = (TextView) view.findViewById(R.id.level);
            TextView tt3 = (TextView) view.findViewById(R.id.security);

            if (tt1 != null) {
                tt1.setText(hotspotItem.getSsid());
            }

            if (tt2 != null) {
                tt2.setText(String.valueOf(hotspotItem.getLevel()));
            }

            if (tt3 != null) {
                tt3.setText(hotspotItem.getSecurity());
            }
        }

        return view;
    }
}
