package com.example.graduationproject_2019_1.Activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.graduationproject_2019_1.R;
import com.example.graduationproject_2019_1.Request.UploadRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class UploadActivity extends Activity {

    private EditText comment;
    private String category="";


    ImageButton category1;
    ImageButton category2;
    ImageButton category3;
    ImageButton category4;
    ImageButton category5;
    ImageButton category6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);
        category4 = findViewById(R.id.category4);
        category5 = findViewById(R.id.category5);
        category6 = findViewById(R.id.category6);

        ImageButton back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this, ActionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        comment= findViewById(R.id.comment);

        ImageButton upload_btn = findViewById(R.id.upload);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("test",category + "   " + comment.getText().toString());

                if(category.equals("")){
                    Toast.makeText(UploadActivity.this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(comment.getText().toString().equals("")){
                    Toast.makeText(UploadActivity.this, "코멘트를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject participation = new JSONObject();
                try {
                    participation.put("id",0);
                    participation.put("comment", comment.getText().toString());
                    participation.put("category", category);

                    String result = new UploadRequest(UploadActivity.this).execute(participation).get();

                    Intent intent = new Intent(UploadActivity.this, ActionActivity.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        ImageButton cancel_btn = findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadActivity.this, ActionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClickCategory(View v){
        switch (v.getId()){
            case R.id.category1:
                category1.setBackgroundResource(R.drawable.bicycle_choice);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="걷거나 자전거 타기";
                break;
            case R.id.category2:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bux_choice);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="대중교통 이용하기";
                break;
            case R.id.category3:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car_choice);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="차량운행 자제하기";
                break;
            case R.id.category4:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.water_choice);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant);
                category="물 많이 마시기";
                break;
            case R.id.category5:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting_choice);
                category6.setBackgroundResource(R.drawable.plant);
                category="야외활동 자제하기";
                break;
            case R.id.category6:
                category1.setBackgroundResource(R.drawable.bicycle);
                category2.setBackgroundResource(R.drawable.bus);
                category3.setBackgroundResource(R.drawable.car);
                category4.setBackgroundResource(R.drawable.drinkwater);
                category5.setBackgroundResource(R.drawable.dustouting);
                category6.setBackgroundResource(R.drawable.plant_choice);
                category="공기정화식물 키우기";
                break;
        }
    }
}
