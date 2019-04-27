package com.example.graduationproject_2019_1.Manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.graduationproject_2019_1.Activity.MainActivity;
import com.example.graduationproject_2019_1.R;

public class MyService extends Service {
    ServiceThread thread;
    private static final int NOTI_ID = 100;
    String pm10_data;
    String pm25_data;
    String temp_data;
    String wfKor_data;
    String pop_data;
    String reh_data;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    /*
public void Alarm() {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 19, 2, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
}
 */

    // 스레드로 무한히 특정시간마다 작동시키기 위함
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pm10_data = intent.getStringExtra("mise");
        pm25_data = intent.getStringExtra("cho_mise");
        temp_data = intent.getStringExtra("temp");
        wfKor_data = intent.getStringExtra("wfKor");
        pop_data = intent.getStringExtra("pop");
        reh_data = intent.getStringExtra("reh");
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();

        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업
    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }


    // 이 부분 알림 설정 바꿔야 됨.
    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            //날씨정보 받아오기
            //Intent weather_data = new Intent(this.getIntent());
            //get_location_gu_intent = intent_location.getStringExtra("searching_location_gu");


            initChannel(); // 채널 설정, 알람 셋팅완료 --> 알람에 스타일 정하기
            //Intent notificationIntent = new Intent(MyService.this, NotificationDetail.class); // MyService.this에서 NotificationDetail.class로 보내는 intent
            Intent notificationIntent = new Intent(MyService.this, MainActivity.class); // MyService.this에서 NotificationDetail.class로 보내는 intent
            notificationIntent.putExtra("notificationId", 9999); //전달할 값

            PendingIntent contentIntent = PendingIntent.getActivity(MyService.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //어떤 알림을 보낼지 생성해줌
            NotificationCompat.Builder notification = new NotificationCompat.Builder(MyService.this, "default");
            notification.setContentTitle("현재 기상정보")
                    .setContentText("미세먼지: " +pm10_data+"| 초미세먼지: "+pm25_data+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data)
                    .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("현재 기상정보")
                        .bigText("미세먼지: " +pm10_data+"| 초미세먼지: "+pm25_data+"| 온도: "+temp_data+"| 날씨: "+wfKor_data+"| 강수확률: "+pop_data+"| 습도: "+reh_data))
                    .setSmallIcon(R.drawable.tmp)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
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
    };

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

}
