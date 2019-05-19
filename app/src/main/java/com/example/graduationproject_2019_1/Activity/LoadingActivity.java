package com.example.graduationproject_2019_1.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.GpsInfo;
import com.example.graduationproject_2019_1.R;

import java.io.IOException;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public double latitude;
    public double longitude;
    public String string_location;
    public String[] array_location;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private boolean isPermission = false;

    public String GU;
    public String DONG;
    public GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        if (!checkLocationPermissions()) {
            Log.i("test","In " );
            requestLocationPermissions(PERMISSIONS_ACCESS_FINE_LOCATION);
        } else {
            Log.i("test","else " );
            Handler handler = new Handler();
            handler.postDelayed(new splashHandler(), 1000);
        }
    }

    class splashHandler implements Runnable{
        @Override
        public void run() {
            GPS_function();
            sharedPreferences = getSharedPreferences("hyunsoo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("gu", GU);
            editor.putString("dong", DONG);
            editor.commit();

            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Handler handler = new Handler();
                    handler.postDelayed(new splashHandler(), 1000);
                } else {
//                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    // 권한 요청
    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        switch (requestCode) {
            case PERMISSIONS_ACCESS_FINE_LOCATION:
                ActivityCompat.requestPermissions(
                        LoadingActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                        requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
                );
        }
    }

    public void GPS_function() {
        Geocoder mGeoCoder = new Geocoder(LoadingActivity.this);

        gps = new GpsInfo(LoadingActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            try {
                List<Address> mResultList = mGeoCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                Log.d("TAG", "onComplete: " + mResultList.get(0).getAddressLine(0));
                string_location = mResultList.get(0).getAddressLine(0);
                array_location = string_location.split(" ");
                GU = array_location[2];
                DONG = array_location[3];

                // array_location[2]: 구, array_location[3]: 동 만 살리면 된다.
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG", "onComplete: 주소변환 실패");
            }
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
    }
}
