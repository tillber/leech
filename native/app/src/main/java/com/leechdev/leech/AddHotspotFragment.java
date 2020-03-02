package com.leechdev.leech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AddHotspotFragment extends Fragment {

    DbHandler leechDB;
    EditText editHotspotName, editHotspotLat, editHotspotLong;
    Button btnAddHotspot, btnGetHotspots;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_hotspot, container, false);

        leechDB = new DbHandler(getActivity().getApplicationContext());

        editHotspotName = view.findViewById(R.id.HotspotName);
        editHotspotLat = view.findViewById(R.id.HotspotLat);
        editHotspotLong = view.findViewById(R.id.HotspotLong);
        btnAddHotspot = view.findViewById(R.id.buttonAddHospot);
        btnGetHotspots = view.findViewById(R.id.buttonGetHotspot);
        AddHotspot();

        return view;
        //// inflater.inflate(R.layout.fragment_add_hotspot, container, false);
    }

    public void AddHotspot(){
        btnAddHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean insertResult = leechDB.addHotspot(new Hotspot(editHotspotName.getText().toString(), editHotspotLat.getText().toString(), editHotspotLong.getText().toString()));

                if(insertResult == true){
                    Toast.makeText(getActivity().getApplicationContext(), "data inserted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
