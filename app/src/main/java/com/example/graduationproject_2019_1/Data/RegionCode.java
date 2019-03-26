package com.example.graduationproject_2019_1.Data;

public class RegionCode {
    public static final int[] RCode = new int[25];

    Region region[] = { //동이름,코드값 객체 배열
            new Region("삼선동",123456),
            new Region("성북동", 159123)
    };

    public int findCode(String dong){ //동이름으로 코드값 찾는 함수
        int cnt = 0;
        for(int i=0; i<region.length; i++) {
            if (region[i].dong == dong) {
                cnt = i;
            }
            else{
                return 400; //에러코드
            }
        }
        return region[cnt].code;
    }

    public RegionCode(){

        RCode[0] = 1168000000;// 강남구
        RCode[1] = 1174000000;// 강동구
        RCode[2] = 1130500000; // 강북구
        RCode[3] = 1150000000; // 강서구
        RCode[4] = 1162000000; // 관악구
        RCode[5] = 1121500000; //광진구
        RCode[6] = 1153000000; // 구로구
        RCode[7] = 1154500000; // 금천구
        RCode[8] = 1135000000; //노원구
        RCode[9] = 1132000000; // 도봉구
        RCode[10] = 1123000000; // 동대문구
        RCode[11] = 1159000000; // 동작구
        RCode[12] = 1144000000; // 마포구
        RCode[13] = 1141000000; // 서대문구
        RCode[14] = 1165000000; // 서초구
        RCode[15] = 1120000000; // 성동구
        RCode[16] = 1129055500; // 성북구
        RCode[17] = 1171000000; // 송파구
        RCode[18] = 1147000000; // 양천구
        RCode[19] = 1156000000; // 영등포구
        RCode[20] = 1117000000; // 용산구
        RCode[21] = 1138000000; // 은평구
        RCode[22] = 1111000000; // 종로구
        RCode[23] = 1114000000; // 중구
        RCode[24] = 1126000000; // 중랑구
    }

    /* // 순서
    public int gangnamgu = 1168000000; // 강남구
    public int gangdonggu = 1174000000; // 강동구
    public int ganbuckgu = 1130500000; // 강북구
    public int ganseogu = 1150000000; // 강서구
    public int guanakgu = 1162000000; // 관악구
    public int guangjingu = 1121500000; //광진구
    public int gurogu = 1153000000; // 구로구
    public int gumcheongu = 1154500000; // 금천구
    public int nowongu = 1135000000; //노원구
    public int dobonggu = 1132000000; // 도봉구
    public int dongdaemungu = 1123000000; // 동대문구
    public int dongjackgu = 1159000000; // 동작구
    public int mapogu = 1144000000; // 마포구
    public int seodeamungu = 1141000000; // 서대문구
    public int seochogu = 1165000000; // 서초구
    public int seongdonggu = 1120000000; // 성동구
    public int seongbuckgu = 1129055500; // 성북구
    public int seongpagu = 1171000000; // 송파구
    public int yangcheongu = 1147000000; // 양천구
    public int yeongdongpogu = 1156000000; // 영등포구
    public int yongsangu = 1117000000; // 용산구
    public int enpeonggu = 1138000000; // 은평구
    public int jongrogu = 1111000000; // 종로구
    public int junggu = 1114000000; // 중구
    public int jungranggu = 1126000000; // 중랑구
    */

    public class Region{
        private String dong;
        private int code;

        public Region(String dong, int code){
            this.dong=dong;
            this.code=code;
        }
    }
}

