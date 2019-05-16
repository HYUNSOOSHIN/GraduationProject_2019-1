package com.example.graduationproject_2019_1.Manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.graduationproject_2019_1.Activity.MainActivity;
import com.example.graduationproject_2019_1.Data.Url;
import com.example.graduationproject_2019_1.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FireBaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private SharedPreferences sharedPreferences;

    // 날씨 정보에 사용되는 변수
    String pm10_detail = null;
    String pm25_detail = null;
    String temp_data = null;
    String wfKor_data = null;
    String pop_data = null;
    String reh_data = null;
    String result_weather = null;
    private static final int NOTI_ID = 100;

    JSONArray DAY1;

    public String GU;
    public String DONG;

    // onMessageReceived(..) 함수는 foreground에서만 작동, 따라서 Background 로직 구현해주기
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            alarm_function(remoteMessage.getNotification().getBody());
        }


        if(remoteMessage.getData() != null){
            Log.d(TAG, "Message Notification Data: " + remoteMessage.getData().get("message"));
            alarm_function(remoteMessage.getData().get("message"));
            Log.d(TAG, "Weather Data: " + "미세먼지: " +pm10_detail+"| 초미세먼지: "+pm25_detail+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data);
        }
    }

    public void alarm_function(String messageBody){
        messageBody = messageBody.trim();
        if(messageBody.equals("background")){
            set_Locaion_Weather(); // 위치정보, 기상정보 받아오기
            initChannel(); // 채널 설정, 알람 셋팅완료 --> 알람에 스타일 정하기

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra("notificationId", 9999); //전달할 값

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //어떤 알림을 보낼지 생성해줌
            NotificationCompat.Builder notification = new NotificationCompat.Builder(FireBaseMessagingService.this, "default");
            notification.setContentTitle(GU + " " + DONG + " 현재 기상정보")
                    .setContentText("미세먼지: " +pm10_detail+"| 초미세먼지: "+pm25_detail+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .setBigContentTitle(GU + " " + DONG + " 현재 기상정보")
                            .bigText("미세먼지: " +pm10_detail+"| 초미세먼지: "+pm25_detail+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data))
                    .setSmallIcon(R.drawable.loading)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.loading))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL);

            //소리추가
            notification.setDefaults(Notification.DEFAULT_SOUND);

            // 설정된 notification을 호출하여 시스템에 띄워줌
            // NotificationManager 객체의 notify() 메소드 호출해서 Notification 객체를 전달
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTI_ID, notification.build());
        }

    }

    public void initChannel() {
        if (android.os.Build.VERSION.SDK_INT >=26) {
            // The id of the channel.
            String id = "default";

            // The user-visible name of the channel.
            CharSequence name = getString(R.string.channel_name);

            // The user-visible description of the channel.
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_HIGH;

            // 채널 id: default, name: 채널 이름, importance: 알람 중요도
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);

            // notification 채널 상세 설정
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel); // NotificationManager가 설정된 알람을 적용
        }
    }

    public void set_Locaion_Weather(){
        sharedPreferences = getSharedPreferences("hyunsoo", MODE_PRIVATE);
        GU = sharedPreferences.getString("gu","영등포구");
        DONG = sharedPreferences.getString("dong","당산1동");

        Log.d(TAG, "GU : " + GU + ", DONG : " + DONG);

        try {
            result_weather = (String) new WeatherAsynTask(GU, DONG).execute().get();
            JSONArray jsonArray = new JSONArray(result_weather);
            DAY1 = new JSONArray();

            int count = jsonArray.getInt(0);

            for (int i = 1; i <= count; i++) {
                if (jsonArray.getJSONObject(i).getString("day").equals("0")) { //오늘
                    DAY1.put(jsonArray.getJSONObject(i));
                }
            }

            temp_data = DAY1.getJSONObject(0).getString("temp") + "°C";
            wfKor_data = DAY1.getJSONObject(0).getString("wfKor");
            pop_data = DAY1.getJSONObject(0).getString("pop") + "%";
            reh_data = DAY1.getJSONObject(0).getString("reh") + "%";

            AsyncManager manager = AsyncManager.getInstance();
            String nm = CityLocationManager.getNMbyCityName(GU);
            String a = manager.make(Url.REAL_TIME_CITY_AIR, URLParameterManager.getRequestString(nm, GU));

            Map<String, String> parsedData = JSONManager.parse(a);

            pm10_detail = parsedData.get("PM10");
            pm25_detail = parsedData.get("PM25");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
