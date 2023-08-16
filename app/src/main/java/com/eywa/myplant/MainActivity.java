package com.eywa.myplant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
 * 플래너 - 달력, 세부 일기입력(github),
 * 추천&도감 - 
 * 미션 -
 * 상단 - qr코드 페이지, 설정페이지,
 *
 * 부가기능
 * 센서 데이터 관련 알림, 다크모드(?)
 *
 * 데이터베이스
 * 사용자명, 미션목록, 호감도,
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BottomNavigationView bottomNavigationView = binding.bnv;
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(botoomNavigationView, navHostFragment.getNavController());
    }
}