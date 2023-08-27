package com.eywa.myplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.eywa.myplant.toolbar.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
 * 상단 메뉴
 * 설정, qr코드 생성, 검색(?)
 *
 * 탭 (Bottom navigation bar)
 * 메인, 플래너, 추천&도감, 미션, 캐시샵(미정)
 *
 * 만들어야 할 페이지
 * 메인 - 식물 나열된 페이지, 각 식물 세부정보 + 공유,
 * 추천&도감 -
 * 미션 -
 *
 * 삭제 ###플래너 - 달력, 세부 일기입력(github), https://github.com/kizitonwose/Calendar###
 * 상단 - qr코드 페이지, 설정페이지,
 *
 * 부가기능
 * 센서 데이터 관련 알림
 *
 * 데이터베이스
 * 사용자명, 미션목록, 호감도,
 */

public class MainActivity extends AppCompatActivity {

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

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtain the NavController for NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            navController = null;
        }
        // Connect the BottomNavigationView to the NavController
        if (navController != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

    }
}