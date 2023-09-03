package com.eywa.myplant.tab;

import static com.eywa.myplant.Global.SERVER_URL;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eywa.myplant.MainActivity;
import com.eywa.myplant.R;
import com.eywa.myplant.data.DatabaseHelper;
import com.eywa.myplant.tab.placeholder.PlaceholderContent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.w3c.dom.Text;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlantDetail extends AppCompatActivity {
    private TextView intimacy, realname, nickname;
    private CircleImageView plantImage;
    private ImageButton qrButton, shareButton;
    private ToggleButton lightButton, alarmButton;
    private TextView light_intensity, soil_moisture, temperature;
    private BluetoothAdapter bluetoothAdapter;
    private OkHttpClient client = new OkHttpClient();


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
        lightButton = findViewById(R.id.item_detail_switch_light);
        alarmButton = findViewById(R.id.item_detail_switch_alarm);
        light_intensity = findViewById(R.id.item_detail_light_figure);
        soil_moisture = findViewById(R.id.item_detail_moisture_figure);
        temperature = findViewById(R.id.item_detail_temperature_figure);

        intimacy.setText("- " +  String.valueOf(plant.intimacy) + " -");
        realname.setText(plant.realname);
        nickname.setText(plant.nickname);
        plantImage.setImageURI(plant.plantImageUri);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        qrButton.setOnClickListener(v -> {
            showQRCodeDialog();
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "반려식물의 근황을 공유해보세요.");
            startActivity(Intent.createChooser(shareIntent, "공유하기"));
        });

        lightButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Boolean status = isChecked ? true : false;
            Toast.makeText(PlantDetail.this, "Toggle is " + status, Toast.LENGTH_SHORT).show();
            sendStatusToServer(status);
        });

        alarmButton.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
    }

    private void showQRCodeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_qr);
        ImageView imageView = dialog.findViewById(R.id.qr_image);
        imageView.setImageBitmap(generateQRCode());
        dialog.show();
    }

    private Bitmap generateQRCode() {
        String bluetoothAddress = bluetoothAdapter.getAddress();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(bluetoothAddress, BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
            for (int x = 0; x < 400; x++) {
                for (int y = 0; y < 400; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendStatusToServer(boolean status) {
        String url = SERVER_URL + "/state?status=" + status;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(PlantDetail.this, "Status updated successfully", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(PlantDetail.this, "Failed to update status", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
