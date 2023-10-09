package com.eywa.myplant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import com.eywa.myplant.data.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DialogAddPlant extends DialogFragment {
    private OnPlantAddedListener listener;
    private CropImageView plantImage;
    private Uri selectedImageUri;

    public interface OnPlantAddedListener {
        void onPlantAdded(String nickname, String realName, Uri plantImageUri);
    }

    public DialogAddPlant(OnPlantAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_plant, null);

        EditText nicknameEditText = view.findViewById(R.id.item_add_nickname);
        Spinner realnameSpinner = view.findViewById(R.id.item_add_realname);
        plantImage = (CropImageView) view.findViewById(R.id.item_add_plant_image);
        Button selectImageButton = view.findViewById(R.id.item_add_select_image_button);
        Button cropImageButton = view.findViewById(R.id.item_add_crop_image_button);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });

        cropImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plantImage.getImageUri() == null) {
                    // 이미지가 선택되지 않았을 때
                    Toast.makeText(getActivity(), "이미지를 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap cropped = plantImage.getCroppedImage();
                plantImage.setImageBitmap(cropped);

                // 고유한 파일명 생성
                String fileName = "cropped_" + System.currentTimeMillis() + ".jpg";
                File croppedFile = new File(getActivity().getExternalFilesDir(null), fileName);

                // 기존 파일 삭제
                if (croppedFile.exists()) {
                    croppedFile.delete();
                }

                // Save the cropped image to a file
                try (FileOutputStream out = new FileOutputStream(croppedFile)) {
                    cropped.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Get the Uri of the cropped image file
                selectedImageUri = Uri.fromFile(croppedFile);
            }
        });

        List<String> plantNamesKor = dbHelper.getAllPlantNamesKor(dbHelper.getReadableDatabase());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, plantNamesKor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        realnameSpinner.setAdapter(adapter);

        builder.setView(view)
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String nickname = nicknameEditText.getText().toString();
                    String realname = realnameSpinner.getSelectedItem().toString();
                    listener.onPlantAdded(nickname, realname, selectedImageUri);
                })
                .setNegativeButton("Cancel", (dialog, id) -> DialogAddPlant.this.getDialog().cancel());

        return builder.create();
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri originalUri) {
                    if (originalUri != null) {
                        selectedImageUri = originalUri;
                        plantImage.setImageUriAsync(originalUri);
                        plantImage.setAspectRatio(1, 1);
                    }
                }
            });
}
