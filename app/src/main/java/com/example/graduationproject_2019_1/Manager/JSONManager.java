package com.example.graduationproject_2019_1.Manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JSONManager {

    private JSONManager() {
    }

    public static Map<String, String> parse(String jsonString) {
        Map<String, String> result = new HashMap<>();

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONObject whole = new JSONObject(object.get("RealtimeCityAir").toString());
//            Log.i("test_parse", object.toString());
//            Log.i("test_parse", whole.toString());

            JSONObject requestResult = new JSONObject(whole.get("RESULT").toString());
            String resultCode = requestResult.get("CODE").toString();

            if (!resultCode.equals("INFO-000")) {
                Log.e("test", "Error on parsing");
                return result;
            }

            JSONArray row = new JSONArray(whole.getJSONArray("row").toString());
            JSONObject rowData = row.getJSONObject(0);

            String msrdt = rowData.get("MSRDT").toString();
            String msrrgn_nm = rowData.get("MSRRGN_NM").toString();
            String msrste_nm = rowData.get("MSRSTE_NM").toString();

            String pm10 = rowData.get("PM10").toString();
            String pm25 = rowData.get("PM25").toString();
            String o3 = rowData.get("O3").toString();

            String no2 = rowData.get("NO2").toString();
            String co = rowData.get("CO").toString();
            String so2 = rowData.get("SO2").toString();

            String idex_nm = rowData.get("IDEX_NM").toString();
            String idex_mvl = rowData.get("IDEX_MVL").toString();
            String arplt_main = rowData.get("ARPLT_MAIN").toString();

            result.put("MSRDT", msrdt);
            result.put("MSRRGN_NM", msrrgn_nm);
            result.put("MSRSTE_NM", msrste_nm);

            result.put("PM10", pm10);
            result.put("PM25", pm25);
            result.put("O3", o3);

            result.put("NO2", no2);
            result.put("CO", co);
            result.put("SO2", so2);

            result.put("IDEX_NM", idex_nm);
            result.put("IDEX_MVL", idex_mvl);
            result.put("ARPLT_MAIN", arplt_main);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}

