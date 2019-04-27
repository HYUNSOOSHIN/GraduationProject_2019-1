package com.example.graduationproject_2019_1.Manager;


import android.os.Handler;
import java.util.Calendar;


public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
                Thread.sleep(1000 * 10 * 6); //60초씩 쉰다.
            }catch (Exception e) {}
        }
    }
}

/*
    // 이렇게 해도 안되네
    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            try{
                Thread.sleep(1000 * 10 ); //60초씩 쉰다.
                Calendar calendar = Calendar.getInstance();
                if(Integer.toString(calendar.get(Calendar.HOUR)).equals("12") && Integer.toString(calendar.get(Calendar.MINUTE)).equals("01")){
                    handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
                }
            }catch (Exception e) {}
        }
    }
*/




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