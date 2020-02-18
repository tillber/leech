package com.leechdev.leech;

public class HotspotItem {
    String ssid;
    int level;
    String security;

    public HotspotItem(String ssid, int level, String security){
        this.ssid = ssid;
        this.level = level;
        this.security = security;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
}
