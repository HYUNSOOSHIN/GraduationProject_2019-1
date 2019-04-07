package com.example.graduationproject_2019_1.Data;

public class Region{
    public String gu;
    public String dong;
    public int code;
    public String getGu() {
        return gu;
    }
    public void setGu(String gu) {
        this.gu = gu;
    }
    public String getDong() {
        return dong;
    }
    public void setDong(String dong) {
        this.dong = dong;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public Region(String gu, String dong, int code){
        this.gu = gu;
        this.dong = dong;
        this.code = code;
    }
}