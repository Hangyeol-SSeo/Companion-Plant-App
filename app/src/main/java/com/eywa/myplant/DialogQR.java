package com.eywa.myplant;

import android.net.Uri;

import androidx.fragment.app.DialogFragment;

import com.eywa.myplant.DialogAddPlant;

public class DialogQR extends DialogFragment {
    private OnQRShowListener listener;

    public interface OnQRShowListener {
        void onQRShow(String username, String userId, String plantname, String PlantRealName);
    }

    public DialogQR(OnQRShowListener listener) {
        this.listener = listener;
    }
}
