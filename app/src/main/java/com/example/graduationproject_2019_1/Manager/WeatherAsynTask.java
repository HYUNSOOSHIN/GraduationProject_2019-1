package com.example.graduationproject_2019_1.Manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.example.graduationproject_2019_1.Data.RegionCode;
import com.example.graduationproject_2019_1.Data.WeatherData;
import java.io.IOException;
import static com.example.graduationproject_2019_1.Data.WeatherData.WData;

//"http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0]"
//new WeatherAsynTask(weather).execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0]");


public class WeatherAsynTask extends AsyncTask {
    RegionCode R_Code = new RegionCode();
    Document[] document = new Document[25];
    Elements element[][] = new Elements[25][19];
    WeatherData weatherData = new WeatherData();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Object[] objects) {
        int cnt = 0;
        int day = -1;
        try {
            // 지역마다 홈페이지 접근
            for(int i=0; i<25; i++){
                document[i] = Jsoup.connect("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone="+R_Code.RCode[i]).get();
            }

            for(int i=0; i<25; i++){
                element[i][0] = document[i].select("item category");
                element[i][1] = document[i].select("data[seq=0] "+weatherData.WData[0]); // 오늘
                element[i][2] = document[i].select("data[seq=0] "+weatherData.WData[1]);
                element[i][3] = document[i].select("data[seq=0] "+weatherData.WData[2]);
                element[i][4] = document[i].select("data[seq=0] "+weatherData.WData[3]);
                element[i][5] = document[i].select("data[seq=0] "+weatherData.WData[4]);
                element[i][6] = document[i].select("data[seq=0] "+weatherData.WData[5]);
                element[i][7] = document[i].select("data[seq=1] "+weatherData.WData[0]); // 내일
                element[i][8] = document[i].select("data[seq=1] "+weatherData.WData[1]);
                element[i][9] = document[i].select("data[seq=1] "+weatherData.WData[2]);
                element[i][10] = document[i].select("data[seq=1] "+weatherData.WData[3]);
                element[i][11] = document[i].select("data[seq=1] "+weatherData.WData[4]);
                element[i][12] = document[i].select("data[seq=1] "+weatherData.WData[5]);
                element[i][13] = document[i].select("data[seq=2] "+weatherData.WData[0]); // 모레
                element[i][14] = document[i].select("data[seq=2] "+weatherData.WData[1]);
                element[i][15] = document[i].select("data[seq=2] "+weatherData.WData[2]);
                element[i][16] = document[i].select("data[seq=2] "+weatherData.WData[3]);
                element[i][17] = document[i].select("data[seq=2] "+weatherData.WData[4]);
                element[i][18] = document[i].select("data[seq=2] "+weatherData.WData[5]);
            }

/*
            for(int i=0; i<25; i++){
                for(int j=0; j<18; j++){
                    if((cnt % 6)== 0) {
                        day++;
                        cnt = 0;
                    }
                    element[i][j] = document[i].select("data[seq="+Integer.toString(day)+"] "+weatherData.WData[cnt]);
                    cnt++;
                }
            }
*/
            /*
            for(Element element : elements){
                result = result+element.text()+"\n";
            }
            System.out.println(result);
            */
            for(int i=0; i<25; i++){
                for(int j=0; j<19; j++){
                    //System.out.println(element[i][j]);
                    Log.d("test1111", String.valueOf(element[i][j].text()));
                }
            }
            //Log.d("test1111", result);
            //return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    /*
    public class WeatherData {
    //public String dong; // 어디 동인지?
    public String hour; // 받아온 시간
    public String day; // 받아온 날짜, 0: 오늘, 1: 내일, 2: 모레
    public String temp;  // 온도
    public String wfKor; // ex 맑음
    public String pop; // 강수확률
    public String reh; // 습도
    }
     */

    //@Override
    //protected void onPostExecute(String s){
    //super.onPostExecute(s);
    //    textView.setText(s);
    //}
}