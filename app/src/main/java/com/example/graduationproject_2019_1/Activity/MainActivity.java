package com.example.graduationproject_2019_1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.Manager.AirGradeManager;
import com.example.graduationproject_2019_1.Manager.AsyncManager;
import com.example.graduationproject_2019_1.Manager.CityLocationManager;
import com.example.graduationproject_2019_1.Manager.JSONManager;
import com.example.graduationproject_2019_1.Manager.URLParameterManager;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<LinearLayout> backgroundList = new ArrayList<>();

    LinearLayout main_whole_background;
    LinearLayout main_title;
    LinearLayout main_detail;
    LinearLayout main_advertisement;
    LinearLayout main_more_detail;

    // main_title
    Button main_refresh;
    TextView location;
    TextView time;
    ImageView face;
    TextView quality;
    TextView qualityMessage;

    // main_detail
    // 미세먼지
    TextView main_pm10_name;
    ImageView main_pm10_face;
    TextView main_pm10_quality;
    TextView main_pm10_detail;

    // 초미세먼지
    TextView main_pm25_name;
    ImageView main_pm25_face;
    TextView main_pm25_quality;
    TextView main_pm25_detail;

    // 오존
    TextView main_o3_name;
    ImageView main_o3_face;
    TextView main_o3_quality;
    TextView main_o3_detail;

    // 이산화질소
    TextView main_no2_name;
    ImageView main_no2_face;
    TextView main_no2_quality;
    TextView main_no2_detail;

    // 일산화탄소
    TextView main_co_name;
    ImageView main_co_face;
    TextView main_co_quality;
    TextView main_co_detail;

    // 아황산가스
    TextView main_so2_name;
    ImageView main_so2_face;
    TextView main_so2_quality;
    TextView main_so2_detail;

    // 세부 사항
    TextView main_detail_update_time;
    TextView main_detail_pm10;
    TextView main_detail_pm25;

    TextView main_detail_o3;
    TextView main_detail_no2;
    TextView main_detail_co;

    TextView main_detail_so2;
    // TextView main_detail_pm10_measure;
    // TextView main_detail_pm25_measure;

    TextView main_detail_whole_value;
    TextView main_detail_whole_state;

    // 텍스트뷰(가이드) 색 진하게 칠할 부분
    TextView currentState;
    TextView advertisement;
    TextView detail;

    /*
    msrdt 측정일시
    msrrgn_nm 권영멱
    msrste_nm 측정소명

    pm10 미세먼지
    pm25 초미세먼지
    o3   오존
    no2  이산화질소농도
    co   일산화탄소농도
    so2  아황산가스농도

    idex_nm  통합대기환경등급
    idex_mvl 통합대기환경지수
    arplt_main 지수결정물질
    */

    private int wholeGrade;
    private boolean isSpinnerClickeRId = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton nextView_btn = findViewById(R.id.nextView_btn);
        nextView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActionActivity.class);
                startActivity(intent);
            }
        });

        findUIObjects();
        addBackgroundList();

        setData("성북구");
    }
