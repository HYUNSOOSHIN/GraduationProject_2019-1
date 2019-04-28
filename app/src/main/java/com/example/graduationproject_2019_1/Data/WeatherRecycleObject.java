package com.example.graduationproject_2019_1.Data;

public class WeatherRecycleObject {

    public String time;
    public int tempimage;
    public String tempvalue;
    //    public int wfKorimage;
//    public String wfKorvalue;
    public int popimage;
    public String popvalue;
    public int rehimage;
    public String rehvalue;


    //    public WeatherRecycleObject(String time, int tempimage, String tempvalue, int wfKorimage, String wfKorvalue, int popimage, String popvalue, int rehimage, String rehvalue){
    public WeatherRecycleObject(String time, int tempimage, String tempvalue, int popimage, String popvalue, int rehimage, String rehvalue) {
        this.time = time;
        this.tempimage = tempimage;
        this.tempvalue = tempvalue;
//        this.wfKorimage = wfKorimage;
//        this.wfKorvalue = wfKorvalue;
        this.popimage = popimage;
        this.popvalue = popvalue;
        this.rehimage = rehimage;
        this.rehvalue = rehvalue;
    }
}
