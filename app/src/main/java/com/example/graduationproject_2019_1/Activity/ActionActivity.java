package com.example.graduationproject_2019_1.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.graduationproject_2019_1.Adapter.ActionInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Adapter.ParticipationRecyclerAdapter;
import com.example.graduationproject_2019_1.Data.ActionRecycleObject;
import com.example.graduationproject_2019_1.Data.ParticipationRecycleObject;
import com.example.graduationproject_2019_1.R;
import com.example.graduationproject_2019_1.Request.GetListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActionActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;

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

        ArrayList<ActionRecycleObject> actionInfoArrayList = new ArrayList<>();
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.mask, "수분보충","하루 물 8잔 이상 마시면 물로 인해 먼지를 걸러주게 되고 호흡기에 수분을 보충해줍니다."));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.mask, "차량2부제","미세먼지 비상저감조치가 발령됐을시 자발적인 차량 2부제로 미세먼지를 줄여보세요"));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.mask, "친환경 자동차","친환경 자동차의 보급을 위해 친환경차 협력금제"));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.mask, "미세먼지 관리 종합대책","미세먼지 저감을 위한 노력은 산업부분에서도 이루어집니다."));

        ActionInfoRecyclerAdapter actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(actionInfoArrayList);

        recyclerView.setAdapter(actionInfoRecyclerAdapter);

        //아래
        recyclerView2 = findViewById(R.id.action_recycleView2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);

        ArrayList<ParticipationRecycleObject> getListArrayList = new ArrayList<>();
        try {
            String result = new GetListRequest(ActionActivity.this).execute().get();
            JSONArray jsonArray = new JSONArray (result);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                getListArrayList.add(new ParticipationRecycleObject(R.drawable.tmp, jsonObject.getString("category"), jsonObject.getString("comment")));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParticipationRecyclerAdapter participationRecyclerAdapter = new ParticipationRecyclerAdapter(getListArrayList);

        recyclerView2.setAdapter(participationRecyclerAdapter);
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
