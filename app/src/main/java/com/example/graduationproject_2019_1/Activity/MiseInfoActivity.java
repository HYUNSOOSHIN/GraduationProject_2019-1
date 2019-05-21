package com.example.graduationproject_2019_1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.graduationproject_2019_1.Adapter.ActionInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Adapter.MiseInfoRecyclerAdapter;
import com.example.graduationproject_2019_1.Adapter.ParticipationRecyclerAdapter;
import com.example.graduationproject_2019_1.Data.ActionRecycleObject;
import com.example.graduationproject_2019_1.Data.MiseInfoRecycleObject;
import com.example.graduationproject_2019_1.Data.ParticipationRecycleObject;
import com.example.graduationproject_2019_1.R;
import com.example.graduationproject_2019_1.Request.GetListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MiseInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mise_info);

        ImageButton backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MiseInfoActivity.this,MainActivity.class));
                finish();
            }
        });

        recyclerView = findViewById(R.id.mise_recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<MiseInfoRecycleObject> miseInfoArrayList = new ArrayList<>();
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i1,"최고", "0~15","0~8","0~0.02","0~0.02","0~1","0~0.01"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i2,"좋음","16~30","9~15","0.02~0.03","0.02~0.03","1~2","0.01~0.02"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i3,"양호","31~40","16~20","0.03~0.06","0.03~0.05","2~5.5","0.02~0.04"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i4,"보통","41~50","21~25","0.06~0.09","0.05~0.06","5.5~9","0.04~0.05"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i5,"나쁨","51~75","26~37","0.09~0.12","0.06~0.13","9~12","0.05~0.1"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i6,"상당히 나쁨","76~100","38~50","0.12~0.15","0.13~0.2","12~15","0.1~0.15"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i7,"매우 나쁨","101~150","51~75","0.15~0.38","0.2~1.1","15~32","0.15~0.6"));
        miseInfoArrayList.add(new MiseInfoRecycleObject(R.drawable.i8,"최악","151~","76~","0.38~","1.1~2","32~","0.6~"));

        MiseInfoRecyclerAdapter miseInfoRecyclerAdapter = new MiseInfoRecyclerAdapter(miseInfoArrayList);

        Log.e("test", miseInfoRecyclerAdapter.getItemCount()+"");

        recyclerView.setAdapter(miseInfoRecyclerAdapter);
    }
}
