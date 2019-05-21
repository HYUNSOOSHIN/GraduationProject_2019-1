package com.example.graduationproject_2019_1.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;

    //sideView 관련
    Boolean isMenuShow = false;
    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역

    // main_title
    TextView location;
    TextView time;
    ImageView face;

    // main_detail

    //통합대기지수

    TextView total_value;

    // 미세먼지
    TextView main_pm10_status;
    TextView main_pm10_value;
    TextView main_pm10_status2;
    TextView main_pm10_value2;
    String pm10_detail;

    // 초미세먼지
    TextView main_pm25_status;
    TextView main_pm25_value;
    String pm25_detail;

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
    //어제 오늘 내일 날씨데이터
    JSONArray DAY1;
    JSONArray DAY2;
    JSONArray DAY3;

    //미세먼지 등급
    private int wholeGrade;

    //마크
    private RelativeLayout mark;
    private ImageView mark_img;
    private TextView mark_text;

    //오늘날씨 텍스트뷰
    private TextView main_weather;
    private TextView main_temp;
    private TextView main_rain;
    private TextView main_reh;

    //날씨정보, 구이름, 동이름
    public String result_weather;
    public String GU;
    public String DONG;

    // GPSTracker class
    public Intent intent_location;
    public String get_location_gu_intent = null;
    public String get_location_dong_intent = null;
    public boolean send_or_not = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        intent_location = new Intent(this.getIntent());
        get_location_gu_intent = intent_location.getStringExtra("searching_location_gu");
        get_location_dong_intent = intent_location.getStringExtra("searching_location_dong");
        send_or_not = intent_location.getBooleanExtra("send_or_not", false);
        if (send_or_not == true) { //검색으로 정보 받아오는 경우
            GU = get_location_gu_intent;
            DONG = get_location_dong_intent;
        } else if(send_or_not == false) { //현재위치 기반으로 받아오는 경우
            sharedPreferences = getSharedPreferences("hyunsoo", MODE_PRIVATE);;
            GU = sharedPreferences.getString("gu","영등포구");
            DONG = sharedPreferences.getString("dong","당산1동");
        }

        try {
            result_weather = (String) new WeatherAsynTask(GU, DONG).execute().get();
            JSONArray jsonArray = new JSONArray(result_weather);
            DAY1 = new JSONArray();
            DAY2 = new JSONArray();
            DAY3 = new JSONArray();

            int count = jsonArray.getInt(0);

            for (int i = 1; i <= count; i++) {
                if (jsonArray.getJSONObject(i).getString("day").equals("0")) { //오늘
                    DAY1.put(jsonArray.getJSONObject(i));
                } else if (jsonArray.getJSONObject(i).getString("day").equals("1")) { //내일
                    if (DAY1.length() < 8) { //오늘 데이터가 8보다 작으면
                        DAY1.put(jsonArray.getJSONObject(i));
                    }
                    DAY2.put(jsonArray.getJSONObject(i));
                } else { //모레
                    DAY3.put(jsonArray.getJSONObject(i));
                }
            }

//            Log.i("test", "day1" + DAY1.toString());
//            Log.i("test", "day2" + DAY2.toString());
//            Log.i("test", "day3" + DAY3.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        init(); //사이드바 init
        addSideView();  //사이드바 add
        findUIObjects(); //뷰 Id 매칭
        setWeatherText(); //오늘 날씨 데이터
        weather_list(0); //주간 날씨 리스트 출력
        action_list(); //건강관리 및 행동요령 리스트 출력
        setData(GU); // 미세먼지 위치정보 구 이름 입력 ex) 성북구


    }


    //텍스트뷰 월/화/수 리스너
    public void whatday(View view) {

        switch (view.getId()) {
            case R.id.day1:
                day1.setTextColor(Color.parseColor("#3a3a3a"));
                day2.setTextColor(Color.parseColor("#818181"));
                day2.setBackgroundColor(Color.parseColor("#fafafa"));
                day3.setTextColor(Color.parseColor("#818181"));
                day3.setBackgroundColor(Color.parseColor("#fafafa"));
                weather_list(0); //오늘 조건으로 리스트 출력
//                Log.i("test", "오늘");
                break;
            case R.id.day2:
                day1.setTextColor(Color.parseColor("#818181"));
                day1.setBackgroundColor(Color.parseColor("#fafafa"));
                day2.setTextColor(Color.parseColor("#3a3a3a"));
                day3.setTextColor(Color.parseColor("#818181"));
                day3.setBackgroundColor(Color.parseColor("#fafafa"));
                weather_list(1);
//                Log.i("test", "내일");
                break;
            case R.id.day3:
                day1.setTextColor(Color.parseColor("#818181"));
                day1.setBackgroundColor(Color.parseColor("#fafafa"));
                day2.setTextColor(Color.parseColor("#818181"));
                day2.setBackgroundColor(Color.parseColor("#fafafa"));
                day3.setTextColor(Color.parseColor("#3a3a3a"));
                weather_list(2);
//                Log.i("test", "모레");
                break;
            default:
                return;
        }
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

        // 통합대기지수 값 텍스트 설정
        total_value.setText(AirGradeManager.getGradetextWithWholeValue(wholeGrade));
        total_value.setTextColor(AirGradeManager.getTextColorIdWithGrade(wholeGrade));

        setTitleData(parsedData);
        setDetailData(parsedData);
        setTextColor();
    }

    private void setTitleData(Map<String, String> titleData) {
        String date = makeMeasureTimeText(titleData).toString();
        int faceId = AirGradeManager.getGradeImageIdWithGrade(wholeGrade);

        location.setText("서울시 " + GU + " " + DONG);
        time.setText(date);
        today.setText(date.substring(0, date.lastIndexOf(" ")));
        face.setImageResource(faceId);
    }

    private void setDetailData(Map<String, String> detailData) {

        pm10_detail = detailData.get("PM10");
        AirGradeWrapper pm10_wrapper = AirGradeManager.get("PM10", pm10_detail);
        pm25_detail = detailData.get("PM25");
        AirGradeWrapper pm25_wrapper = AirGradeManager.get("PM25", pm25_detail);

        if(detailData.get("IDEX_NM").equals("점검중")){
            main_pm10_status.setText(pm10_wrapper.getQuality());
            main_pm10_value.setText("점검중");
            main_pm10_status2.setText(pm10_wrapper.getQuality());

            main_pm25_status.setText(pm25_wrapper.getQuality());
            main_pm25_value.setText("점검중");

            mark_img.setImageResource(AirGradeManager.getMarkImage(Integer.parseInt(pm10_detail)));

            total_value.setText("점검중");
        } else {
            // 미세먼지
            main_pm10_status.setText(pm10_wrapper.getQuality());
            main_pm10_value.setText(pm10_detail + " ㎍/㎥");
            main_pm10_status2.setText(pm10_wrapper.getQuality());
            mark.setPadding(markLocation(Integer.parseInt((pm10_detail))),0,0,0);
            mark_img.setImageResource(AirGradeManager.getMarkImage(Integer.parseInt(pm10_detail)));
            mark_text.setText(pm10_detail);

            // 초미세먼지
            main_pm25_status.setText(pm25_wrapper.getQuality());
            main_pm25_value.setText(pm25_detail + " ㎍/㎥");
        }

       // 기상 정보를 받아오지 못했을 경우를 대비하는 용도
        sharedPreferences = getSharedPreferences("hyunsoo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PM10", pm10_detail);
        editor.putString("PM25", pm25_detail);
    }

    private void setWeatherText() {
        try {
            main_weather.setText(DAY1.getJSONObject(0).getString("wfKor")); // 날씨 ex)맑음
            main_temp.setText(DAY1.getJSONObject(0).getString("temp").substring(0,DAY1.getJSONObject(0).getString("temp").indexOf(".")) + "°C"); // 온도
            main_rain.setText(DAY1.getJSONObject(0).getString("pop") + "%"); // 강수확률
            main_reh.setText(DAY1.getJSONObject(0).getString("reh") + "%"); // 습도

            // 기상 정보를 받아오지 못했을 경우를 대비하는 용도
            sharedPreferences = getSharedPreferences("hyunsoo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("wfKor", DAY1.getJSONObject(0).getString("wfKor"));
            editor.putString("temp", DAY1.getJSONObject(0).getString("temp").substring(0,DAY1.getJSONObject(0).getString("temp").indexOf(".")) + "°C");
            editor.putString("pop", DAY1.getJSONObject(0).getString("pop") + "%");
            editor.putString("reh", DAY1.getJSONObject(0).getString("reh") + "%");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 텍스트 색 변경
    private void setTextColor() {
        main_pm10_status.setTextColor(AirGradeManager.getPM10textColor(pm10_detail));
        main_pm25_status.setTextColor(AirGradeManager.getPM25textColor(pm25_detail));
        main_pm10_value.setTextColor(AirGradeManager.getPM10textColor(pm10_detail));
        main_pm25_value.setTextColor(AirGradeManager.getPM25textColor(pm25_detail));
        main_pm10_status2.setTextColor(AirGradeManager.getPM10textColor(pm10_detail));
    }

    private void findUIObjects() {
        findTitles();
        findDetails();
        findOthers();
        findTodayWeather();
    }


    private void findTitles() {
        location = (TextView) findViewById(R.id.main_location);
        time = (TextView) findViewById(R.id.main_time);
        face = (ImageView) findViewById(R.id.main_face);
    }

    private void findDetails() {

        today = findViewById(R.id.today);

        total_value = findViewById(R.id.total_value);

        // 미세먼지
        main_pm10_status = (TextView) findViewById(R.id.main_pm10_status);
        main_pm10_value = (TextView) findViewById(R.id.main_pm10_value);
        main_pm10_status2 = (TextView) findViewById(R.id.main_pm10_status2);

        // 초미세먼지
        main_pm25_status = (TextView) findViewById(R.id.main_pm25_status);
        main_pm25_value = (TextView) findViewById(R.id.main_pm25_value);
    }

    private void findOthers() {
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        mark = findViewById(R.id.mark);
        mark_img = findViewById(R.id.mark_img);
        mark_text = findViewById(R.id.mark_text);
    }

    private void findTodayWeather() {
        main_weather = (TextView) findViewById(R.id.main_weather);
        main_temp = (TextView) findViewById(R.id.main_temp);
        main_rain = (TextView) findViewById(R.id.main_rain);
        main_reh = (TextView) findViewById(R.id.main_reh);
    }

    //건강관리 및 행동요령 리스트
    private void action_list() {
        recyclerView = findViewById(R.id.main_recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<ActionRecycleObject> adtionInfoArrayList = new ArrayList<>();
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.mask, "마스크", "상쾌한 날입니다. 좋은 공기 많이 마시세요!"));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.aircleaner, "공기청정기", "공기가 좋은 날입니다. 공기청정기 필터를 청소해주세요."));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.window, "창문", "오늘은 환기를 마음껏 하시길 바랍니다."));
        adtionInfoArrayList.add(new ActionRecycleObject(R.drawable.outdoor_activities, "야외활동", "야외활동하기 좋은 날입니다."));

        ActionInfoRecyclerAdapter actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(adtionInfoArrayList);

        recyclerView.setAdapter(actionInfoRecyclerAdapter);
    }

    //주간 날씨 정보
    private void weather_list(int condition) {
        recyclerView2 = findViewById(R.id.main_recycleView2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);

        ArrayList<WeatherRecycleObject> weatherInfoArrayList = new ArrayList<>();
        try {
            if (condition == 0) {
                for (int i = 0; i < DAY1.length(); i++) {
                    weatherInfoArrayList.add(
                            new WeatherRecycleObject(
                                    timeFormat(DAY1.getJSONObject(i).getString("hour")) + "시",
                                    AirGradeManager.getWeatherImageId(DAY1.getJSONObject(i).getString("wfKor")),
                                    DAY1.getJSONObject(i).getString("temp") + "°C",
                                    DAY1.getJSONObject(i).getString("wfKor"),
                                    R.drawable.humidity,
                                    DAY1.getJSONObject(i).getString("pop") + "%",
                                    R.drawable.precipitation,
                                    DAY1.getJSONObject(i).getString("reh") + "%"));
                }
            } else if (condition == 1) {
                for (int i = 0; i < DAY2.length(); i++) {
                    weatherInfoArrayList.add(
                            new WeatherRecycleObject(
                                    timeFormat(DAY2.getJSONObject(i).getString("hour")) + "시",
                                    AirGradeManager.getWeatherImageId(DAY2.getJSONObject(i).getString("wfKor")),
                                    DAY2.getJSONObject(i).getString("temp") + "°C",
                                    DAY2.getJSONObject(i).getString("wfKor"),
                                    R.drawable.humidity,
                                    DAY2.getJSONObject(i).getString("pop") + "%",
                                    R.drawable.precipitation,
                                    DAY2.getJSONObject(i).getString("reh") + "%"));
                }
            } else {
                for (int i = 0; i < DAY3.length(); i++) {
                    weatherInfoArrayList.add(
                            new WeatherRecycleObject(
                                    timeFormat(DAY3.getJSONObject(i).getString("hour")) + "시",
                                    AirGradeManager.getWeatherImageId(DAY3.getJSONObject(i).getString("wfKor")),
                                    DAY3.getJSONObject(i).getString("temp") + "°C",
                                    DAY3.getJSONObject(i).getString("wfKor"),
                                    R.drawable.humidity,
                                    DAY3.getJSONObject(i).getString("pop") + "%",
                                    R.drawable.precipitation,
                                    DAY3.getJSONObject(i).getString("reh") + "%"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        WeatherInfoRecyclerAdapter weatherInfoRecyclerAdapter = new WeatherInfoRecyclerAdapter(weatherInfoArrayList);

        recyclerView2.setAdapter(weatherInfoRecyclerAdapter);
    }

    private  String timeFormat(String time){
        if(Integer.parseInt(time)>=12){
            time = "오후 "+time;
        }
        else {
            time = "오전 "+time;
        }

        return time;
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

    private int markLocation(int gradeValue) {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int result = Math.round(12 * dm.density); // 처음 여백을 포함한 결과값
        int one = Math.round(77 * dm.density); //범위 길이 77dp

        if(gradeValue>=0 && gradeValue<30) {
            int size = Math.round(gradeValue * dm.density); // gradeValue에 따른 dp값
            result +=  size*(2.56);
        } else if(gradeValue>=30 && gradeValue <80) {
            int size = Math.round((gradeValue-30) * dm.density); // gradeValue에 따른 dp값
            result += one;
            result += (size*1.54);
        } else if(gradeValue>=80 && gradeValue <150) {
            int size = Math.round((gradeValue-80) * dm.density); // gradeValue에 따른 dp값
            result += 2*one;
            result +=  size*(1.1);
        } else if(gradeValue>=150 && gradeValue<227){
            int size = Math.round((gradeValue-150) * dm.density); // gradeValue에 따른 dp값
            result += 3*one;
            result += size;
        } else result =  0;

        return result;
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
            public void btnMise() {
                startActivity(new Intent(MainActivity.this, MiseInfoActivity.class));
            }

//            @Override
//            public void btnAlarm() {
//                closeMenu();
//            }

            @Override
            public void btnContact() {
                startActivity(new Intent(MainActivity.this, QnAActivity.class));
            }

            @Override
            public void btnInfo() {
                startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
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