/*
    public String getCityInfoString() {
        SharedPreferences prefs = getSharedPreferences(ALARM_CITY_NAME_STRING, MODE_PRIVATE);
        return prefs.getString(ALARM_CITY_NAME_STNG, "강남구");
    }
*/


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setData(String gu) {
        Log.i("test", "setData");

        AsyncManager manager = AsyncManager.getInstance();
        String nm = CityLocationManager.getNMbyCityName(gu);
        String a = manager.make(Url.REAL_TIME_CITY_AIR, URLParameterManager.getRequestString(nm, gu));

        Map<String, String> parsedData = JSONManager.parse(a);

        // 통합대기환경등급을 비교해 background color change

        String titleQuality = parsedData.get("IDEX_MVL");
        int titleQualityInt = Integer.parseInt(titleQuality);
        wholeGrade = AirGradeManager.getGradeWithWholeValue(titleQualityInt);
        setTitleData(parsedData);
//        setDetailData(parsedData);
//        setMoreDetailData(parsedData);
//        setBackgroundColor(AirGradeManager.getBackgroundColorIdWithGrade(wholeGrade, false));
//        setThickBackgroundColor(AirGradeManager.getBackgroundColorIdWithGrade(wholeGrade, true));
    }

    //일단 임시로 테스트로 한개만 살림
    private void findUIObjects() {
        //findBackgrounds();
        //findThickBackgrounds();
        findTitles();
        //findDetails();
        //findMoreDetails();
        //findOthers();

        //setOnClickListeners();
    }

    private void addBackgroundList() {
        backgroundList.add(main_whole_background);
        backgroundList.add(main_title);
        backgroundList.add(main_detail);
        backgroundList.add(main_advertisement);
        backgroundList.add(main_more_detail);
    }

    private void setThickBackgroundColor(int colorId) {
        currentState.setBackgroundColor(colorId);
        advertisement.setBackgroundColor(colorId);
        detail.setBackgroundColor(colorId);
        main_refresh.setBackgroundColor(colorId);
    }

    /*
        private void findBackgrounds() {
            main_whole_background = (LinearLayout) view.findViewById(R.id.main_whole_background);
            main_title = (LinearLayout) view.findViewById(R.id.main_title);
            main_detail = (LinearLayout) view.findViewById(R.id.main_detail);
            main_advertisement = (LinearLayout) view.findViewById(R.id.main_advertisement);
            main_more_detail = (LinearLayout) view.findViewById(R.id.main_more_detail_explain);
        }

        private void findThickBackgrounds() {
            currentState = (TextView) view.findViewById(R.id.main_bar_currentState);
            advertisement = (TextView) view.findViewById(R.id.main_bar_advertise);
            detail = (TextView) view.findViewById(R.id.main_bar_detail);
        }
    */
    private void findTitles() {
        location = (TextView) findViewById(R.id.main_location);
        time = (TextView) findViewById(R.id.main_time);
        face = (ImageView) findViewById(R.id.main_face);
        //quality = (TextView) view.findViewById(R.id.main_air_quality);
        //qualityMessage = (TextView) view.findViewById(R.id.main_air_quality_message);
    }

    /*
        private void findDetails() {
            // 미세먼지
            main_pm10_name = (TextView) view.findViewById(R.id.main_pm10_name);
            main_pm10_face = (ImageView) view.findViewById(R.id.main_pm10_face);
            main_pm10_quality = (TextView) view.findViewById(R.id.main_pm10_quality);
            main_pm10_detail = (TextView) view.findViewById(R.id.main_pm10_detail);

            // 초미세먼지
            main_pm25_name = (TextView) view.findViewById(R.id.main_pm25_name);
            main_pm25_face = (ImageView) view.findViewById(R.id.main_pm25_face);
            main_pm25_quality = (TextView) view.findViewById(R.id.main_pm25_quality);
            main_pm25_detail = (TextView) view.findViewById(R.id.main_pm25_detail);

            // 오존
            main_o3_name = (TextView) view.findViewById(R.id.main_o3_name);
            main_o3_face = (ImageView) view.findViewById(R.id.main_o3_face);
            main_o3_quality = (TextView) view.findViewById(R.id.main_o3_quality);
            main_o3_detail = (TextView) view.findViewById(R.id.main_o3_detail);

            // 이산화질소
            main_no2_name = (TextView) view.findViewById(R.id.main_no2_name);
            main_no2_face = (ImageView) view.findViewById(R.id.main_no2_face);
            main_no2_quality = (TextView) view.findViewById(R.id.main_no2_quality);
            main_no2_detail = (TextView) view.findViewById(R.id.main_no2_detail);

            // 일산화탄소
            main_co_name = (TextView) view.findViewById(R.id.main_co_name);
            main_co_face = (ImageView) view.findViewById(R.id.main_co_face);
            main_co_quality = (TextView) view.findViewById(R.id.main_co_quality);
            main_co_detail = (TextView) view.findViewById(R.id.main_co_detail);

            // 아황산가스
            main_so2_name = (TextView) view.findViewById(R.id.main_so2_name);
            main_so2_face = (ImageView) view.findViewById(R.id.main_so2_face);
            main_so2_quality = (TextView) view.findViewById(R.id.main_so2_quality);
            main_so2_detail = (TextView) view.findViewById(R.id.main_so2_detail);
        }

        private void findMoreDetails() {
            main_detail_update_time = (TextView) view.findViewById(R.id.main_detail_update_time);
            main_detail_pm10 = (TextView) view.findViewById(R.id.main_detail_pm10);
            main_detail_pm25 = (TextView) view.findViewById(R.id.main_detail_pm25);

            main_detail_o3 = (TextView) view.findViewById(R.id.main_detail_o3);
            main_detail_no2 = (TextView) view.findViewById(R.id.main_detail_no2);
            main_detail_co = (TextView) view.findViewById(R.id.main_detail_co);

            main_detail_so2 = (TextView) view.findViewById(R.id.main_detail_so2);
            // main_detail_pm10_measure = (TextView) view.findViewById(R.id.main_detail_pm10_measure);
            // main_detail_pm25_measure = (TextView) view.findViewById(R.id.main_detail_pm25_measure);

            main_detail_whole_value = (TextView) view.findViewById(R.id.main_detail_whole_value);
            main_detail_whole_state = (TextView) view.findViewById(R.id.main_detail_whole_state);
        }

        private void findOthers() {
            main_refresh = (Button) view.findViewById(R.id.main_refresh);
            main_location_spinner = (Spinner) view.findViewById(R.id.main_location_spinner);
            toolbar = (Toolbar) MainActivity.instance.findViewById(R.id.toolbar);
        }
    */
    boolean isPassed = false;


    private StringBuilder makeMeasureTimeText(Map<String, String> titleData) {
        String strDate = titleData.get("MSRDT");
        String year = strDate.substring(0, 4);
        String month = strDate.substring(4, 6);
        String day = strDate.substring(6, 8);
        String hour = strDate.substring(8, 10);
        String minute = strDate.substring(10, 12);

        StringBuilder date = new StringBuilder();
        date.append(year)
                .append("-")
                .append(month)
                .append("-")
                .append(day)
                .append(" ")
                .append(hour)
                .append(":")
                .append(minute);

        return date;
    }

    // 얼굴, 나쁨, 공기가탁하네요
    private void setTitleData(Map<String, String> titleData) {
        String date = makeMeasureTimeText(titleData).toString();
        String _qualityMessage = AirGradeManager.getGradeMessageWithGrade(wholeGrade);
        String shortMessage = AirGradeManager.getGradeShortMessageWithGrade(wholeGrade);
        int faceId = AirGradeManager.getGradeImageIdWithGrade(wholeGrade);

        location.setText(titleData.get("MSRRGN_NM") + " " + titleData.get("MSRSTE_NM"));
        time.setText("측정일시 : " + date);
        face.setImageResource(faceId);
        //quality.setText(shortMessage);
        //qualityMessage.setText(_qualityMessage);

        // titleData.get("MSRDT")

        Log.d("test1234", titleData.get("MSRRGN_NM"));
        Log.d("test1235", titleData.get("MSRSTE_NM"));
    }

