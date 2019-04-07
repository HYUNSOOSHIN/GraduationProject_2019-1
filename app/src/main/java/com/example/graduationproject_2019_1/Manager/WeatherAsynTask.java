package com.example.graduationproject_2019_1.Manager;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.graduationproject_2019_1.Data.Region;
import com.example.graduationproject_2019_1.Data.RegionCode;
import com.example.graduationproject_2019_1.Data.WeatherData;
import java.io.IOException;

//"http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0]"
//new WeatherAsynTask(weather).execute("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129057500", "data[seq=0]");


public class WeatherAsynTask extends AsyncTask {
    public String gu;
    public String dong;
    public int region_code;
    public int region_index;

    Region region[] = new Region[449];
    Document[] document = new Document[449];
    Elements element[][] = new Elements[449][145];
    WeatherData weatherData = new WeatherData();


    public WeatherAsynTask(String GU, String DONG) {
        this.gu = GU;
        this.dong = DONG;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String[] doInBackground(Object[] objects) {
        String[] result = new String[145];
        //array_str = R_Code.find_Code_Index(dong).split(" ");
        //RegionCode R_Code = new RegionCode(region);

        region[0] = new Region("종로구", "", 1111000000);
        region[1] = new Region("종로구", "청운효자동", 1111051500);
        region[2] = new Region("종로구", "사직동", 1111053000);
        region[3] = new Region("종로구", "삼청동", 1111054000);
        region[4] = new Region("종로구", "부암동", 1111055000);
        region[5] = new Region("종로구", "평창동", 1111056000);
        region[6] = new Region("종로구", "무악동", 1111057000);
        region[7] = new Region("종로구", "교남동", 1111058000);
        region[8] = new Region("종로구", "가회동", 1111060000);
        region[9] = new Region("종로구", "종로1.2.3.4가동", 1111061500);
        region[10] = new Region("종로구", "종로5.6가동", 1111063000);
        region[11] = new Region("종로구", "이화동", 1111064000);
        region[12] = new Region("종로구", "혜화동", 1111065000);
        region[13] = new Region("종로구", "창신제1동", 1111067000);
        region[14] = new Region("종로구", "창신제2동", 1111068000);
        region[15] = new Region("종로구", "창신제3동", 1111069000);
        region[16] = new Region("종로구", "숭인제1동", 1111070000);
        region[17] = new Region("종로구", "숭인제2동", 1111071000);
        region[18] = new Region("중구", "", 1114000000);
        region[19] = new Region("중구", "소공동", 1114052000);
        region[20] = new Region("중구", "회현동", 1114054000);
        region[21] = new Region("중구", "명동", 1114055000);
        region[22] = new Region("중구", "필동", 1114057000);
        region[23] = new Region("중구", "장충동", 1114058000);
        region[24] = new Region("중구", "광희동", 1114059000);
        region[25] = new Region("중구", "을지로동", 1114060500);
        region[26] = new Region("중구", "신당동", 1114061500);
        region[27] = new Region("중구", "다산동", 1114062500);
        region[28] = new Region("중구", "약수동", 1114063500);
        region[29] = new Region("중구", "청구동", 1114064500);
        region[30] = new Region("중구", "신당제5동", 1114065000);
        region[31] = new Region("중구", "동화동", 1114066500);
        region[32] = new Region("중구", "황학동", 1114067000);
        region[33] = new Region("중구", "중림동", 1114068000);
        region[34] = new Region("용산구", "", 1117000000);
        region[35] = new Region("용산구", "후암동", 1117051000);
        region[36] = new Region("용산구", "용산2가동", 1117052000);
        region[37] = new Region("용산구", "남영동", 1117053000);
        region[38] = new Region("용산구", "청파동", 1117055500);
        region[39] = new Region("용산구", "원효로제1동", 1117056000);
        region[40] = new Region("용산구", "원효로제2동", 1117057000);
        region[41] = new Region("용산구", "효창동", 1117058000);
        region[42] = new Region("용산구", "용문동", 1117059000);
        region[43] = new Region("용산구", "한강로동", 1117062500);
        region[44] = new Region("용산구", "이촌제1동", 1117063000);
        region[45] = new Region("용산구", "이촌제2동", 1117064000);
        region[46] = new Region("용산구", "이태원제1동", 1117065000);
        region[47] = new Region("용산구", "이태원제2동", 1117066000);
        region[48] = new Region("용산구", "한남동", 1117068500);
        region[49] = new Region("용산구", "서빙고동", 1117069000);
        region[50] = new Region("용산구", "보광동", 1117070000);
        region[51] = new Region("성동구", "", 1120000000);
        region[52] = new Region("성동구", "왕십리제2동", 1120052000);
        region[53] = new Region("성동구", "왕십리도선동", 1120053500);
        region[54] = new Region("성동구", "마장동", 1120054000);
        region[55] = new Region("성동구", "사근동", 1120055000);
        region[56] = new Region("성동구", "행당제1동", 1120056000);
        region[57] = new Region("성동구", "행당제2동", 1120057000);
        region[58] = new Region("성동구", "응봉동", 1120058000);
        region[59] = new Region("성동구", "금호1가동", 1120059000);
        region[60] = new Region("성동구", "금호2.3가동", 1120061500);
        region[61] = new Region("성동구", "금호4가동", 1120062000);
        region[62] = new Region("성동구", "옥수동", 1120064500);
        region[63] = new Region("성동구", "성수1가제1동", 1120065000);
        region[64] = new Region("성동구", "성수1가제2동", 1120066000);
        region[65] = new Region("성동구", "성수2가제1동", 1120067000);
        region[66] = new Region("성동구", "성수2가제3동", 1120069000);
        region[67] = new Region("성동구", "송정동", 1120072000);
        region[68] = new Region("성동구", "용답동", 1120079000);
        region[69] = new Region("광진구", "", 1121500000);
        region[70] = new Region("광진구", "화양동", 1121571000);
        region[71] = new Region("광진구", "군자동", 1121573000);
        region[72] = new Region("광진구", "중곡제1동", 1121574000);
        region[73] = new Region("광진구", "중곡제2동", 1121575000);
        region[74] = new Region("광진구", "중곡제3동", 1121576000);
        region[75] = new Region("광진구", "중곡제4동", 1121577000);
        region[76] = new Region("광진구", "능동", 1121578000);
        region[77] = new Region("광진구", "광장동", 1121581000);
        region[78] = new Region("광진구", "자양제1동", 1121582000);
        region[79] = new Region("광진구", "자양제2동", 1121583000);
        region[80] = new Region("광진구", "자양제3동", 1121584000);
        region[81] = new Region("광진구", "자양제4동", 1121584700);
        region[82] = new Region("광진구", "구의제1동", 1121585000);
        region[83] = new Region("광진구", "구의제2동", 1121586000);
        region[84] = new Region("광진구", "구의제3동", 1121587000);
        region[85] = new Region("동대문구", "", 1123000000);
        region[86] = new Region("동대문구", "용신동", 1123053600);
        region[87] = new Region("동대문구", "제기동", 1123054500);
        region[88] = new Region("동대문구", "전농제1동", 1123056000);
        region[89] = new Region("동대문구", "전농제2동", 1123057000);
        region[90] = new Region("동대문구", "답십리제1동", 1123060000);
        region[91] = new Region("동대문구", "답십리제2동", 1123061000);
        region[92] = new Region("동대문구", "장안제1동", 1123065000);
        region[93] = new Region("동대문구", "장안제2동", 1123066000);
        region[94] = new Region("동대문구", "청량리동", 1123070500);
        region[95] = new Region("동대문구", "회기동", 1123071000);
        region[96] = new Region("동대문구", "휘경제1동", 1123072000);
        region[97] = new Region("동대문구", "휘경제2동", 1123073000);
        region[98] = new Region("동대문구", "이문제1동", 1123074000);
        region[99] = new Region("동대문구", "이문제2동", 1123075000);
        region[100] = new Region("중랑구", "", 1126000000);
        region[101] = new Region("중랑구", "면목제2동", 1126052000);
        region[102] = new Region("중랑구", "면목제4동", 1126054000);
        region[103] = new Region("중랑구", "면목제5동", 1126055000);
        region[104] = new Region("중랑구", "면목본동", 1126056500);
        region[105] = new Region("중랑구", "면목제7동", 1126057000);
        region[106] = new Region("중랑구", "면목제3.8동", 1126057500);
        region[107] = new Region("중랑구", "상봉제1동", 1126058000);
        region[108] = new Region("중랑구", "상봉제2동", 1126059000);
        region[109] = new Region("중랑구", "중화제1동", 1126060000);
        region[110] = new Region("중랑구", "중화제2동", 1126061000);
        region[111] = new Region("중랑구", "묵제1동", 1126062000);
        region[112] = new Region("중랑구", "묵제2동", 1126063000);
        region[113] = new Region("중랑구", "망우본동", 1126065500);
        region[114] = new Region("중랑구", "망우제3동", 1126066000);
        region[115] = new Region("중랑구", "신내1동", 1126068000);
        region[116] = new Region("중랑구", "신내2동", 1126069000);
        region[117] = new Region("성북구", "", 1129000000);
        region[118] = new Region("성북구", "성북동", 1129052500);
        region[119] = new Region("성북구", "삼선동2가", 1129055500);
        region[120] = new Region("성북구", "동선동", 1129057500);
        region[121] = new Region("성북구", "돈암제1동", 1129058000);
        region[122] = new Region("성북구", "돈암제2동", 1129059000);
        region[123] = new Region("성북구", "안암동", 1129060000);
        region[124] = new Region("성북구", "보문동", 1129061000);
        region[125] = new Region("성북구", "정릉제1동", 1129062000);
        region[126] = new Region("성북구", "정릉제2동", 1129063000);
        region[127] = new Region("성북구", "정릉제3동", 1129064000);
        region[128] = new Region("성북구", "정릉제4동", 1129065000);
        region[129] = new Region("성북구", "길음제1동", 1129066000);
        region[130] = new Region("성북구", "길음제2동", 1129068500);
        region[131] = new Region("성북구", "종암동", 1129070500);
        region[132] = new Region("성북구", "월곡제1동", 1129071500);
        region[133] = new Region("성북구", "월곡제2동", 1129072500);
        region[134] = new Region("성북구", "장위제1동", 1129076000);
        region[135] = new Region("성북구", "장위제2동", 1129077000);
        region[136] = new Region("성북구", "장위제3동", 1129078000);
        region[137] = new Region("성북구", "석관동", 1129081000);
        region[138] = new Region("강북구", "", 1130500000);
        region[139] = new Region("강북구", "삼양동", 1130553400);
        region[140] = new Region("강북구", "미아동", 1130553500);
        region[141] = new Region("강북구", "송중동", 1130554500);
        region[142] = new Region("강북구", "송천동", 1130555500);
        region[143] = new Region("강북구", "삼각산동", 1130557500);
        region[144] = new Region("강북구", "번제1동", 1130559000);
        region[145] = new Region("강북구", "번제2동", 1130560000);
        region[146] = new Region("강북구", "번제3동", 1130560600);
        region[147] = new Region("강북구", "수유제1동", 1130561000);
        region[148] = new Region("강북구", "수유제2동", 1130562000);
        region[149] = new Region("강북구", "수유제3동", 1130563000);
        region[150] = new Region("강북구", "우이동", 1130564500);
        region[151] = new Region("강북구", "인수동", 1130566000);
        region[152] = new Region("도봉구", "", 1132000000);
        region[153] = new Region("도봉구", "창제1동", 1132051100);
        region[154] = new Region("도봉구", "창제2동", 1132051200);
        region[155] = new Region("도봉구", "창제3동", 1132051300);
        region[156] = new Region("도봉구", "창제4동", 1132051400);
        region[157] = new Region("도봉구", "창제5동", 1132051500);
        region[158] = new Region("도봉구", "도봉제1동", 1132052100);
        region[159] = new Region("도봉구", "도봉제2동", 1132052200);
        region[160] = new Region("도봉구", "쌍문제1동", 1132066000);
        region[161] = new Region("도봉구", "쌍문제2동", 1132067000);
        region[162] = new Region("도봉구", "쌍문제3동", 1132068000);
        region[163] = new Region("도봉구", "쌍문제4동", 1132068100);
        region[164] = new Region("도봉구", "방학제1동", 1132069000);
        region[165] = new Region("도봉구", "방학제2동", 1132070000);
        region[166] = new Region("도봉구", "방학제3동", 1132071000);
        region[167] = new Region("노원구", "", 1135000000);
        region[168] = new Region("노원구", "월계1동", 1135056000);
        region[169] = new Region("노원구", "월계2동", 1135057000);
        region[170] = new Region("노원구", "월계3동", 1135058000);
        region[171] = new Region("노원구", "공릉1동", 1135059500);
        region[172] = new Region("노원구", "공릉2동", 1135060000);
        region[173] = new Region("노원구", "하계1동", 1135061100);
        region[174] = new Region("노원구", "하계2동", 1135061200);
        region[175] = new Region("노원구", "중계본동", 1135061900);
        region[176] = new Region("노원구", "중계1동", 1135062100);
        region[177] = new Region("노원구", "중계4동", 1135062400);
        region[178] = new Region("노원구", "중계2.3동", 1135062500);
        region[179] = new Region("노원구", "상계1동", 1135063000);
        region[180] = new Region("노원구", "상계2동", 1135064000);
        region[181] = new Region("노원구", "상계3.4동", 1135066500);
        region[182] = new Region("노원구", "상계5동", 1135067000);
        region[183] = new Region("노원구", "상계6.7동", 1135069500);
        region[184] = new Region("노원구", "상계8동", 1135070000);
        region[185] = new Region("노원구", "상계9동", 1135071000);
        region[186] = new Region("노원구", "상계10동", 1135072000);
        region[187] = new Region("은평구", "", 1138000000);
        region[188] = new Region("은평구", "녹번동", 1138051000);
        region[189] = new Region("은평구", "불광제1동", 1138052000);
        region[190] = new Region("은평구", "불광제2동", 1138053000);
        region[191] = new Region("은평구", "갈현제1동", 1138055100);
        region[192] = new Region("은평구", "갈현제2동", 1138055200);
        region[193] = new Region("은평구", "구산동", 1138056000);
        region[194] = new Region("은평구", "대조동", 1138057000);
        region[195] = new Region("은평구", "응암제1동", 1138058000);
        region[196] = new Region("은평구", "응암제2동", 1138059000);
        region[197] = new Region("은평구", "응암제3동", 1138060000);
        region[198] = new Region("은평구", "역촌동", 1138062500);
        region[199] = new Region("은평구", "신사제1동", 1138063100);
        region[200] = new Region("은평구", "신사제2동", 1138063200);
        region[201] = new Region("은평구", "증산동", 1138064000);
        region[202] = new Region("은평구", "수색동", 1138065000);
        region[203] = new Region("은평구", "진관동", 1138069000);
        region[204] = new Region("서대문구", "", 1141000000);
        region[205] = new Region("서대문구", "천연동", 1141052000);
        region[206] = new Region("서대문구", "북아현동", 1141055500);
        region[207] = new Region("서대문구", "충현동", 1141056500);
        region[208] = new Region("서대문구", "신촌동", 1141058500);
        region[209] = new Region("서대문구", "연희동", 1141061500);
        region[210] = new Region("서대문구", "홍제제1동", 1141062000);
        region[211] = new Region("서대문구", "홍제제3동", 1141064000);
        region[212] = new Region("서대문구", "홍제제2동", 1141065500);
        region[213] = new Region("서대문구", "홍은제1동", 1141066000);
        region[214] = new Region("서대문구", "홍은제2동", 1141068500);
        region[215] = new Region("서대문구", "남가좌제1동", 1141069000);
        region[216] = new Region("서대문구", "남가좌제2동", 1141070000);
        region[217] = new Region("서대문구", "북가좌제1동", 1141071000);
        region[218] = new Region("서대문구", "북가좌제2동", 1141072000);
        region[219] = new Region("마포구", "", 1144000000);
        region[220] = new Region("마포구", "아현동", 1144055500);
        region[221] = new Region("마포구", "공덕동", 1144056500);
        region[222] = new Region("마포구", "도화동", 1144058500);
        region[223] = new Region("마포구", "용강동", 1144059000);
        region[224] = new Region("마포구", "대흥동", 1144060000);
        region[225] = new Region("마포구", "염리동", 1144061000);
        region[226] = new Region("마포구", "신수동", 1144063000);
        region[227] = new Region("마포구", "서강동", 1144065500);
        region[228] = new Region("마포구", "서교동", 1144066000);
        region[229] = new Region("마포구", "합정동", 1144068000);
        region[230] = new Region("마포구", "망원제1동", 1144069000);
        region[231] = new Region("마포구", "망원제2동", 1144070000);
        region[232] = new Region("마포구", "연남동", 1144071000);
        region[233] = new Region("마포구", "성산제1동", 1144072000);
        region[234] = new Region("마포구", "성산제2동", 1144073000);
        region[235] = new Region("마포구", "상암동", 1144074000);
        region[236] = new Region("양천구", "", 1147000000);
        region[237] = new Region("양천구", "목1동", 1147051000);
        region[238] = new Region("양천구", "목2동", 1147052000);
        region[239] = new Region("양천구", "목3동", 1147053000);
        region[240] = new Region("양천구", "목4동", 1147054000);
        region[241] = new Region("양천구", "목5동", 1147055000);
        region[242] = new Region("양천구", "신월1동", 1147056000);
        region[243] = new Region("양천구", "신월2동", 1147057000);
        region[244] = new Region("양천구", "신월3동", 1147058000);
        region[245] = new Region("양천구", "신월4동", 1147059000);
        region[246] = new Region("양천구", "신월5동", 1147060000);
        region[247] = new Region("양천구", "신월6동", 1147061000);
        region[248] = new Region("양천구", "신월7동", 1147061100);
        region[249] = new Region("양천구", "신정1동", 1147062000);
        region[250] = new Region("양천구", "신정2동", 1147063000);
        region[251] = new Region("양천구", "신정3동", 1147064000);
        region[252] = new Region("양천구", "신정4동", 1147065000);
        region[253] = new Region("양천구", "신정6동", 1147067000);
        region[254] = new Region("양천구", "신정7동", 1147068000);
        region[255] = new Region("강서구", "", 1150000000);
        region[256] = new Region("강서구", "염창동", 1150051000);
        region[257] = new Region("강서구", "등촌제1동", 1150052000);
        region[258] = new Region("강서구", "등촌제2동", 1150053000);
        region[259] = new Region("강서구", "등촌제3동", 1150053500);
        region[260] = new Region("강서구", "화곡제1동", 1150054000);
        region[261] = new Region("강서구", "화곡제2동", 1150055000);
        region[262] = new Region("강서구", "화곡제3동", 1150056000);
        region[263] = new Region("강서구", "화곡제4동", 1150057000);
        region[264] = new Region("강서구", "화곡본동", 1150059000);
        region[265] = new Region("강서구", "화곡제6동", 1150059100);
        region[266] = new Region("강서구", "화곡제8동", 1150059300);
        region[267] = new Region("강서구", "가양제1동", 1150060300);
        region[268] = new Region("강서구", "가양제2동", 1150060400);
        region[269] = new Region("강서구", "가양제3동", 1150060500);
        region[270] = new Region("강서구", "발산제1동", 1150061100);
        region[271] = new Region("강서구", "우장산동", 1150061500);
        region[272] = new Region("강서구", "공항동", 1150062000);
        region[273] = new Region("강서구", "방화제1동", 1150063000);
        region[274] = new Region("강서구", "방화제2동", 1150064000);
        region[275] = new Region("강서구", "방화제3동", 1150064100);
        region[276] = new Region("구로구", "", 1153000000);
        region[277] = new Region("구로구", "신도림동", 1153051000);
        region[278] = new Region("구로구", "구로제1동", 1153052000);
        region[279] = new Region("구로구", "구로제2동", 1153053000);
        region[280] = new Region("구로구", "구로제3동", 1153054000);
        region[281] = new Region("구로구", "구로제4동", 1153055000);
        region[282] = new Region("구로구", "구로제5동", 1153056000);
        region[283] = new Region("구로구", "가리봉동", 1153059500);
        region[284] = new Region("구로구", "고척제1동", 1153072000);
        region[285] = new Region("구로구", "고척제2동", 1153073000);
        region[286] = new Region("구로구", "개봉제1동", 1153074000);
        region[287] = new Region("구로구", "개봉제2동", 1153075000);
        region[288] = new Region("구로구", "개봉제3동", 1153076000);
        region[289] = new Region("구로구", "오류제1동", 1153077000);
        region[290] = new Region("구로구", "오류제2동", 1153078000);
        region[291] = new Region("구로구", "수궁동", 1153079000);
        region[292] = new Region("금천구", "", 1154500000);
        region[293] = new Region("금천구", "가산동", 1154551000);
        region[294] = new Region("금천구", "독산제1동", 1154561000);
        region[295] = new Region("금천구", "독산제2동", 1154562000);
        region[296] = new Region("금천구", "독산제3동", 1154563000);
        region[297] = new Region("금천구", "독산제4동", 1154564000);
        region[298] = new Region("금천구", "시흥제1동", 1154567000);
        region[299] = new Region("금천구", "시흥제2동", 1154568000);
        region[300] = new Region("금천구", "시흥제3동", 1154569000);
        region[301] = new Region("금천구", "시흥제4동", 1154570000);
        region[302] = new Region("금천구", "시흥제5동", 1154571000);
        region[303] = new Region("영등포구", "", 1156000000);
        region[304] = new Region("영등포구", "영등포본동", 1156051500);
        region[305] = new Region("영등포구", "영등포동", 1156053500);
        region[306] = new Region("영등포구", "여의동", 1156054000);
        region[307] = new Region("영등포구", "당산제1동", 1156055000);
        region[308] = new Region("영등포구", "당산제2동", 1156056000);
        region[309] = new Region("영등포구", "도림동", 1156058500);
        region[310] = new Region("영등포구", "문래동", 1156060500);
        region[311] = new Region("영등포구", "양평제1동", 1156061000);
        region[312] = new Region("영등포구", "양평제2동", 1156062000);
        region[313] = new Region("영등포구", "신길제1동", 1156063000);
        region[314] = new Region("영등포구", "신길제3동", 1156065000);
        region[315] = new Region("영등포구", "신길제4동", 1156066000);
        region[316] = new Region("영등포구", "신길제5동", 1156067000);
        region[317] = new Region("영등포구", "신길제6동", 1156068000);
        region[318] = new Region("영등포구", "신길제7동", 1156069000);
        region[319] = new Region("영등포구", "대림제1동", 1156070000);
        region[320] = new Region("영등포구", "대림제2동", 1156071000);
        region[321] = new Region("영등포구", "대림제3동", 1156072000);
        region[322] = new Region("동작구", "", 1159000000);
        region[323] = new Region("동작구", "노량진제1동", 1159051000);
        region[324] = new Region("동작구", "노량진제2동", 1159052000);
        region[325] = new Region("동작구", "상도제1동", 1159053000);
        region[326] = new Region("동작구", "상도제2동", 1159054000);
        region[327] = new Region("동작구", "상도제3동", 1159055000);
        region[328] = new Region("동작구", "상도제4동", 1159056000);
        region[329] = new Region("동작구", "흑석동", 1159060500);
        region[330] = new Region("동작구", "사당제1동", 1159062000);
        region[331] = new Region("동작구", "사당제2동", 1159063000);
        region[332] = new Region("동작구", "사당제3동", 1159064000);
        region[333] = new Region("동작구", "사당제4동", 1159065000);
        region[334] = new Region("동작구", "사당제5동", 1159065100);
        region[335] = new Region("동작구", "대방동", 1159066000);
        region[336] = new Region("동작구", "신대방제1동", 1159067000);
        region[337] = new Region("동작구", "신대방제2동", 1159068000);
        region[338] = new Region("관악구", "", 1162000000);
        region[339] = new Region("관악구", "보라매동", 1162052500);
        region[340] = new Region("관악구", "청림동", 1162054500);
        region[341] = new Region("관악구", "성현동", 1162056500);
        region[342] = new Region("관악구", "행운동", 1162057500);
        region[343] = new Region("관악구", "낙성대동", 1162058500);
        region[344] = new Region("관악구", "청룡동", 1162059500);
        region[345] = new Region("관악구", "은천동", 1162060500);
        region[346] = new Region("관악구", "중앙동", 1162061500);
        region[347] = new Region("관악구", "인헌동", 1162062500);
        region[348] = new Region("관악구", "남현동", 1162063000);
        region[349] = new Region("관악구", "서원동", 1162064500);
        region[350] = new Region("관악구", "신원동", 1162065500);
        region[351] = new Region("관악구", "서림동", 1162066500);
        region[352] = new Region("관악구", "신사동", 1162068500);
        region[353] = new Region("관악구", "신림동", 1162069500);
        region[354] = new Region("관악구", "난향동", 1162071500);
        region[355] = new Region("관악구", "조원동", 1162072500);
        region[356] = new Region("관악구", "대학동", 1162073500);
        region[357] = new Region("관악구", "삼성동", 1162074500);
        region[358] = new Region("관악구", "미성동", 1162076500);
        region[359] = new Region("관악구", "난곡동", 1162077500);
        region[360] = new Region("서초구", "", 1165000000);
        region[361] = new Region("서초구", "서초1동", 1165051000);
        region[362] = new Region("서초구", "서초2동", 1165052000);
        region[363] = new Region("서초구", "서초3동", 1165053000);
        region[364] = new Region("서초구", "서초4동", 1165053100);
        region[365] = new Region("서초구", "잠원동", 1165054000);
        region[366] = new Region("서초구", "반포본동", 1165055000);
        region[367] = new Region("서초구", "반포1동", 1165056000);
        region[368] = new Region("서초구", "반포2동", 1165057000);
        region[369] = new Region("서초구", "반포3동", 1165058000);
        region[370] = new Region("서초구", "반포4동", 1165058100);
        region[371] = new Region("서초구", "방배본동", 1165059000);
        region[372] = new Region("서초구", "방배1동", 1165060000);
        region[373] = new Region("서초구", "방배2동", 1165061000);
        region[374] = new Region("서초구", "방배3동", 1165062000);
        region[375] = new Region("서초구", "방배4동", 1165062100);
        region[376] = new Region("서초구", "양재1동", 1165065100);
        region[377] = new Region("서초구", "양재2동", 1165065200);
        region[378] = new Region("서초구", "내곡동", 1165066000);
        region[379] = new Region("강남구", "", 1168000000);
        region[380] = new Region("강남구", "신사동", 1168051000);
        region[381] = new Region("강남구", "논현1동", 1168052100);
        region[382] = new Region("강남구", "논현2동", 1168053100);
        region[383] = new Region("강남구", "압구정동", 1168054500);
        region[384] = new Region("강남구", "청담동", 1168056500);
        region[385] = new Region("강남구", "삼성1동", 1168058000);
        region[386] = new Region("강남구", "삼성2동", 1168059000);
        region[387] = new Region("강남구", "대치1동", 1168060000);
        region[388] = new Region("강남구", "대치2동", 1168061000);
        region[389] = new Region("강남구", "대치4동", 1168063000);
        region[390] = new Region("강남구", "역삼1동", 1168064000);
        region[391] = new Region("강남구", "역삼2동", 1168065000);
        region[392] = new Region("강남구", "도곡1동", 1168065500);
        region[393] = new Region("강남구", "도곡2동", 1168065600);
        region[394] = new Region("강남구", "개포1동", 1168066000);
        region[395] = new Region("강남구", "개포2동", 1168067000);
        region[396] = new Region("강남구", "개포4동", 1168069000);
        region[397] = new Region("강남구", "세곡동", 1168070000);
        region[398] = new Region("강남구", "일원본동", 1168072000);
        region[399] = new Region("강남구", "일원1동", 1168073000);
        region[400] = new Region("강남구", "일원2동", 1168074000);
        region[401] = new Region("강남구", "수서동", 1168075000);
        region[402] = new Region("송파구", "", 1171000000);
        region[403] = new Region("송파구", "풍납1동", 1171051000);
        region[404] = new Region("송파구", "풍납2동", 1171052000);
        region[405] = new Region("송파구", "거여1동", 1171053100);
        region[406] = new Region("송파구", "거여2동", 1171053200);
        region[407] = new Region("송파구", "마천1동", 1171054000);
        region[408] = new Region("송파구", "마천2동", 1171055000);
        region[409] = new Region("송파구", "방이1동", 1171056100);
        region[410] = new Region("송파구", "방이2동", 1171056200);
        region[411] = new Region("송파구", "오륜동", 1171056600);
        region[412] = new Region("송파구", "오금동", 1171057000);
        region[413] = new Region("송파구", "송파1동", 1171058000);
        region[414] = new Region("송파구", "송파2동", 1171059000);
        region[415] = new Region("송파구", "석촌동", 1171060000);
        region[416] = new Region("송파구", "삼전동", 1171061000);
        region[417] = new Region("송파구", "가락본동", 1171062000);
        region[418] = new Region("송파구", "가락1동", 1171063100);
        region[419] = new Region("송파구", "가락2동", 1171063200);
        region[420] = new Region("송파구", "문정1동", 1171064100);
        region[421] = new Region("송파구", "문정2동", 1171064200);
        region[422] = new Region("송파구", "장지동", 1171064600);
        region[423] = new Region("송파구", "위례동", 1171064700);
        region[424] = new Region("송파구", "잠실본동", 1171065000);
        region[425] = new Region("송파구", "잠실2동", 1171067000);
        region[426] = new Region("송파구", "잠실3동", 1171068000);
        region[427] = new Region("송파구", "잠실4동", 1171069000);
        region[428] = new Region("송파구", "잠실6동", 1171071000);
        region[429] = new Region("송파구", "잠실7동", 1171072000);
        region[430] = new Region("강동구", "", 1174000000);
        region[431] = new Region("강동구", "강일동", 1174051500);
        region[432] = new Region("강동구", "상일동", 1174052000);
        region[433] = new Region("강동구", "명일제1동", 1174053000);
        region[434] = new Region("강동구", "명일제2동", 1174054000);
        region[435] = new Region("강동구", "고덕제1동", 1174055000);
        region[436] = new Region("강동구", "고덕제2동", 1174056000);
        region[437] = new Region("강동구", "암사제1동", 1174057000);
        region[438] = new Region("강동구", "암사제2동", 1174058000);
        region[439] = new Region("강동구", "암사제3동", 1174059000);
        region[440] = new Region("강동구", "천호제1동", 1174060000);
        region[441] = new Region("강동구", "천호제2동", 1174061000);
        region[442] = new Region("강동구", "천호제3동", 1174062000);
        region[443] = new Region("강동구", "성내제1동", 1174064000);
        region[444] = new Region("강동구", "성내제2동", 1174065000);
        region[445] = new Region("강동구", "성내제3동", 1174066000);
        region[446] = new Region("강동구", "길동", 1174068500);
        region[447] = new Region("강동구", "둔촌제1동", 1174069000);
        region[448] = new Region("강동구", "둔촌제2동", 1174070000);

        region_index = 400;
        region_code = 1171065000;
        for(int i=0; i<region.length; i++){
            if(region[i].getDong().equals(dong)){
                region_index = i;
                region_code = region[i].getCode();
                break;
            }
        }
  //      Log.d("region_index", String.valueOf(region_index));
    //    Log.d("region_code", String.valueOf(region_code));

        int R_I = region_index;
//        Log.d("test for region_index", String.valueOf(R_I)); // 매칭이 안되서 오류


        try{
            document[region_index] = Jsoup.connect("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone="+region_code).get();
            //document[Integer.parseInt(region_index)] = Jsoup.connect("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone="+region_code).get();



            int cnt = 0;
            int j=0;
            /*
            for(int i=1; i<24; i++){
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[0]); // 오늘
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[1]);
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[2]);
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[3]);
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[4]);
                element[R_I][i] = document[R_I].select("data[seq="+i+"] "+weatherData.WData[5]);
            }
            */
            element[R_I][0] = document[R_I].select("item category");
            for(int i=1; i<145; i++){
                if(document[R_I].select("data[seq="+i+"] "+weatherData.WData[0]).text().equals(null)){
                    break;
                }
                if((i+1)%6 == 0){
                    j = 0;
                    cnt++;
                }
                element[R_I][i] = document[R_I].select("data[seq="+cnt+"] "+weatherData.WData[j]);
                j++;
            }
/*
            element[R_I][1] = document[R_I].select("data[seq=0] "+weatherData.WData[0]); // 오늘
            element[R_I][2] = document[R_I].select("data[seq=0] "+weatherData.WData[1]);
            element[R_I][3] = document[R_I].select("data[seq=0] "+weatherData.WData[2]);
            element[R_I][4] = document[R_I].select("data[seq=0] "+weatherData.WData[3]);
            element[R_I][5] = document[R_I].select("data[seq=0] "+weatherData.WData[4]);
            element[R_I][6] = document[R_I].select("data[seq=0] "+weatherData.WData[5]);
            element[R_I][7] = document[R_I].select("data[seq=1] "+weatherData.WData[0]); // 내일
            element[R_I][8] = document[R_I].select("data[seq=1] "+weatherData.WData[1]);
            element[R_I][9] = document[R_I].select("data[seq=1] "+weatherData.WData[2]);
            element[R_I][10] = document[R_I].select("data[seq=1] "+weatherData.WData[3]);
            element[R_I][11] = document[R_I].select("data[seq=1] "+weatherData.WData[4]);
            element[R_I][12] = document[R_I].select("data[seq=1] "+weatherData.WData[5]);
            element[R_I][13] = document[R_I].select("data[seq=2] "+weatherData.WData[0]); // 모레
            element[R_I][14] = document[R_I].select("data[seq=2] "+weatherData.WData[1]);
            element[R_I][15] = document[R_I].select("data[seq=2] "+weatherData.WData[2]);
            element[R_I][16] = document[R_I].select("data[seq=2] "+weatherData.WData[3]);
            element[R_I][17] = document[R_I].select("data[seq=2] "+weatherData.WData[4]);
            element[R_I][18] = document[R_I].select("data[seq=2] "+weatherData.WData[5]);
*/

            for(int i=0; i<element[R_I].length; i++){
                result[i] = element[R_I][i].text();
                //Log.d("test_weather", result[i]);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onProgressUpdate(Object[] objects) {

    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
    }


}