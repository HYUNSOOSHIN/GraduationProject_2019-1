package com.example.graduationproject_2019_1.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.graduationproject_2019_1.Adapter.ActionInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Adapter.WeatherInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Data.GpsInfo;
import com.example.graduationproject_2019_1.Data.ActionRecycleObject;
import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.Data.WeatherData;
import com.example.graduationproject_2019_1.Data.WeatherRecycleObject;
import com.example.graduationproject_2019_1.Manager.AirGradeManager;
import com.example.graduationproject_2019_1.Manager.AirGradeWrapper;
import com.example.graduationproject_2019_1.Manager.AsyncManager;
import com.example.graduationproject_2019_1.Manager.CityLocationManager;
import com.example.graduationproject_2019_1.Manager.JSONManager;
import com.example.graduationproject_2019_1.Manager.URLParameterManager;
import com.example.graduationproject_2019_1.Manager.WeatherAsynTask;
import com.example.graduationproject_2019_1.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    // main_title
    TextView location;
    TextView time;
    ImageView face;

    // main_detail
    // 미세먼지
    TextView main_pm10_status;
    TextView main_pm10_value;
    TextView main_pm10_status2;
    TextView main_pm10_value2;

    // 초미세먼지
    TextView main_pm25_status;
    TextView main_pm25_value;

    //잡
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;
    TextView today;
    TextView day1;
    TextView day2;
    TextView day3;

    /*
    msrdt 측정일시
    msrrgn_nm 권영멱
    msrste_nm 측정소명

    pm10 미세먼지
    pm25 초미세먼지

    idex_nm  통합대기환경등급
    idex_mvl 통합대기환경지수
    arplt_main 지수결정물질
    */

    private int wholeGrade;
    private boolean isSpinnerClickeRId = false;

    private TextView txtLat;
    private TextView txtLon;
    private TextView txtLocation;
    private TextView txtWeather;

    private TextView main_weather;
    private TextView main_temp;
    private TextView main_rain;
    private TextView main_reh;

    public double latitude ;
    public double longitude;
    public String string_location;
    public String[] array_location;
    public String[] result_weather;
    public ArrayList<WeatherData> W_D;
    public int first_day;
    public int second_day;
    public int third_day;
    public String GU;
    public String DONG;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;
    public Intent intent_location;
    public String get_location_gu_intent = null;
    public String get_location_dong_intent = null;
    public boolean send_or_not = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent_location = new Intent(this.getIntent());
        first_day = 1; second_day = 1; third_day = 1;

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


        main_weather = (TextView) findViewById(R.id.main_weather);
        main_temp = (TextView) findViewById(R.id.main_temp);
        main_rain = (TextView) findViewById(R.id.main_rain);
        main_reh = (TextView) findViewById(R.id.main_reh);
        get_location_gu_intent = intent_location.getStringExtra("searching_location_gu");
        get_location_dong_intent = intent_location.getStringExtra("searching_location_dong");
        send_or_not = intent_location.getBooleanExtra("send_or_not", false);
        if(send_or_not == false){
            GPS_function();
        }
        else{
            GU = get_location_gu_intent;
            DONG = get_location_dong_intent;
        }

        try {
            result_weather = (String[]) new WeatherAsynTask(GU, DONG).execute().get(); // --> perfectly doing well
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        txtWeather = (TextView) findViewById(R.id.tv_weather);
        //W_D = new ArrayList<>(); // 날씨정보 3일치 매핑해야 됨

        int cnt = 0;
        String print = "";
        for(int i=1; i<result_weather.length; i++){
            print += result_weather[i] + " ";
            if((i % 6) == 0){
                print += "\n";
            }
        }
/*
        txtWeather.setText(result_weather[0] + "\n" +
                result_weather[1] + " "  + result_weather[2] + " "  + result_weather[3] + " "  + result_weather[4] + " "  + result_weather[5] + " "  + result_weather[6] + "\n" +
                result_weather[7] + " "  + result_weather[8] + " " + result_weather[9] + " " + result_weather[10] + " " + result_weather[11] + " " + result_weather[12] + "\n" +
                result_weather[13] + " " + result_weather[14] + " " + result_weather[15] + " " + result_weather[16] + " " + result_weather[17] + " " + result_weather[18] + "\n"
        );
*/
        txtWeather.setText(result_weather[0] + "\n" + print);
        //setData(getCityInfoString());
        main_weather.setText(result_weather[4]); // 날씨 맑음?
        main_temp.setText(result_weather[3]); // 온도
        main_rain.setText(result_weather[5]); // 강수확률
        main_reh.setText(result_weather[6]); // 풍속


        for(int i=1; i<result_weather.length-5; i+=6){
            if(result_weather[i+1].equals("1")){
                second_day = i;
                break;
            }
        }
        for(int i=second_day; i<result_weather.length-5; i+=6){
            if(result_weather[i+1].equals("2")){
                third_day = i;
                break;
            }
        }

        weather_list(result_weather, 0);
        init(); //사이드바 init
        addSideView();  //사이드바 add
        findUIObjects(); //뷰 Id 매칭
        //setData(getCityInfoString()); // 데이터 매핑
        action_list(); //건강관리 및 행동요령 리스트 출력

        setData(GU); // 구 넣어줌

    }


    //텍스트뷰 월/화/수 리스너
    public void whatday(View view){

        switch (view.getId()){
            case R.id.day1:
                day1.setTextColor(Color.parseColor("#3a3a3a"));
                day2.setTextColor(Color.parseColor("#818181"));
                day3.setTextColor(Color.parseColor("#818181"));
                weather_list(result_weather, 0); //오늘 조건으로 리스트 출력
                Log.i("test","오늘");
                break;
            case R.id.day2:
                day1.setTextColor(Color.parseColor("#818181"));
                day2.setTextColor(Color.parseColor("#3a3a3a"));
                day3.setTextColor(Color.parseColor("#818181"));
                weather_list(result_weather, 1);
                Log.i("test","내일");
                break;
            case R.id.day3:
                day1.setTextColor(Color.parseColor("#818181"));
                day2.setTextColor(Color.parseColor("#818181"));
                day3.setTextColor(Color.parseColor("#3a3a3a"));
                weather_list(result_weather, 2);
                Log.i("test","모레");
                break;
            default:
                return;
        }
    }



    

    private void setData(String gu) {
        //Log.i("test", "setData");
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
        setTextColor(AirGradeManager.getTextColorIdWithGrade(wholeGrade, false));
    }

    private void setTitleData(Map<String, String> titleData) {
        String date = makeMeasureTimeText(titleData).toString();
        String _qualityMessage = AirGradeManager.getGradeMessageWithGrade(wholeGrade);
        String shortMessage = AirGradeManager.getGradeShortMessageWithGrade(wholeGrade);
        int faceId = AirGradeManager.getGradeImageIdWithGrade(wholeGrade);

        location.setText(titleData.get("MSRRGN_NM") + " " + titleData.get("MSRSTE_NM"));
        //location.setText(titleData.get("MSRSTE_NM"));
        time.setText(date);
        today.setText(date);

//        face.setImageResource(faceId);
    }

    private void setDetailData(Map<String, String> detailData) {
        // 미세먼지
        String pm10_detail = detailData.get("PM10");
        AirGradeWrapper pm10_wrapper = AirGradeManager.get("PM10", pm10_detail);
        main_pm10_status.setText(pm10_wrapper.getQuality());
        main_pm10_value.setText(pm10_detail + " ㎍/㎥");
        main_pm10_status2.setText(pm10_wrapper.getQuality());
        main_pm10_value2.setText(pm10_detail);

        // 초미세먼지
        String pm25_detail = detailData.get("PM25");
        AirGradeWrapper pm25_wrapper = AirGradeManager.get("PM25", pm25_detail);
        main_pm25_status.setText(pm25_wrapper.getQuality());
        main_pm25_value.setText(pm25_detail + " ㎍/㎥");
    }

    // 텍스트 색 변경
    private void setTextColor(int colorId) {
        main_pm10_status.setTextColor(colorId);
        main_pm25_status.setTextColor(colorId);
        main_pm10_value.setTextColor(colorId);
        main_pm25_value.setTextColor(colorId);
        main_pm10_status2.setTextColor(colorId);
    }

    private void findUIObjects() {
        findTitles();
        findDetails();
        findOthers();
    }


    private void findTitles() {
        location = (TextView) findViewById(R.id.main_location);
        time = (TextView) findViewById(R.id.main_time);
        face = (ImageView) findViewById(R.id.main_face);
    }

    private void findDetails() {

        today = findViewById(R.id.today);
        // 미세먼지
        main_pm10_status = (TextView) findViewById(R.id.main_pm10_status);
        main_pm10_value = (TextView) findViewById(R.id.main_pm10_value);
        main_pm10_status2 = (TextView) findViewById(R.id.main_pm10_status2);
        main_pm10_value2 = (TextView) findViewById(R.id.main_pm10_value2);

        // 초미세먼지
        main_pm25_status = (TextView) findViewById(R.id.main_pm25_status);
        main_pm25_value = (TextView) findViewById(R.id.main_pm25_value);
    }

    private void findOthers(){
        day1=findViewById(R.id.day1);
        day2=findViewById(R.id.day2);
        day3=findViewById(R.id.day3);
    }

    //건강관리 및 행동요령 리스트
    private void action_list(){
        recyclerView = findViewById(R.id.main_recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<ActionRecycleObject> adtionInfoArrayList = new ArrayList<>();
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.tmp, "마스크", "마스크는 K94를 권장합니다."));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.tmp, "공기청정기", "외출 후 공기청정기를 가동해주세요."));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.tmp, "창문", "절때 열지 마세요."));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.tmp, "야외활동", "야외활동을 최대한 자제하며 외출 후 반드시 샤워를 하세요."));

        ActionInfoRecyclerAdapter actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(adtionInfoArrayList);

        recyclerView.setAdapter(actionInfoRecyclerAdapter);
    }

    //주간 날씨 정보
    private void weather_list(String[] result_weather, int day){
        recyclerView2 = findViewById(R.id.main_recycleView2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);

        ArrayList<WeatherRecycleObject> weatherInfoArrayList = new ArrayList<>();

        // i = 1 + 6 = 7부터 시작, 만약 day가 0에서 처음으로 1로 바뀌는, 1이 처음으로 2로 바뀌는 시점에 따라 falg 조절
        // i = result_weather.length - 6에서 끝남
        /*
        1 2 3 4 5 6
        7 8 9 10 11 12
        13 14 15 16 17 18
        19 20 21 22 23 24
        25 26 27 28 29 30
        31 32 33 34 35 36
        37 38 39 40 41 42
        43 44 45 46 47 48

        49
         */
        boolean flag = false;
        int index = 0;

        if(day == 0){
            for(int i=1; i<second_day; i+=6){
                weatherInfoArrayList.add(new WeatherRecycleObject(result_weather[i], R.drawable.tmp, result_weather[i+1], R.drawable.tmp,
                        result_weather[i+2], R.drawable.tmp, result_weather[i+3], R.drawable.tmp, result_weather[i+4], R.drawable.tmp, result_weather[i+5]));
            }
        }
        else if(day == 1){
            for(int i=second_day; i<third_day; i+=6){
                weatherInfoArrayList.add(new WeatherRecycleObject(result_weather[i], R.drawable.tmp, result_weather[i+1], R.drawable.tmp,
                        result_weather[i+2], R.drawable.tmp, result_weather[i+3], R.drawable.tmp, result_weather[i+4], R.drawable.tmp, result_weather[i+5]));
            }
        }
        else if(day == 2){
            for(int i=third_day; i<result_weather.length-5; i+=6){
                weatherInfoArrayList.add(new WeatherRecycleObject(result_weather[i], R.drawable.tmp, result_weather[i+1], R.drawable.tmp,
                        result_weather[i+2], R.drawable.tmp, result_weather[i+3], R.drawable.tmp, result_weather[i+4], R.drawable.tmp, result_weather[i+5]));
            }
        }



        WeatherInfoRecyclerAdapter weatherInfoRecyclerAdapter = new WeatherInfoRecyclerAdapter(weatherInfoArrayList);

        recyclerView2.setAdapter(weatherInfoRecyclerAdapter);
    }

    //측정시간
    private StringBuilder makeMeasureTimeText(Map<String, String> titleData) {
        String strDate = titleData.get("MSRDT");
        String year = strDate.substring(0, 4);
        String month = strDate.substring(4, 6);
        String day = strDate.substring(6, 8);
        String hour = strDate.substring(8, 10);
        String minute = strDate.substring(10, 12);

        StringBuilder date = new StringBuilder();
        date.append(year)
                .append(".")
                .append(month)
                .append(".")
                .append(day)
                .append(" ")
                .append(hour)
                .append(":")
                .append(minute);
        return date;
    }


    //SideBar - start
    private void init() {
        findViewById(R.id.sidebar_btn).setOnClickListener(this);
        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);
    }

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
                closeMenu();
            }
            @Override
            public void btnLevel1() {
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
    }
    //SideBar - end


    // This is previlege request logic
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

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
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }


	public void GPS_function(){

        txtLat = (TextView) findViewById(R.id.tv_latitude);
        txtLon = (TextView) findViewById(R.id.tv_logitude);
        txtLocation = (TextView) findViewById(R.id.tv_location);
        Geocoder mGeoCoder = new Geocoder(MainActivity.this);

        callPermission();
        // 권한 요청을 해야 함
        if (!isPermission) {
            callPermission();
            return;
        }
        gps = new GpsInfo(MainActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            txtLat.setText(String.valueOf(latitude));
            txtLon.setText(String.valueOf(longitude));

            try{
                List<Address>  mResultList = mGeoCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                Log.d("TAG", "onComplete: " + mResultList.get(0).getAddressLine(0));
                string_location = mResultList.get(0).getAddressLine(0);
                txtLocation.setText(string_location);
                array_location = string_location.split(" ");
                GU = array_location[2];
                DONG = array_location[3];

                // array_location[2]: 구, array_location[3]: 동 만 살리면 된다.
            }catch (IOException e){
                e.printStackTrace();
                Log.d("TAG", "onComplete: 주소변환 실패");
            }

            // This is for test, you can delete it.
            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
    }

}

    
