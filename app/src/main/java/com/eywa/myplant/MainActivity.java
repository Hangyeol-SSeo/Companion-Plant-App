package com.eywa.myplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.eywa.myplant.tab.ArchiveFragment;
import com.eywa.myplant.tab.ItemFragment;
import com.eywa.myplant.tab.MissionFragment;
import com.eywa.myplant.tab.OnBackPressedListener;
import com.eywa.myplant.toolbar.DialogQR;
import com.eywa.myplant.toolbar.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
 * 상단 메뉴
 * 마이페이지 ok
 *
 * 탭 (Bottom navigation bar) ok
 * 메인, 추천&도감, 미션, 캐시샵(미정) ok
 *
 * 만들어야 할 페이지
 * 메인 - 식물 나열된 페이지, 각 식물 세부정보 + 공유, qr
 * 추천&도감 - ok
 * 미션 - ok
 *
 * 삭제 ###플래너 - 달력, 세부 일기입력(github), https://github.com/kizitonwose/Calendar### ok
 *
 * 부가기능
 * 센서 데이터 관련 알림
 *
 * 데이터베이스
 * 사용자명, 미션목록, 호감도, 등등 ok
 */

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    public Fragment selectedFragment = new ItemFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton mypageButton = findViewById(R.id.toolbar_mypage_button);
        mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyPageFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // tab
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        // Open the HomeFragment when the app starts
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new ItemFragment()).commit();
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Log.d("Main", item.getTitle().toString());

                    String itemName = item.getTitle().toString();
                    if (itemName.equals("Plant list")) {
                        selectedFragment = new ItemFragment();
                    } else if (itemName.equals("Archive")) {
                        selectedFragment = new ArchiveFragment();
                    } else if (itemName.equals("Mission")) {
                        selectedFragment = new MissionFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selectedFragment).commit();

                    return true;
                }
            };

    private OnBackPressedListener onBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}