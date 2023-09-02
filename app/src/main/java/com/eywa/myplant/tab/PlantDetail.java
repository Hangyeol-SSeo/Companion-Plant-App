package com.eywa.myplant.tab;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eywa.myplant.R;
import com.eywa.myplant.data.DatabaseHelper;
import com.eywa.myplant.tab.placeholder.PlaceholderContent;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantDetail extends AppCompatActivity {
    TextView intimacy, realname, nickname;
    CircleImageView plantImage;
    ImageButton qrButton, shareButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        String plantId = getIntent().getStringExtra("plantId");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        PlaceholderContent.PlaceholderItem plant = dbHelper.getPlantById(plantId);

        intimacy = findViewById(R.id.item_detail_intimacy);
        realname = findViewById(R.id.item_detail_realname);
        nickname = findViewById(R.id.item_detail_nickname);
        plantImage = findViewById(R.id.item_detail_image);
        qrButton = findViewById(R.id.toolbar_qr_button);
        shareButton = findViewById(R.id.toolbar_share_button);

        intimacy.setText("- " +  String.valueOf(plant.intimacy) + " -");
        realname.setText(plant.realname);
        nickname.setText(plant.nickname);
        plantImage.setImageURI(plant.plantImageUri);

        qrButton.setOnClickListener(v -> {

        });

        shareButton.setOnClickListener(v -> {

        });
    }
}
