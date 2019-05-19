package com.example.graduationproject_2019_1.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Data.GpsInfo;
import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.Manager.AsyncManager;
import com.example.graduationproject_2019_1.Manager.CityLocationManager;
import com.example.graduationproject_2019_1.Manager.JSONManager;
import com.example.graduationproject_2019_1.Manager.URLParameterManager;
import com.example.graduationproject_2019_1.Manager.WeatherAsynTask;
import com.example.graduationproject_2019_1.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class AppWidget extends AppWidgetProvider{

    AppWidgetManager appWidgetManager;
    ComponentName thisAppWidget;
    int[] appWidgets;

    SharedPreferences sharedPreferences;

    // 날씨 정보에 사용되는 변수
    String pm10_detail = null;
    String pm25_detail = null;
    String temp_data = null;
    String wfKor_data = null;
    String pop_data = null;
    String reh_data = null;
    String result_weather = null;

    public String GU = null;
    public String DONG = null;

    // GPS 관련
    public double latitude;
    public double longitude;
    public String string_location;
    public String[] array_location;
    public GpsInfo gps;

    private static final String LOG = "refresh";
    //private static final String ACTION_BUTTON1 = "com.example.graduationproject_2019_1.BUTTON1";
    private static final String ACTION_BUTTON1 = "android.appwidget.action.APPWIDGET_UPDATE";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);

        //버튼1 클릭 : 클릭 성공 메세지 출력!
        Intent intent1 = new Intent(ACTION_BUTTON1);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.button_refresh, pendingIntent1);

        // 위치, 날씨정보 update
        GPS_function(context);
        set_Locaion_Weather(context);

        // 제목 부분
        //시작되면서 동적으로 타이틀 넣고 스타일 설정하기
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        String title = GU + " " + DONG + " 현재 기상정보";
        views.setTextColor(R.id.appwidget_text, Color.WHITE);
        views.setViewPadding(R.id.appwidget_text, 8, 8, 8, 8);
        views.setTextViewText(R.id.appwidget_text, title);

        // 숫자 부분
        //랜덤 값을 만들어 화면에 출력해 보기
        int number = (new Random().nextInt(100)); // 새로고침 테스트 용도
        String body = "미세먼지: " +pm10_detail+"| 초미세먼지: "+pm25_detail+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data;
        views.setViewPadding(R.id.message_text, 0, 8,0,8);
        views.setTextColor(R.id.message_text, Color.YELLOW);
        views.setTextViewText(R.id.message_text, body);
        //views.setTextViewText(R.id.message_text, String.valueOf(number)); // 새로고침 테스트 용도

        // 이미지 부분
        views.setImageViewResource(R.id.imageView, R.drawable.loading); // this is for test

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    //onReceive : 브로드캐스트가 왔을 때 호출된다.
    // onReceive에서 onUpdate를 호출해주면 되는 것
    @Override
    public void onReceive(Context context, Intent intent){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName testWidget = new ComponentName(context.getPackageName(), AppWidget.class.getName());
        int[] widgetIds = appWidgetManager.getAppWidgetIds(testWidget);

        String action = intent.getAction();
        Log.d(LOG, "action: " + action);

        if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            if(widgetIds != null && widgetIds.length>0){
                this.onUpdate(context, AppWidgetManager.getInstance(context), widgetIds);
            }
        }

    }

    //onUpdate : 위젯 xml 파일 내의 android:updatePeriodMillis 값에 따라 주기적으로 호출된다. 위젯을 갱신하는 함수를 여기 넣으면 된다.(주기는 최소 30분으로 제한)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public void set_Locaion_Weather(Context context){
        if(GU.equals(null)){
            sharedPreferences = context.getSharedPreferences("hyunsoo", Context.MODE_PRIVATE);
            GU = sharedPreferences.getString("gu","영등포구");
            DONG = sharedPreferences.getString("dong","당산1동");

            Log.d(LOG, "GU : " + GU + ", DONG : " + DONG);
        }

        try {
            result_weather = (String) new WeatherAsynTask(GU, DONG).execute().get();
            JSONArray jsonArray = new JSONArray(result_weather);

            temp_data = jsonArray.getJSONObject(1).getString("temp") + "°C";
            wfKor_data = jsonArray.getJSONObject(1).getString("wfKor");
            pop_data = jsonArray.getJSONObject(1).getString("pop") + "%";
            reh_data = jsonArray.getJSONObject(1).getString("reh") + "%";

            AsyncManager manager = AsyncManager.getInstance();
            String nm = CityLocationManager.getNMbyCityName(GU);
            String a = manager.make(Url.REAL_TIME_CITY_AIR, URLParameterManager.getRequestString(nm, GU));

            Map<String, String> parsedData = JSONManager.parse(a);

            pm10_detail = parsedData.get("PM10");
            pm25_detail = parsedData.get("PM25");

            if(wfKor_data.equals(null)){
                wfKor_data = sharedPreferences.getString("wfKor", "맑음");
                temp_data = sharedPreferences.getString("temp", "20°C");
                pop_data = sharedPreferences.getString("pop", "0%");
                reh_data = sharedPreferences.getString("reh", "10%");
                pm10_detail = sharedPreferences.getString("PM10", "60");
                pm25_detail = sharedPreferences.getString("PM25", "45");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void GPS_function(Context context) {
        Geocoder mGeoCoder = new Geocoder(context);

        gps = new GpsInfo(context);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            try {
                List<Address> mResultList = mGeoCoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                Log.d(LOG, "onComplete: " + mResultList.get(0).getAddressLine(0));
                string_location = mResultList.get(0).getAddressLine(0);
                array_location = string_location.split(" ");
                GU = array_location[2];
                DONG = array_location[3];

                Log.d(LOG, "GU : " + GU + ", DONG : " + DONG);
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
