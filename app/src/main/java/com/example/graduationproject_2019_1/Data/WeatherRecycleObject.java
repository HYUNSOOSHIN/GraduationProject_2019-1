package com.example.graduationproject_2019_1.Data;

public class WeatherRecycleObject {

    public String time;
    public int miseimage;
    public String misevalue;
    public int tempimage;
    public String tempvalue;
    public int rainimage;
    public String rainvalue;


    public WeatherRecycleObject(String time, int miseimage, String misevalue, int tempimage, String tempvalue, int rainimage, String rainvalue){
        this.time = time;
        this.miseimage = miseimage;
        this.misevalue = misevalue;
        this.tempimage = tempimage;
        this.tempvalue = tempvalue;
        this.rainimage = rainimage;
        this.rainvalue = rainvalue;
    }
}
