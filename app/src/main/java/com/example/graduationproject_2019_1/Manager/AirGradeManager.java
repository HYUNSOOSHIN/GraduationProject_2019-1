package com.example.graduationproject_2019_1.Manager;

import android.util.Log;

import com.example.graduationproject_2019_1.R;

public class AirGradeManager {

    private AirGradeManager() {
    }

    public static final String GRADE_01 = "외출을 지향 합니다!";
    public static final String GRADE_02 = "신선한 공기 많이 마시세요~";
    public static final String GRADE_03 = "환기 한 번 하세요~";
    public static final String GRADE_04 = "마스크는 필요없어요!";
    public static final String GRADE_05 = "마스크 꼭 챙기세요!";
    public static final String GRADE_06 = "외출은 삼가하세요!";
    public static final String GRADE_07 = "미세먼지 폭탄이에요!";
    public static final String GRADE_08 = "최악!! 집 밖은 위험해요!";

    public static final String GRADE_01_SHORT = "최고";
    public static final String GRADE_02_SHORT = "좋음";
    public static final String GRADE_03_SHORT = "양호";
    public static final String GRADE_04_SHORT = "보통";

    public static final String GRADE_05_SHORT = "나쁨";
    public static final String GRADE_06_SHORT = "상당히 나쁨";
    public static final String GRADE_07_SHORT = "매우 나쁨";
    public static final String GRADE_08_SHORT = "최악";

