package com.example.graduationproject_2019_1.Manager;

import android.graphics.Color;
import android.util.Log;

import com.example.graduationproject_2019_1.R;

public class AirGradeManager {

    private AirGradeManager() {
    }

    public static final String SUNNY = "맑음";
    public static final String SUN_CLOUD= "흐림";
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


    //등급
    public static int getGradeWithWholeValue(int value) {
        int result;

        if(value==-99){ // 점검중
            result = 0;
        } else if (value > 00 && value <= 30) {
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

    //통합대기지수텍스트
    public static String getGradetextWithWholeValue(int value) {
        String result;

        if(value==-99){ // 점검중
            result = "점검중";
        } else if (value > 00 && value <= 30) {
            result = "최고";
        } else if (value > 30 && value <= 60) {
            result = "좋음";
        } else if (value > 60 && value <= 90) {
            result = "양호";
        } else if (value > 90 && value <= 110) {
            result = "보통";
        } else if (value > 110 && value <= 130) {
            result = "나쁨";
        } else if (value > 130 && value <= 150) {
            result = "상당히 나쁨";
        } else if (value > 150 && value <= 170) {
            result = "매우 나쁨";
       } else {
            result = "최악";
        }
        return result;
    }

    //통합대기지수 텍스트색
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

    //미세먼지 텍스트색
    public static int getPM10textColor(String pm10) {
        int data = Integer.parseInt(pm10);
        int result;

        if (0 < data && data <= 15) {
            result = GRADE_1;
        } else if (16 <= data && data <= 30) {
            result = GRADE_2;
        } else if (31 <= data && data <= 40) {
            result = GRADE_3;
        } else if (41 <= data && data <= 50) {
            result = GRADE_4;
        } else if (51 <= data && data <= 75) {
            result = GRADE_5;
        } else if (76 <= data && data <= 100) {
            result = GRADE_6;
        } else if (101 <= data && data <= 150) {
            result = GRADE_7;
        } else if (151 <= data) {
            result = GRADE_8;
        } else {
            result = GRADE_8;
            Log.e("test", "미세먼지 텍스트색 에러");
        }
        return result;
    }

    //초미세먼지 텍스트색
    public static int getPM25textColor(String pm25) {
        int data = Integer.parseInt(pm25);
        int result;

        if (0 < data && data <= 8) {
            result = GRADE_1;
        } else if (9 <= data && data <= 15) {
            result = GRADE_2;
        } else if (16 <= data && data <= 20) {
            result = GRADE_3;
        } else if (21 <= data && data <= 25) {
            result = GRADE_4;
        } else if (26 <= data && data <= 37) {
            result = GRADE_5;
        } else if (38 <= data && data <= 50) {
            result = GRADE_6;
        } else if (51 <= data && data <= 75) {
            result = GRADE_7;
        } else if (76 <= data) {
            result = GRADE_8;
        } else {
            result = GRADE_8;
            Log.e("test", "초미세먼지 텍스트색 에러");
        }

        return result;
    }

    public static int getWidgetImage(int grade) {
        int result;

        switch (grade) {
            case 1:
                result = R.drawable.widget_1;
                break;

            case 2:
                result = R.drawable.widget_2;
                break;

            case 3:
                result = R.drawable.widget_3;
                break;

            case 4:
                result = R.drawable.widget_4;
                break;

            case 5:
                result = R.drawable.widget_5;
                break;

            case 6:
                result = R.drawable.widget_6;
                break;

            case 7:
                result = R.drawable.widget_7;
                break;

            case 8:
                result = R.drawable.widget_8;
                break;

            default:
                result = R.drawable.widget_8;
        }

        return result;
    }

    public static int getMarkImage(int gradeValue) {
        int result;

        if(gradeValue>0 && gradeValue<30) result = R.drawable.good;
        else if(gradeValue>=30 && gradeValue <80) result = R.drawable.normal;
        else if(gradeValue>=80 && gradeValue <150) result = R.drawable.bad;
        else if(gradeValue>=150) result =  R.drawable.verybad;
        else result =  R.drawable.worst;

        return result;
    }

    public static int getGradeImageIdWithGrade(int grade) {
        int result;

        Log.e("test", grade+"");

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

        if (0 < data && data <= 15) {
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

        if (0 < data && data <= 8) {
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
