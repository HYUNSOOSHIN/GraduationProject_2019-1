package com.example.graduationproject_2019_1.Activity;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.graduationproject_2019_1.Adapter.RecyclerAdapter;
import com.example.graduationproject_2019_1.Data.RecycleObject;
import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.Manager.AirGradeManager;
import com.example.graduationproject_2019_1.Manager.AirGradeWrapper;
import com.example.graduationproject_2019_1.Manager.AsyncManager;
import com.example.graduationproject_2019_1.Manager.CityLocationManager;
import com.example.graduationproject_2019_1.Manager.JSONManager;
import com.example.graduationproject_2019_1.Manager.URLParameterManager;
import com.example.graduationproject_2019_1.Manager.WeatherAsynTask;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private Boolean isMenuShow = false;
    private Boolean isExitFlag = false;
    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역

    //private static final String ALARM_PREF_NAME = "alarmPrefName";
    //private static final String ALARM_CITY_NAME = "alarmCityName";
    private static final String ALARM_CITY_NAME_STRING = "alarmCityNameString";

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


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
//    TextView main_pm10_name;
//    ImageView main_pm10_face;
//    TextView main_pm10_quality;
    TextView main_pm10_status;
    TextView main_pm10_value;
    TextView main_pm10_status2;
    TextView main_pm10_value2;

    // 초미세먼지
//    TextView main_pm25_name;
//    ImageView main_pm25_face;
//    TextView main_pm25_quality;
    TextView main_pm25_status;
    TextView main_pm25_value;

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

    TextView weather; // This usage is test for weather data.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //사이드바 init
        addSideView();  //사이드바 add

        Button nextView_btn = findViewById(R.id.nextView_btn);
        nextView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actionIntent = new Intent(MainActivity.this, ActionActivity.class);
                startActivity(actionIntent);
            }
        });
        ImageButton searchView_btn = findViewById(R.id.searchView_btn);
        searchView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        recyclerView = findViewById(R.id.main_recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<RecycleObject> foodInfoArrayList = new ArrayList<>();
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "마스크", "마스크는 K94를 권장합니다."));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "공기청정기", "외출 후 공기청정기를 가동해주세요."));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "창문", "절때 열지 마세요."));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "야외활동", "야외활동을 최대한 자제하며 외출 후 반드시 샤워를 하세요."));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(foodInfoArrayList);

        recyclerView.setAdapter(recyclerAdapter);

        findUIObjects();
        addBackgroundList();

        setData(getCityInfoString());

//        weather = (TextView) findViewById(R.id.weather);

        //new WeatherAsynTask(weather).execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0] hour"); // --> perfectly doing well
        new WeatherAsynTask().execute(); // --> perfectly doing well
    }


    public String getCityInfoString() {
        SharedPreferences prefs = getSharedPreferences(ALARM_CITY_NAME_STRING, MODE_PRIVATE);
        return prefs.getString(ALARM_CITY_NAME_STRING, "강남구");
    }


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
        setDetailData(parsedData);
