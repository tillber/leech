package com.leechdev.leech;

public class Hotspot {

    private String TABLE_NAME = "hotspot_table";

    private String COLUMN_ID = "hotspot_id";
    private String COLUMN_NAME = "hotspot_name";
    private String COLUMN_LOCATION_LAT = "hotspot_location_lat";
    private String COLUMN_LOCATION_LONG = "hotspot_location_long";
    private String COLUMN_AVGSPEED = "hotspot_AVGSpeed";

    private int hotspot_id;
    private String hotspot_name;
    private String hotspot_lat;
    private String hotspot_long;
    private int hotspot_AVGSpeed;

    public Hotspot(String name, String hotspot_lat, String hotspot_long){
        this.hotspot_name = name;
        this.hotspot_lat = hotspot_lat;
        this.hotspot_long = hotspot_long;
    }

    public int getHotspot_id() {
        return hotspot_id;
    }

    public String getHotspot_name() {
        return hotspot_name;
    }

    public String getHotspot_lat() {
        return hotspot_lat;
    }

    public String getHotspot_long() {
        return hotspot_long;
    }

    public int getHotspot_AVGSpeed() {
        return hotspot_AVGSpeed;
    }

    public void setHotspot_id(int hotspot_id){
        this.hotspot_id = hotspot_id;
    }

    public void setHotspot_name(String hotspot_name) {
        this.hotspot_name = hotspot_name;
    }

    public void setHotspot_lat(String hotspot_lat) {
        this.hotspot_lat = hotspot_lat;
    }

    public void setHotspot_long(String hotspot_long) {
        this.hotspot_long = hotspot_long;
    }

    public void setHotspot_AVGSpeed(int hotspot_AVGSpeed) {
        this.hotspot_AVGSpeed = hotspot_AVGSpeed;
    }
}
