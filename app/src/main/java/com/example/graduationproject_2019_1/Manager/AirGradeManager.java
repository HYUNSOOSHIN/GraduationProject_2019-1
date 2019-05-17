package com.example.graduationproject_2019_1.Manager;

import android.graphics.Color;
import android.util.Log;

import com.example.graduationproject_2019_1.R;

public class AirGradeManager {

    private AirGradeManager() {
    }

    public static final String SUNNY = "맑음";
    public static final String SUN_CLOUD= "몰라";
    public static final String MANY_CLOUD = "구름 많음";
    public static final String LITTLE_CLOUD = "구름 조금";
    public static final String RAIN = "비";
    public static final String SNOW = "눈";

    public static final int GRADE_1 = Color.parseColor("#d9feff");
    public static final int GRADE_2 = Color.parseColor("#86edf0");
    public static final int GRADE_3 = Color.parseColor("#67caff");
    public static final int GRADE_4 = Color.parseColor("#67adff");
    public static final int GRADE_5 = Color.parseColor("#ffc424");
    public static final int GRADE_6 = Color.parseColor("#ff8914");
    public static final int GRADE_7 = Color.parseColor("#db5454");
    public static final int GRADE_8 = Color.parseColor("#383838");




    public static AirGradeWrapper get(String type, String data) {
        AirGradeWrapper result;
        switch (type) {
            case "PM10":
                result = getPM10(data);
                break;
            case "PM25":
                result = getPM25(data);
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    public static int getGradeWithWholeValue(int value) {
        int result;
        if (value > 00 && value <= 30) {
            result = 1;
        } else if (value > 30 && value <= 60) {
            result = 2;
        } else if (value > 60 && value <= 90) {
            result = 3;
        } else if (value > 90 && value <= 110) {
            result = 4;
        } else if (value > 110 && value <= 130) {
            result = 5;
        } else if (value > 130 && value <= 150) {
            result = 6;
        } else if (value > 150 && value <= 170) {
            result = 7;
        } else {
            result = 8;
        }
        return result;
    }

    public static int getTextColorIdWithGrade(int grade) {
        int id;
        switch (grade) {
            case 1:
                id = GRADE_1;
                break;

            case 2:
                id = GRADE_2;
                break;

            case 3:
                id = GRADE_3;
                break;

            case 4:
                id = GRADE_4;
                break;

            case 5:
                id = GRADE_5;
                break;

            case 6:
                id = GRADE_6;
                break;

            case 7:
                id = GRADE_7;
                break;

            case 8:
                id = GRADE_8;
                break;

            default:
                id = Color.parseColor("#000000");
        }
        return id;
    }

    public static int getMarkImage(int gradeValue) {
        int result;

        if(gradeValue>=0 && gradeValue<30) result = R.drawable.good;
        else if(gradeValue>=30 && gradeValue <80) result = R.drawable.normal;
        else if(gradeValue>=80 && gradeValue <150) result = R.drawable.bad;
        else if(gradeValue>=150) result =  R.drawable.verybad;
        else result =  R.drawable.worst;

        return result;
    }

    public static int getGradeImageIdWithGrade(int grade) {
        int result;
        switch (grade) {
            case 1:
                result = R.drawable.finedust_1;
                break;

            case 2:
                result = R.drawable.finedust_2;
                break;

            case 3:
                result = R.drawable.finedust_3;
                break;

            case 4:
                result = R.drawable.finedust_4;
                break;

            case 5:
                result = R.drawable.finedust_5;
                break;

            case 6:
                result = R.drawable.finedust_6;
                break;

            case 7:
                result = R.drawable.finedust_7;
                break;

            case 8:
                result = R.drawable.finedust_8;
                break;

            default:
                result = R.drawable.finedust_8;
        }

        return result;
    }

    public static int getWeatherImageId(String weathertext) {
        int result;
        switch (weathertext) {
            case  SUNNY:
                result = R.drawable.sun;
                break;

            case SUN_CLOUD:
                result = R.drawable.sun_cloud;
                break;

            case MANY_CLOUD:
                result = R.drawable.many_cloud;
                break;

            case LITTLE_CLOUD:
                result = R.drawable.little_cloud;
                break;

            case RAIN:
                result = R.drawable.rain;
                break;

            case SNOW:
                result = R.drawable.snow;
                break;

            default:
                result = R.drawable.finedust_8;
        }

        return result;
    }

    public static AirGradeWrapper getPM10(String pm10) {
        int data = Integer.parseInt(pm10);
        AirGradeWrapper result;

        if (0 <= data && data <= 15) {
            result = AirGradeWrapper.create(R.drawable.finedust_1, "최고", "최고message");
        } else if (16 <= data && data <= 30) {
            result = AirGradeWrapper.create(R.drawable.finedust_2, "좋음", "좋음message");
        } else if (31 <= data && data <= 40) {
            result = AirGradeWrapper.create(R.drawable.finedust_3, "양호", "양호message");
        } else if (41 <= data && data <= 50) {
            result = AirGradeWrapper.create(R.drawable.finedust_4, "보통", "보통message");
        } else if (51 <= data && data <= 75) {
            result = AirGradeWrapper.create(R.drawable.finedust_5, "나쁨", "나쁨message");
        } else if (76 <= data && data <= 100) {
            result = AirGradeWrapper.create(R.drawable.finedust_6, "상당히 나쁨", "상당히 나쁨message");
        } else if (101 <= data && data <= 150) {
            result = AirGradeWrapper.create(R.drawable.finedust_7, "매우 나쁨", "매우 나쁨message");
        } else if (151 <= data) {
            result = AirGradeWrapper.create(R.drawable.finedust_8, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.finedust_8, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }
        return result;
    }

    public static AirGradeWrapper getPM25(String pm25) {
        int data = Integer.parseInt(pm25);
        AirGradeWrapper result;

        if (0 <= data && data <= 8) {
            result = AirGradeWrapper.create(R.drawable.finedust_1, "최고", "최고message");
        } else if (9 <= data && data <= 15) {
            result = AirGradeWrapper.create(R.drawable.finedust_2, "좋음", "좋음message");
        } else if (16 <= data && data <= 20) {
            result = AirGradeWrapper.create(R.drawable.finedust_3, "양호", "양호message");
        } else if (21 <= data && data <= 25) {
            result = AirGradeWrapper.create(R.drawable.finedust_4, "보통", "보통message");
        } else if (26 <= data && data <= 37) {
            result = AirGradeWrapper.create(R.drawable.finedust_5, "나쁨", "나쁨message");
        } else if (38 <= data && data <= 50) {
            result = AirGradeWrapper.create(R.drawable.finedust_6, "상당히 나쁨", "상당히 나쁨message");
        } else if (51 <= data && data <= 75) {
            result = AirGradeWrapper.create(R.drawable.finedust_7, "매우 나쁨", "매우 나쁨message");
        } else if (76 <= data) {
            result = AirGradeWrapper.create(R.drawable.finedust_8, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.finedust_8, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }
}
