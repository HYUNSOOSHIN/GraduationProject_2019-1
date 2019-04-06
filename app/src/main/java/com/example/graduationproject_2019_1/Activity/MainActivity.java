package com.example.graduationproject_2019_1.Activity;

import android.content.SharedPreferences;
import android.content.Intent;
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

import com.example.graduationproject_2019_1.Adapter.ActionInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Adapter.WeatherInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Data.ActionRecycleObject;
import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.Data.WeatherRecycleObject;
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

    TextView weather; // This usage is test for weather data.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //사이드바 init
        addSideView();  //사이드바 add
        findUIObjects(); //뷰 Id 매칭
        setData(getCityInfoString()); // 데이터 매핑
        action_list(); //건강관리 및 행동요령 리스트 출력
        weather_list(0); //주간날씨정보 리스트 출력


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

//        weather = (TextView) findViewById(R.id.weather);
//        new WeatherAsynTask(weather).execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0] hour"); // --> perfectly doing well
        new WeatherAsynTask().execute(); // --> perfectly doing well
    }

    //텍스트뷰 월/화/수 리스너
    public void whatday(View view){

        switch (view.getId()){
            case R.id.day1:
                day1.setTextColor(Color.parseColor("#3a3a3a"));
                day2.setTextColor(Color.parseColor("#818181"));
                day3.setTextColor(Color.parseColor("#818181"));
                weather_list(0); //오늘 조건으로 리스트 출력
                Log.i("test","오늘");
                break;
            case R.id.day2:
                day1.setTextColor(Color.parseColor("#818181"));
                day2.setTextColor(Color.parseColor("#3a3a3a"));
                day3.setTextColor(Color.parseColor("#818181"));
//                weather_list(1);
                Log.i("test","내일");
                break;
            case R.id.day3:
                day1.setTextColor(Color.parseColor("#818181"));
                day2.setTextColor(Color.parseColor("#818181"));
                day3.setTextColor(Color.parseColor("#3a3a3a"));
//                weather_list(2);
                Log.i("test","모레");
                break;
            default:
                return;
        }
    }


    public String getCityInfoString() {
        SharedPreferences prefs = getSharedPreferences(ALARM_CITY_NAME_STRING, MODE_PRIVATE);
        return prefs.getString(ALARM_CITY_NAME_STRING, "강남구");
    }


    private void setData(String gu) {
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

//        location.setText(titleData.get("MSRRGN_NM") + " " + titleData.get("MSRSTE_NM"));
        location.setText(titleData.get("MSRSTE_NM"));
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
    private void weather_list(int condition){
        recyclerView2 = findViewById(R.id.main_recycleView2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);

        ArrayList<WeatherRecycleObject> weatherInfoArrayList = new ArrayList<>();
        if(condition==0){ //오늘
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
            weatherInfoArrayList.add(new WeatherRecycleObject("시간", R.drawable.tmp, "미세", R.drawable.tmp, "온도", R.drawable.tmp, "강수"));
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