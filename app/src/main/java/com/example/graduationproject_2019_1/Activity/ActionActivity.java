package com.example.graduationproject_2019_1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Adapter.RecyclerAdapter;
import com.example.graduationproject_2019_1.Data.RecycleObject;
import com.example.graduationproject_2019_1.R;

import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends Activity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button apply_btn = findViewById(R.id.apply_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        //위
        recyclerView = findViewById(R.id.action_recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<RecycleObject> foodInfoArrayList = new ArrayList<>();
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "수분보충","하루 물 8잔 이상 마시면 물로 인해 먼지를 걸러주게 되고 호흡기에 수분을 보충해줍니다."));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "차량2부제","미세먼지 비상저감조치가 발령됐을시 자발적인 차량 2부제로 미세먼지를 줄여보세요"));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "친환경 자동차","친환경 자동차의 보급을 위해 친환경차 협력금제"));
        foodInfoArrayList.add(new RecycleObject(R.drawable.tmp, "미세먼지 관리 종합대책","미세먼지 저감을 위한 노력은 산업부분에서도 이루어집니다."));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(foodInfoArrayList);

        recyclerView.setAdapter(recyclerAdapter);
    }

    public void leftbtn(View v) {
        // Shows a Toast message in response to button
        Toast.makeText(getApplicationContext(), "왼쪽",Toast.LENGTH_SHORT).show();
    }

    public void rightbtn(View v) {
        // Shows a Toast message in response to button
        Toast.makeText(getApplicationContext(), "오른쪽",Toast.LENGTH_SHORT).show();
    }
}
