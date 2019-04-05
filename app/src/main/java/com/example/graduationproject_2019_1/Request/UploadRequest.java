package com.example.graduationproject_2019_1.Request;

import android.app.Activity;

import com.example.graduationproject_2019_1.Data.Url;

import java.net.MalformedURLException;
import java.net.URL;

public class UploadRequest extends PostRequest {

        public UploadRequest(Activity activity) {
            super(activity);
        }

        @Override
        protected void onPreExecute() {
            try {
                String ip = Url.SPRING_SERVER;
                url = new URL(  ip+ "/upload");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

}
