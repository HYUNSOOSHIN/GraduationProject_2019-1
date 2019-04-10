package com.example.graduationproject_2019_1.Manager;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.graduationproject_2019_1.Data.Region;
import com.example.graduationproject_2019_1.Data.RegionCode;
import com.example.graduationproject_2019_1.Data.WeatherData;
import java.io.IOException;

public class WeatherAsynTask extends AsyncTask {
    public String gu;
    public String dong;
    public int region_code;
    public int region_index;

    Region region[] = new Region[449];
    Document document;
    JSONArray jsonArray;
    WeatherData weatherData = new WeatherData();


    public WeatherAsynTask(String GU, String DONG) {
        this.gu = GU;
        this.dong = DONG;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Object[] objects) {

        RegionCode regionCode = new RegionCode();

        region_index = regionCode.find_Code(dong);
        region_code = regionCode.find_Index(dong);

        try{
            document = Jsoup.connect("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=" + region_code).get();
            jsonArray = new JSONArray();
            //날씨데이터 갯수
            Element count = document.select("data").last();
            int countt = Integer.parseInt(count.toString().substring(count.toString().indexOf('"')+1,count.toString().indexOf('>')-1));
            jsonArray.put(countt);
            //
            for(int i=0; i<=countt; i++) {
                Elements hour = document.select("data[seq="+i+"] "+weatherData.WData[0]);
                Elements day = document.select("data[seq="+i+"] "+weatherData.WData[1]);
                Elements temp = document.select("data[seq="+i+"] "+weatherData.WData[2]);
                Elements wfKor = document.select("data[seq="+i+"] "+weatherData.WData[3]);
                Elements pop = document.select("data[seq="+i+"] "+weatherData.WData[4]);
                Elements reh = document.select("data[seq="+i+"] "+weatherData.WData[5]);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hour",hour.toString().substring(hour.toString().indexOf(" ")+1,hour.toString().lastIndexOf("\n")));
                jsonObject.put("day",day.toString().substring(day.toString().indexOf(" ")+1,day.toString().lastIndexOf("\n")));
                jsonObject.put("temp",temp.toString().substring(temp.toString().indexOf(" ")+1,temp.toString().lastIndexOf("\n")));
                jsonObject.put("wfKor", wfKor.toString().substring(wfKor.toString().indexOf(" ")+1,wfKor.toString().lastIndexOf("\n")));
                jsonObject.put("pop",pop.toString().substring(pop.toString().indexOf(" ")+1,pop.toString().lastIndexOf("\n")));
                jsonObject.put("reh",reh.toString().substring(reh.toString().indexOf(" ")+1,reh.toString().lastIndexOf("\n")));
                jsonArray.put(jsonObject);
//                Log.i("test", "hyunsoo: "+hourr);
//                Log.i("test", "hyunsoo: "+jsonObject);
//                Log.i("test", "hyunsoo: "+jsonArray);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
    @Override
    protected void onProgressUpdate(Object[] objects) {

    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
    }


}