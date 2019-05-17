package com.example.graduationproject_2019_1.Activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.graduationproject_2019_1.R;

public class SideBarView extends RelativeLayout implements View.OnClickListener {


    /**
     * 메뉴버튼 클릭 이벤트 리스너
     */
    public EventListener listener;

    public void setEventListener(EventListener l) {
        listener = l;
    }

    /**
     * 메뉴버튼 클릭 이벤트 리스너 인터페이스
     */
    public interface EventListener { // 닫기 버튼 클릭 이벤트
        void btnCancel();
        void btnMise();
        void btnAlarm();
    }

    public SideBarView(Context context) {
        this(context, null);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.sidebar, this, true);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.mise_btn).setOnClickListener(this);
        findViewById(R.id.alarm_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                listener.btnCancel();
                break;
            case R.id.mise_btn:
                listener.btnMise();
                break;
            case R.id.alarm_btn:
                listener.btnAlarm();
                break;
            default:
                break;
        }
    }

}
