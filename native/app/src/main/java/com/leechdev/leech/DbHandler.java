package com.leechdev.leech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "leech.db";
    private static final String TABLE_NAME = "hotspot_table";
    private static final String COLUMN_NAME1 = "id";
    private static final String COLUMN_NAME2 = "hotspot_name";
    private static final String COLUMN_NAME3 = "hotspot_lat";
    private static final String COLUMN_NAME4 = "hotspot_long";
    private static final String COLUMN_NAME5 = "hotspot_AVGSpeed";

    public DbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE " +TABLE_NAME +"(" +COLUMN_NAME1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +COLUMN_NAME2 +" TEXT NOT NULL, " +COLUMN_NAME3 +" TEXT NOT NULL, " +COLUMN_NAME4 +" TEXT NOT NULL, " +COLUMN_NAME5 +" INTEGER)");
        db.execSQL("CREATE TABLE hotspot_table (id INTEGER PRIMARY KEY, hotspot_name TEXT NOT NULL, hotspot_lat TEXT NOT NULL, hotspot_long TEXT NOT NULL, hotspot_avgspeed INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Hotspot> getAllHotspots(){
        ArrayList<Hotspot> result = new ArrayList<Hotspot>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " +TABLE_NAME, null);

        while(cursor.moveToNext()){
            Hotspot hotspot = new Hotspot(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            hotspot.setHotspot_id(cursor.getInt(0));
            hotspot.setHotspot_AVGSpeed( cursor.getInt(4));
            result.add(hotspot);
        }

        cursor.close();
        return result;
    }

    public boolean addHotspot(Hotspot hotspot){

        SQLiteDatabase db = this.getWritableDatabase();

        //String query = "INSERT INTO hotspot_table (hotspot_name, hotspot_lat, hotspot_long) VALUES ('" +hotspot.getHotspot_name() +"', '" +hotspot.getHotspot_lat() +"', '" +hotspot.getHotspot_long() +"')";

        ContentValues HotspotValues = new ContentValues();
        HotspotValues.put(COLUMN_NAME2, hotspot.getHotspot_name().toString());
        HotspotValues.put(COLUMN_NAME3, hotspot.getHotspot_lat().toString());
        HotspotValues.put(COLUMN_NAME4, hotspot.getHotspot_long().toString());

        long result = db.insert(TABLE_NAME, null, HotspotValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }



}
