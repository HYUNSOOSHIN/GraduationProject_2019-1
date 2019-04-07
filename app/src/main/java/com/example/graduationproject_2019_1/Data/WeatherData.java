package com.example.graduationproject_2019_1.Data;

public class WeatherData {
    //public String dong; // 어디 동인지?
    public static final String[] WData = new String[6];
    public String hour;
    public String day;
    public String temp;
    public String wfKor;
    public String pop;
    public String reh;

    public WeatherData(){
        WData[0] = "hour";
        WData[1] = "day";
        WData[2] = "temp";
        WData[3] = "wfKor";
        WData[4] = "pop";
        WData[5] = "reh";
    }

    public WeatherData(String hour, String day, String temp, String wfKor, String pop, String reh){
        this.hour = hour;
        this.day = day;
        this.temp = temp;
        this.wfKor = wfKor;
        this.pop = pop;
        this.reh = reh;
    }

    /*
    public String hour; // 받아온 시간
    public String day; // 받아온 날짜, 0: 오늘, 1: 내일, 2: 모레
    public String temp;  // 온도
    public String wfKor; // ex 맑음
    public String pop; // 강수확률
    public String reh; // 습도
    */
}