//    private void setDetailData(Map<String, String> detailData) {
//        // 미세먼지
//        String pm10_detail = detailData.get("PM10");
//        AirGradeWrapper pm10_wrapper = AirGradeManager.get("PM10", pm10_detail);
//        main_pm10_face.setBackgroundResource(pm10_wrapper.getFaceId());
//        main_pm10_quality.setText(pm10_wrapper.getQuality());
//        main_pm10_detail.setText(pm10_detail + " ㎍/㎥");
//
//        // 초미세먼지
//        String pm25_detail = detailData.get("PM25");
//        AirGradeWrapper pm25_wrapper = AirGradeManager.get("PM25", pm25_detail);
//        main_pm25_face.setBackgroundResource(pm25_wrapper.getFaceId());
//        main_pm25_quality.setText(pm25_wrapper.getQuality());
//        main_pm25_detail.setText(pm25_detail + " ㎍/㎥");
//
//        // 오존
//        String o3_detail = detailData.get("O3");
//        AirGradeWrapper o3_wrapper = AirGradeManager.get("O3", o3_detail);
//        main_o3_face.setBackgroundResource(o3_wrapper.getFaceId());
//        main_o3_quality.setText(o3_wrapper.getQuality());
//        main_o3_detail.setText(o3_detail + " ppm");
//
//        // 이산화질소
//        String no2_detail = detailData.get("NO2");
//        AirGradeWrapper no2_wrapper = AirGradeManager.get("NO2", no2_detail);
//        main_no2_face.setBackgroundResource(no2_wrapper.getFaceId());
//        main_no2_quality.setText(no2_wrapper.getQuality());
//        main_no2_detail.setText(no2_detail + " ppm");
//
//        // 일산화탄소
//        String co_detail = detailData.get("CO");
//        AirGradeWrapper co_wrapper = AirGradeManager.get("CO", co_detail);
//        main_co_face.setBackgroundResource(co_wrapper.getFaceId());
//        main_co_quality.setText(co_wrapper.getQuality());
//        main_co_detail.setText(co_detail + " ppm");
//
//        // 아황산가스
//        String so2_detail = detailData.get("SO2");
//        AirGradeWrapper so2_wrapper = AirGradeManager.get("SO2", so2_detail);
//        main_so2_face.setBackgroundResource(so2_wrapper.getFaceId());
//        main_so2_quality.setText(so2_wrapper.getQuality());
//        main_so2_detail.setText(so2_detail + " ppm");
//    }
//
//    private void setMoreDetailData(Map<String, String> detailData) {
//        String date = makeMeasureTimeText(detailData).toString();
//        String measurePlace = detailData.get("MSRSTE_NM");
//        String wholeValue = detailData.get("IDEX_MVL");
//        String wholeState = detailData.get("IDEX_NM");
//
//        main_detail_update_time.setText(date);
//
//        main_detail_pm10.setText(measurePlace);
//        main_detail_pm25.setText(measurePlace);
//        main_detail_o3.setText(measurePlace);
//        main_detail_no2.setText(measurePlace);
//        main_detail_co.setText(measurePlace);
//        main_detail_so2.setText(measurePlace);
//
//        // main_detail_pm10_measure.setText("");
//        // main_detail_pm25_measure.setText("");
//
//        main_detail_whole_value.setText(wholeValue + " unit");
//        main_detail_whole_state.setText(wholeState);
//    }

}
