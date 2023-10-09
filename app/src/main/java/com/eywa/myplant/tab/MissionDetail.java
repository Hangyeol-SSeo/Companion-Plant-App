package com.eywa.myplant.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.eywa.myplant.R;

public class MissionDetail extends AppCompatActivity {
    TextView missionIcon, missionPoint, missionName, remainTime, intimacyView;
    Button pointButton;

    // TODO: 미션, 아카이브 DB만들기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_detail);

        missionIcon = findViewById(R.id.mission_detail_icon);
        missionPoint = findViewById(R.id.mission_detail_point);
        missionName = findViewById(R.id.mission_detail_name);
        remainTime = findViewById(R.id.mission_detail_time);
        intimacyView = findViewById(R.id.mission_detail_intimacy_point);
        pointButton = findViewById(R.id.mission_detail_button);


    }
}