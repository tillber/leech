package com.leechdev.leech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DbHandlerTesting extends AppCompatActivity {

    DbHandler leechDB;
    EditText editHotspotName, editHotspotLat, editHotspotLong;
    Button btnAddHotspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_handler_testing);
        leechDB = new DbHandler(this);

        editHotspotName = findViewById(R.id.HotspotName);
        editHotspotLat = findViewById(R.id.HotspotLat);
        editHotspotLong = findViewById(R.id.HotspotLong);
        btnAddHotspot = findViewById(R.id.buttonAddHospot);
        AddHotspot();
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

}
