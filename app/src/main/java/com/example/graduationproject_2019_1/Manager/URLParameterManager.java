package com.example.graduationproject_2019_1.Manager;

import android.content.ContentValues;

public class URLParameterManager {

    private static String KEY = "61446d4e496a686932366773527076";
    private static String TYPE = "xml";
    private static String SERVICE = "RealtimeCityAir";
    private static String START_INDEX = "1";
    private static String END_INDEX = "10";

    public static ContentValues getRequestString(String msrrgn, String msrste) {

        ContentValues values = new ContentValues();

        values.put("msrrgn", msrrgn);
        values.put("msrste", msrste);

        return  values;
    }

}
