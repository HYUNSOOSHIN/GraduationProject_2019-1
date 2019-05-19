package com.example.graduationproject_2019_1.Activity;


import android.app.Activity;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

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
        ImageButton imageButton = findViewById(v.getId());
        switch (v.getId()){
            case R.id.category1:
                category="걷거나 자전거 타기";
                break;
            case R.id.category2:
                category="대중교통 이용하기";
                break;
            case R.id.category3:
                category="차량운행 자제하기";
                break;
            case R.id.category4:
                category="물 많이 마시기";
                break;
            case R.id.category5:
                category="야외활동 자제하기 ";
                break;
            case R.id.category6:
                category="공기정화식물 키우기";
                break;
        }
    }
}