//        setMoreDetailData(parsedData);
//        setBackgroundColor(AirGradeManager.getBackgroundColorIdWithGrade(wholeGrade, false));
//        setThickBackgroundColor(AirGradeManager.getBackgroundColorIdWithGrade(wholeGrade, true));
    }

    private void findUIObjects() {
        //findBackgrounds();
        //findThickBackgrounds();
        findTitles();
        findDetails();
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

//    private void setThickBackgroundColor(int colorId) {
//        currentState.setBackgroundColor(colorId);
//        advertisement.setBackgroundColor(colorId);
//        detail.setBackgroundColor(colorId);
//        main_refresh.setBackgroundColor(colorId);
//    }


//    private void findBackgrounds() {
//        main_whole_background = (LinearLayout) view.findViewById(R.id.main_whole_background);
//        main_title = (LinearLayout) view.findViewById(R.id.main_title);
//        main_detail = (LinearLayout) view.findViewById(R.id.main_detail);
//        main_advertisement = (LinearLayout) view.findViewById(R.id.main_advertisement);
//        main_more_detail = (LinearLayout) view.findViewById(R.id.main_more_detail_explain);
//    }

//    private void findThickBackgrounds() {
//        currentState = (TextView) view.findViewById(R.id.main_bar_currentState);
//        advertisement = (TextView) view.findViewById(R.id.main_bar_advertise);
//        detail = (TextView) view.findViewById(R.id.main_bar_detail);
//    }

    private void findTitles() {
        location = (TextView) findViewById(R.id.main_location);
        time = (TextView) findViewById(R.id.main_time);
        face = (ImageView) findViewById(R.id.main_face);
//        quality = (TextView) view.findViewById(R.id.main_air_quality);
//        qualityMessage = (TextView) view.findViewById(R.id.main_air_quality_message);
    }


    private void findDetails() {
        // 미세먼지
//        main_pm10_name = (TextView) findViewById(R.id.main_pm10_name);
//        main_pm10_face = (ImageView) findViewById(R.id.main_pm10_image);
//        main_pm10_quality = (TextView) findViewById(R.id.main_pm10_quality);
        main_pm10_status = (TextView) findViewById(R.id.main_pm10_status);
        main_pm10_value = (TextView) findViewById(R.id.main_pm10_value);
        main_pm10_status2 = (TextView) findViewById(R.id.main_pm10_status2);
        main_pm10_value2 = (TextView) findViewById(R.id.main_pm10_value2);

        // 초미세먼지
//        main_pm25_name = (TextView) findViewById(R.id.main_pm25_name);
//        main_pm25_face = (ImageView) findViewById(R.id.main_pm25_image);
//        main_pm25_quality = (TextView) findViewById(R.id.main_pm25_quality);
        main_pm25_status = (TextView) findViewById(R.id.main_pm25_status);
        main_pm25_value = (TextView) findViewById(R.id.main_pm25_value);
    }

//    private void findMoreDetails() {
//        main_detail_update_time = (TextView) view.findViewById(R.id.main_detail_update_time);
//        main_detail_pm10 = (TextView) view.findViewById(R.id.main_detail_pm10);
//        main_detail_pm25 = (TextView) view.findViewById(R.id.main_detail_pm25);
//
//        main_detail_o3 = (TextView) view.findViewById(R.id.main_detail_o3);
//        main_detail_no2 = (TextView) view.findViewById(R.id.main_detail_no2);
//        main_detail_co = (TextView) view.findViewById(R.id.main_detail_co);
//
//        main_detail_so2 = (TextView) view.findViewById(R.id.main_detail_so2);
//        // main_detail_pm10_measure = (TextView) view.findViewById(R.id.main_detail_pm10_measure);
//        // main_detail_pm25_measure = (TextView) view.findViewById(R.id.main_detail_pm25_measure);
//
//        main_detail_whole_value = (TextView) view.findViewById(R.id.main_detail_whole_value);
//        main_detail_whole_state = (TextView) view.findViewById(R.id.main_detail_whole_state);
//    }

//    private void findOthers() {
//        main_refresh = (Button) view.findViewById(R.id.main_refresh);
//        main_location_spinner = (Spinner) view.findViewById(R.id.main_location_spinner);
//        toolbar = (Toolbar) MainActivity.instance.findViewById(R.id.toolbar);
//    }

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

    private void setDetailData(Map<String, String> detailData) {
        // 미세먼지
        String pm10_detail = detailData.get("PM10");
        AirGradeWrapper pm10_wrapper = AirGradeManager.get("PM10", pm10_detail);
//        main_pm10_face.setBackgroundResource(pm10_wrapper.getFaceId());
//        main_pm10_quality.setText(pm10_wrapper.getQuality());
        main_pm10_status.setText(pm10_wrapper.getQuality());
        main_pm10_value.setText(pm10_detail + " ㎍/㎥");
        main_pm10_status2.setText(pm10_wrapper.getQuality());
        main_pm10_value2.setText(pm10_detail);

        // 초미세먼지
        String pm25_detail = detailData.get("PM25");
        AirGradeWrapper pm25_wrapper = AirGradeManager.get("PM25", pm25_detail);
//        main_pm25_face.setBackgroundResource(pm25_wrapper.getFaceId());
//        main_pm25_quality.setText(pm25_wrapper.getQuality());
        main_pm25_status.setText(pm25_wrapper.getQuality());
        main_pm25_value.setText(pm25_detail + " ㎍/㎥");

    }

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

    private void init() {

        findViewById(R.id.sidebar_btn).setOnClickListener(this);

        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);

    }

    //사이드바 추가
    private void addSideView() {

        SideBarView sidebar = new SideBarView(this);
        sideLayout.addView(sidebar);

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sidebar.setEventListener(new SideBarView.EventListener() {

            @Override
            public void btnCancel() {
                Log.e(TAG, "btnCancel");
                closeMenu();
            }

            @Override
            public void btnLevel1() {
                Log.e(TAG, "btnLevel1");

                closeMenu();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.sidebar_btn:

                showMenu();
                break;
        }
    }

    public void closeMenu() {

        isMenuShow = false;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_hidden);
        sideLayout.startAnimation(slide);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        }, 450);
    }

    public void showMenu() {

        isMenuShow = true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
        Log.e(TAG, "메뉴버튼 클릭");
    }

}


// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// This code isn't used in this MainActivity, Because it is now in Manager package.
/*
public class WeatherAsynTask extends AsyncTask<String, Void, String> {
    WeatherData[] W_Data = new WeatherData[25];
    RegionCode R_Code = new RegionCode();
    TextView textView;
    public WeatherAsynTask(TextView textView){
        this.textView = textView;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        String URL = params[0];
        String E1 = params[1];
        String result = "";
        try {
            Document document = Jsoup.connect(URL).get();
            Elements elements = document.select(E1);
            for(Element element : elements){
                result = result+element.text()+"\n";
            }
            System.out.println(result);
            Log.d("test1111", result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s){
    super.onPostExecute(s);
        textView.setText(s);
    }
}
*/
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1절대 지우지 마세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!