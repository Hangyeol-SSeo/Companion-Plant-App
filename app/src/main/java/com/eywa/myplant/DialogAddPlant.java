package com.eywa.myplant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class DialogAddPlant extends DialogFragment {
    private OnPlantAddedListener listener;

    public interface OnPlantAddedListener {
        void onPlantAdded(String nickname, String realName);
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
        EditText realnameEditText = view.findViewById(R.id.item_add_realname);

        builder.setView(view)
                .setPositiveButton("Confirm", (dialog, id) -> {
                    String nickname = nicknameEditText.getText().toString();
                    String realname = realnameEditText.getText().toString();
                    listener.onPlantAdded(nickname, realname);
                })
                .setNegativeButton("Cancel", (dialog, id) -> DialogAddPlant.this.getDialog().cancel());

        return builder.create();
    }
}
