package com.leechdev.leech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DbHandlerTesting extends AppCompatActivity {

    DbHandler leechDB;
    EditText editHotspotName, editHotspotLat, editHotspotLong;
    Button btnAddHotspot, btnGetHotspots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_handler_testing);
        leechDB = new DbHandler(this);

        editHotspotName = findViewById(R.id.HotspotName);
        editHotspotLat = findViewById(R.id.HotspotLat);
        editHotspotLong = findViewById(R.id.HotspotLong);
        btnAddHotspot = findViewById(R.id.buttonAddHospot);
        btnGetHotspots = findViewById(R.id.buttonGetHotspot);
        AddHotspot();
        getAllHotspots();
    }

    public void AddHotspot(){
        btnAddHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean insertResult = leechDB.addHotspot(new Hotspot(editHotspotName.getText().toString(), editHotspotLat.getText().toString(), editHotspotLong.getText().toString()));

                if(insertResult == true){
                    Toast.makeText(DbHandlerTesting.this, "data inserted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DbHandlerTesting.this, "data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getAllHotspots(){
        btnGetHotspots.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Hotspot> hotspotList = leechDB.getAllHotspots();
                        StringBuffer buffer = new StringBuffer();
                        if(hotspotList.isEmpty()){
                            showMessage("error", "no data found");
                            return;
                        }else{
                            showMessage("success", "data found");
                            for(Hotspot hotspot : hotspotList){
                                buffer.append("Hotspot name: " +hotspot.getHotspot_name() +"\n");
                                buffer.append("Hotspot latitude: " +hotspot.getHotspot_lat() +"\n");
                                buffer.append("Hotspot longitude: " +hotspot.getHotspot_long() +"\n");
                            }
                            showMessage("Data", buffer.toString());
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
