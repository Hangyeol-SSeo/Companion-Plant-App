package com.eywa.myplant.toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.eywa.myplant.MainActivity;
import com.eywa.myplant.R;
import com.eywa.myplant.databinding.FragmentMyPageBinding;

public class MyPageFragment extends Fragment {
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private FragmentMyPageBinding binding;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            // Hide the toolbar and bottom navigation bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null) {
            // Show the toolbar and bottom navigation bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMyPageBinding.inflate(inflater, container, false);
        String userName = fetchFromPreferences("userName");
        binding.mypageUsername.setText(userName);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button backButton = binding.mypageBackButton;
        backButton.setVisibility(View.GONE);

        final Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);

        binding.fullscreenContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButton.setVisibility(View.VISIBLE);
                backButton.startAnimation(fadeIn);
                mHideHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backButton.startAnimation(fadeOut);
                    }
                }, AUTO_HIDE_DELAY_MILLIS);
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                backButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, mainActivity.selectedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private String fetchFromPreferences(String key) {
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, ""); // Default value is an empty string
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
