package com.example.graduationproject_2019_1.Request;

import android.app.Activity;
import com.example.graduationproject_2019_1.Data.Url;
import java.net.MalformedURLException;
import java.net.URL;

public class GetListRequest extends GetRequest {

    public GetListRequest(Activity activity) {
        super(activity);
    }

    @Override
    protected void onPreExecute() {
        try {
            String ip = Url.SPRING_SERVER;
            url = new URL(ip+"/get_list");  // http://serverURLStr/get-data
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        //Toast.makeText(activity,jsonString,Toast.LENGTH_SHORT).show();
    }

}