    public static AirGradeWrapper get(String type, String data) {
        AirGradeWrapper result;
        switch (type) {
            case "PM10":
                result = getPM10(data);
                break;
            case "PM25":
                result = getPM25(data);
                break;
            case "O3":
                result = getO3(data);
                break;
            case "NO2":
                result = getNO2(data);
                break;
            case "CO":
                result = getCO(data);
                break;
            case "SO2":
                result = getSO2(data);
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

    public static int getBackgroundColorIdWithGrade(int grade, boolean isThick) {
        int id;
        if (isThick) {
            switch (grade) {
                case 1:
                    id = R.color.grade_01_thick;
                    break;

                case 2:
                    id = R.color.grade_02_thick;
                    break;

                case 3:
                    id = R.color.grade_03_thick;
                    break;

                case 4:
                    id = R.color.grade_04_thick;
                    break;

                case 5:
                    id = R.color.grade_05_thick;
                    break;

                case 6:
                    id = R.color.grade_06_thick;
                    break;

                case 7:
                    id = R.color.grade_07_thick;
                    break;

                case 8:
                    id = R.color.grade_08_thick;
                    break;

                default:
                    id = R.color.white;
            }
        } else {
            switch (grade) {
                case 1:
                    id = R.color.grade_01;
                    break;

                case 2:
                    id = R.color.grade_02;
                    break;

                case 3:
                    id = R.color.grade_03;
                    break;

                case 4:
                    id = R.color.grade_04;
                    break;

                case 5:
                    id = R.color.grade_05;
                    break;

                case 6:
                    id = R.color.grade_06;
                    break;

                case 7:
                    id = R.color.grade_07;
                    break;

                case 8:
                    id = R.color.grade_08;
                    break;

                default:
                    id = R.color.white;
            }
        }
        return id;
    }

    public static String getGradeMessageWithGrade(int grade) {
        String result;
        switch (grade) {
            case 1:
                result = GRADE_01;
                break;

            case 2:
                result = GRADE_02;
                break;

            case 3:
                result = GRADE_03;
                break;

            case 4:
                result = GRADE_04;
                break;

            case 5:
                result = GRADE_05;
                break;

            case 6:
                result = GRADE_06;
                break;

            case 7:
                result = GRADE_07;
                break;

            case 8:
                result = GRADE_08;
                break;

            default:
                result = "미세먼지 정보를 불러오는 중 오류가 발생하였습니다.";
        }

        return result;
    }

    public static String getGradeShortMessageWithGrade(int grade) {
        String result;
        switch (grade) {
            case 1:
                result = GRADE_01_SHORT;
                break;

            case 2:
                result = GRADE_02_SHORT;
                break;

            case 3:
                result = GRADE_03_SHORT;
                break;

            case 4:
                result = GRADE_04_SHORT;
                break;

            case 5:
                result = GRADE_05_SHORT;
                break;

            case 6:
                result = GRADE_06_SHORT;
                break;

            case 7:
                result = GRADE_07_SHORT;
                break;

            case 8:
                result = GRADE_08_SHORT;
                break;

            default:
                result = "미세먼지 정보를 불러오는 중 오류가 발생하였습니다.";
        }

        return result;
    }

    public static int getGradeImageIdWithGrade(int grade) {
        int result;
        switch (grade) {
            case 1:
                result = R.drawable.face_01;
                break;

            case 2:
                result = R.drawable.face_02;
                break;

            case 3:
                result = R.drawable.face_03;
                break;

            case 4:
                result = R.drawable.face_04;
                break;

            case 5:
                result = R.drawable.face_05;
                break;

            case 6:
                result = R.drawable.face_06;
                break;

            case 7:
                result = R.drawable.face_07;
                break;

            case 8:
                result = R.drawable.face_08;
                break;

            default:
                result = R.drawable.face_01;
        }

        return result;
    }

    public static AirGradeWrapper getPM10(String pm10) {
        int data = Integer.parseInt(pm10);
        AirGradeWrapper result;

        if (0 <= data && data <= 15) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (16 <= data && data <= 30) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (31 <= data && data <= 40) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (41 <= data && data <= 50) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (51 <= data && data <= 75) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (76 <= data && data <= 100) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (101 <= data && data <= 150) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (151 <= data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }
        return result;
    }

    public static AirGradeWrapper getPM25(String pm25) {
        int data = Integer.parseInt(pm25);
        AirGradeWrapper result;

        if (0 <= data && data <= 8) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (9 <= data && data <= 15) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (16 <= data && data <= 20) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (21 <= data && data <= 25) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (26 <= data && data <= 37) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (38 <= data && data <= 50) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (51 <= data && data <= 75) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (76 <= data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }

    public static AirGradeWrapper getO3(String o3) {
        float data = Float.parseFloat(o3);
        AirGradeWrapper result;

        if (0 <= data && data <= 0.02) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (0.02 < data && data <= 0.03) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (0.03 < data && data <= 0.06) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (0.06 < data && data <= 0.09) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (0.09 < data && data <= 0.12) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (0.12 < data && data <= 0.15) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (0.15 < data && data <= 0.38) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (0.38 < data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }

    public static AirGradeWrapper getNO2(String no2) {
        float data = Float.parseFloat(no2);
        AirGradeWrapper result;

        if (0 <= data && data <= 0.02) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (0.02 < data && data <= 0.03) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (0.03 < data && data <= 0.05) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (0.05 < data && data <= 0.06) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (0.06 < data && data <= 0.13) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (0.13 < data && data <= 0.20) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (0.20 < data && data <= 1.10) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (1.10 < data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }

    public static AirGradeWrapper getCO(String co) {
        float data = Float.parseFloat(co);
        AirGradeWrapper result;

        if (0 <= data && data <= 1) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (1.0 < data && data <= 2.0) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (2.0 < data && data <= 5.5) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (5.5 < data && data <= 9.0) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (9.0 < data && data <= 12.0) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (12.0 < data && data <= 15.0) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (15.0 < data && data <= 32.0) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (32.0 < data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }

    public static AirGradeWrapper getSO2(String so2) {
        float data = Float.parseFloat(so2);
        AirGradeWrapper result;

        if (0 < data && data <= 0.01) {
            result = AirGradeWrapper.create(R.drawable.face_01, "최고", "최고message");
        } else if (0.01 < data && data <= 0.02) {
            result = AirGradeWrapper.create(R.drawable.face_02, "좋음", "좋음message");
        } else if (0.02 < data && data <= 0.04) {
            result = AirGradeWrapper.create(R.drawable.face_03, "양호", "양호message");
        } else if (0.04 < data && data <= 0.05) {
            result = AirGradeWrapper.create(R.drawable.face_04, "보통", "보통message");
        } else if (0.05 < data && data <= 0.10) {
            result = AirGradeWrapper.create(R.drawable.face_05, "나쁨", "나쁨message");
        } else if (0.10 < data && data <= 0.15) {
            result = AirGradeWrapper.create(R.drawable.face_06, "상당히 나쁨", "상당히 나쁨message");
        } else if (0.15 < data && data <= 0.60) {
            result = AirGradeWrapper.create(R.drawable.face_07, "매우 나쁨", "매우 나쁨message");
        } else if (0.60 < data) {
            result = AirGradeWrapper.create(R.drawable.face_08, "최악", "최악message");
        } else {
            result = AirGradeWrapper.create(R.drawable.face_04, "오류", "불러오는 중 오류 발생");
            Log.e("test", "input data is wrong.");
        }

        return result;
    }
}
