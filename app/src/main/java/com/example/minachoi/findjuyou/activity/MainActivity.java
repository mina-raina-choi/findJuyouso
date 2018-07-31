package com.example.minachoi.findjuyou.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.minachoi.findjuyou.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  현재위치 받아와서 기본값 세팅!?!
        //  옵션메뉴에서 설정페이지로 이동
        // 거리(3km, 5km), 기름종류, 브랜드(all, SK, 알뜰...등등)
    }
}
