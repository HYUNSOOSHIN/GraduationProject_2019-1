package com.example.graduationproject_2019_1.Activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.graduationproject_2019_1.R;

import java.util.Random;

public class AppWidget extends AppWidgetProvider{


    private static final String ACTION_BUTTON1 = "com.example.graduationproject_2019_1.BUTTON1";


    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), AppWidget.class.getName());
        int[] appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget);

        final String action = intent.getAction();
        if(action.equals(ACTION_BUTTON1)){
            //your code here
            Toast.makeText(context, "버튼을 클릭했어요.", Toast.LENGTH_LONG).show();
            onUpdate(context, appWidgetManager, appWidgets);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);

        // 제목 부분
        //시작되면서 동적으로 타이틀 넣고 스타일 설정하기
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        views.setTextColor(R.id.appwidget_text, Color.WHITE);
        views.setViewPadding(R.id.appwidget_text, 8, 8, 8, 8);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // 숫자 부분
        //랜덤 값을 만들어 화면에 출력해 보기
        int number = (new Random().nextInt(100));
        views.setViewPadding(R.id.message_text, 0, 8,0,8);
        views.setTextColor(R.id.message_text, Color.YELLOW);
        views.setTextViewText(R.id.message_text, String.valueOf(number));


        // 이미지 부분
        //시작되면서 지정 이미지로 교체
        views.setImageViewResource(R.id.imageView, R.drawable.ic_android_black_24dp); // this is for test

        //버튼1 클릭 : 클릭 성공 메세지 출력!  --> 해당 버튼을 새로고침 버튼으로 만들어야 함
        Intent intent1 = new Intent(ACTION_BUTTON1);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.resetBtn, pendingIntent1);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}
