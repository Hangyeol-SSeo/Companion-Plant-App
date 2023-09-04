package com.eywa.myplant.tab;

import static com.eywa.myplant.Global.PREFERENCES_NAME;
import static com.eywa.myplant.Global.SERVER_URL;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlantDetail extends AppCompatActivity {
    public static final String TIME = "recordedTime";
    public static final String BASE_URL = SERVER_URL + "/data" + "?plantId=";
    private TextView intimacy, realname, nickname;
    private CircleImageView plantImage;
    private ImageButton qrButton, shareButton;
    private ToggleButton lightButton, alarmButton;
    private TextView light_intensity, soil_moisture, temperature;
    private TextView updateTime;
    private Button updateButton;
    private BluetoothAdapter bluetoothAdapter;
    private OkHttpClient client = new OkHttpClient();
    private SharedPreferences sharedPreferences;


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
        updateTime = findViewById(R.id.item_detail_update_clock);
        updateButton = findViewById(R.id.item_detail_update_button);

        intimacy.setText("- " +  String.valueOf(plant.intimacy) + " -");
        realname.setText(plant.realname);
        nickname.setText(plant.nickname);
        plantImage.setImageURI(plant.plantImageUri);
        temperature.setText("온도: " + plant.temperature);
        soil_moisture.setText("수분: " + plant.soil_moisture);
        light_intensity.setText("일조량: " + plant.light_intensity);

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
            Toast.makeText(PlantDetail.this, "식물 생장 조명 " + status, Toast.LENGTH_SHORT).show();
            sendStatusToServer(status);
        });

        alarmButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(PlantDetail.this, "알림 ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PlantDetail.this, "알림 OFF.", Toast.LENGTH_SHORT).show();
            }
        });

        // update time 표시
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        // Load the saved time
        String savedTime = sharedPreferences.getString(TIME, "기록이 없습니다.");
        updateTime.setText(savedTime);

        updateButton.setOnClickListener(v -> {
            String url = BASE_URL + plantId;
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            float light_intensity = (float) jsonObject.getDouble("light_intensity");
                            float soil_moisture = (float) jsonObject.getDouble("soil_moisture");
                            float temperature = (float) jsonObject.getDouble("temperature");
                            float humidity = (float) jsonObject.getDouble("humidity");

                            dbHelper.updateDataByPlantId(plantId, light_intensity, soil_moisture, temperature, humidity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recreate(); // 액티비티를 다시 시작합니다.
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });

            updateAndSaveCurrentTime();
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
                .post(RequestBody.create(null, new byte[0]))
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

    private void updateAndSaveCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // Save the time to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TIME, currentTime);
        editor.apply();

        updateTime.setText(currentTime);
    }
}
