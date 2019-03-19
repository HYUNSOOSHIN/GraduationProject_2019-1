package com.example.graduationproject_2019_1.Manager;

public class CityLocationManager {

    public static String getNMbyCityName(String cityName) {
        String result;
        switch (cityName) {
            case "종로구":
            case "중구":
            case "용산구":
                result = "도심권";
                break;

            case "광진구":
            case "성동구":
            case "동대문구":
            case "중랑구":
            case "노원구":
            case "도봉구":
            case "강북구":
            case "성북구":
                result = "동북권";
                break;

            case "서초구":
            case "강남구":
            case "송파구":
            case "강동구":
                result = "동남권";
                break;

            case "은평구":
            case "서대문구":
            case "마포구":
                result = "서북권";
                break;

            case "강서구":
            case "양천구":
            case "구로구":
            case "영등포구":
            case "금천구":
            case "관악구":
            case "동작구":
                result = "서남권";
                break;

            default:
                result = "";
        }

        return result;
    }
}


