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
import android.widget.TextView;
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

    private int status=1;
    private TextView pagetext;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;

    ArrayList<ActionRecycleObject> actionInfoArrayList;
    ArrayList<ActionRecycleObject> actionInfoArrayList2;

    ActionInfoRecyclerAdapter actionInfoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        pagetext = findViewById(R.id.pagetext);

        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton apply_btn = findViewById(R.id.apply_btn);
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

        //1페이지
        actionInfoArrayList = new ArrayList<>();
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.bicycle, "걷거나 자전거 타기","가까운 곳은 승용차 대신 두 발로 걷거나 자전거를 타고 가면 어떨까요?"));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.bus, "대중교통 이용하기","대중교통을 이용하면 에너지 절약과 기후변화 완화, 미세먼지 저감에 기여할 수 있습니다."));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.car, "차량 운행을 자제하기","대기 오염의 가장 큰 원인은 자동차입니다. 스스로 차량 운행만 줄여도 교통량과 혼잡비용을 절약하며 대기 오염을 해결할 수 있는 길입니다."));
        actionInfoArrayList.add(new ActionRecycleObject(R.drawable.diesel, "경유차 구매를 자제하기","경유차의 배기가스는 우리가 생각하는 이상으로 위험합니다. 초미세먼지의 원인 물질인 질소산화물에서 67%나 배출됩니다."));

        //2페이지
        actionInfoArrayList2 = new ArrayList<>();
        actionInfoArrayList2.add(new ActionRecycleObject(R.drawable.plant, "공기정화식물 키우기","식물은 오염 물질 제거뿐만 아니라 음이온,산소,수분등으로 실내 공기를 쾌적하게 해줍니다."));
        actionInfoArrayList2.add(new ActionRecycleObject(R.drawable.cook, "요리 시 직화 구이를 삼가하기","음식을 조리하는 과정에서도 많은 미세먼지가 발생합니다. 가급적 굽거나 튀기는 요리를 자제하고 환기와 조리용 후드를 꼭 이용해 주세요 "));
        actionInfoArrayList2.add(new ActionRecycleObject(R.drawable.drinkwater, "물 많이 마시기","미세먼지에 좋은 음식이라면 바로 물입니다. 하루이 2L이상 충분한 물을 마시면 기관지나 혈액에 있는 미세먼지를 배출하는데 큰 도움이 됩니다."));
        actionInfoArrayList2.add(new ActionRecycleObject(R.drawable.dustouting, "미세먼지가 심할 때 외출을 자제하기","자신의 건강 피해를 최소화하기 위해 미세먼지 농도를 확인하고 심할경우 외출을 자제해야 합니다."));

        actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(actionInfoArrayList);
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

    public void btnClick(View v) {
        switch (status){
            case 1:
                status=2;
                actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(actionInfoArrayList2);
                recyclerView.setAdapter(actionInfoRecyclerAdapter);
                pagetext.setText("2/2");
                break;

            case 2:
                status=1;
                actionInfoRecyclerAdapter = new ActionInfoRecyclerAdapter(actionInfoArrayList);
                recyclerView.setAdapter(actionInfoRecyclerAdapter);
                pagetext.setText("1/2");
                break;
        }
    }
}
