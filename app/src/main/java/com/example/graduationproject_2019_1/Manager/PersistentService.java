package com.example.graduationproject_2019_1.Manager;

import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.graduationproject_2019_1.Activity.MainActivity;
import com.example.graduationproject_2019_1.R;

// MainActivity에서 intent시킨 PersistenService class 를 구현
public class PersistentService extends Service implements Runnable {

    private static final String TAG = "PersistentService";
    private static final int NOTI_ID = 100;
    String pm10_data;
    String pm25_data;
    String temp_data;
    String wfKor_data;
    String pop_data;
    String reh_data;

    // 서비스 종료시 재부팅 딜레이 시간, activity의 활성 시간을 벌어야 한다. (10초)
    private static final int REBOOT_DELAY_TIMER = 10 * 1000;

    // GPS를 받는 주기 시간 (5분: 5 * 60 * 1000)
    private static final int LOCATION_UPDATE_DELAY = 10* 1000; // 테스트용으로 짧게 설정함

    private Handler mHandler;
    private boolean mIsRunning;
    private int mStartId = 0;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("PersistentService", "onBind()");
        return null;
    }

    @Override
    public void onCreate(){
        // 등록된 알람은 제거
        Log.d("PersistentService", "onCreate()");
        unregisterRestartAlarm();

        super.onCreate();

        mIsRunning = false;
    }

    @Override
    public void onDestroy() {

        // 서비스가 죽었을때 알람 등록
        Log.d("PersistentService", "onDestroy()");
        registerRestartAlarm();

        super.onDestroy();

        mIsRunning = false;
    }

    /**
     * @see android.app.Service#onStart(android.content.Intent, int)
     * 서비스가 시작되었을때 run()이 실행되기까지 delay를 handler를 통해서 주고 있다.
     */

    //onStart의 return type에 관한 설명

	/*
	START_NOTSTICKY
	system이 service를 죽였을 때, pending intent가 없는 한 service를 다시 실행시키지 않는다.
	가장 안전한 return type

	START_STICKY
	system이 onStartCommand()가 return 한 후 service를 죽였다면, service를 다시 create 하면서 onStartCommand()를 부른다.
	하지만 last intent를 다시 전달하지는 않는다. pending intent가 없다면 null intent가 전달된다.
	MediaPlayer와 같이 자기 일을 묵묵히 하고 request가 간혹 들어오는 type의 service에 더욱 적합하다.

	STARTREDELIVER_INTENT
	system이 service를 죽였을 때 service를 다시 만들고 onStartCommand()를 부르는데,
	이 녀석은 last intent를 전달받는다. 그 다음에 pending intent가 있다면 추후에 전달된다.
	downloading같은 즉각적인 resume이 필요한 service에 적합하다.
	 */

    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.d("PersistentService", "onStart()");
        super.onStartCommand(intent, flag, startId);

        pm10_data = intent.getStringExtra("mise");
        pm25_data = intent.getStringExtra("cho_mise");
        temp_data = intent.getStringExtra("temp");
        wfKor_data = intent.getStringExtra("wfKor");
        pop_data = intent.getStringExtra("pop");
        reh_data = intent.getStringExtra("reh");

        mStartId = startId;

        // 5분후에 시작
        mHandler = new Handler();
        mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
        mIsRunning = true;

        return START_REDELIVER_INTENT;

    }


    /**
     * @see java.lang.Runnable#run()
     * 서비스가 돌아가고 있을때 실제로 내가 원하는 기능을 구현하는 부분
     */
    @Override
    public void run() {
        Log.e(TAG, "run()");

        if(!mIsRunning) {
            Log.d("PersistentService", "run(), mIsRunning is false");
            Log.d("PersistentService", "run(), alarm service end");

            return;

        } else {
            Log.d("PersistentService", "run(), mIsRunning is true");
            Log.d("PersistentService", "run(), alarm repeat after five minutes");

            alarm_function();
            mHandler.postDelayed(this, LOCATION_UPDATE_DELAY);
            mIsRunning = true;
        }
    }

    /**
     * 서비스가 시스템에 의해서 또는 강제적으로 종료되었을 때 호출되어
     * 알람을 등록해서 10초 후에 서비스가 실행되도록 한다.
     */
    // RestartService class는 PersistenService에서 register or unregisterRestartAlarm이 호출됐을 때 call을 받게 된다.
    // RestartService class에서는 Broadcast의 이벤트를 감지해서 서비스를 다시 살리는 부분을 구현
    // smart phone의 전원이 공급되어 최초 부팅이 되었을 때에도 service가 작동하도록 구현
    private void registerRestartAlarm() {

        Log.d("PersistentService", "registerRestartAlarm()");

        Intent intent = new Intent(PersistentService.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += REBOOT_DELAY_TIMER; // 10초 후에 알람이벤트 발생

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,REBOOT_DELAY_TIMER, sender);
    }

    /**
     * 기존 등록되어있는 알람을 해제한다.
     */
    private void unregisterRestartAlarm() {

        Log.d("PersistentService", "unregisterRestartAlarm()");
        Intent intent = new Intent(PersistentService.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_PERSISTENTSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }


    public void alarm_function(){
        //날씨정보 받아오기
        //Intent weather_data = new Intent(this.getIntent());
        //get_location_gu_intent = intent_location.getStringExtra("searching_location_gu");

        initChannel(); // 채널 설정, 알람 셋팅완료 --> 알람에 스타일 정하기

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("notificationId", 9999); //전달할 값

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //어떤 알림을 보낼지 생성해줌
        NotificationCompat.Builder notification = new NotificationCompat.Builder(PersistentService.this, "default");
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


