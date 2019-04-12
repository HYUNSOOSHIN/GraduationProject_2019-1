package com.example.graduationproject_2019_1.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    public String result_weather;
    public String GU;
    public String DONG;
    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(), 1000);
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

    // This is previlege request logic
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }
        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    public void GPS_function() {
        Geocoder mGeoCoder = new Geocoder(LoadingActivity.this);

        callPermission();
        // 권한 요청을 해야 함
        if (!isPermission) {
            callPermission();
            return;
        }
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
