package com.leechdev.leech;

import java.util.Comparator;

public class NameSorter implements Comparator<HotspotItem> {
    @Override
    public int compare (HotspotItem h1, HotspotItem h2){
        return h1.getSsid().compareToIgnoreCase((h2.getSsid()));
    }
}
