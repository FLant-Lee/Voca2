package com.example.voca;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;

public class VocaAddActivity extends Activity {

    private WebView mwv;                                    //Mobile Web View.

    static ArrayList<Voca> vocaArr = new ArrayList<>();     //voca.txt에 넣고 빼기 전에 거치는 동적배열입니다
                                                            //입력과 출력값은 Voca클래스 객체를 통해서 이루어집니다.

    static boolean is_through = false;                      //VocaListActivity에 있는 TextView에 데이터가
                                                            //중복으로 겹쳐보이지 않게 하기위해 설정한 플래그 입니다.
                                                            //안하게 되면 voca.txt파일에 있는 내용을 vocaArr로
                                                            //이동시에 중복으로 내용이 담겨집니다. 표현이 어렵네요...

    Button btnAdd, btnSearch;                               //단어추가하기 , 단어 (네이버 사전)검색하기
    EditText etAddEng, etAddKor;                            //영어입력 칸, 뜻 입력 칸

    View vocalist;                                          //다른 클래스 (VocaListActivity)에 있는 vocalist.xml의
    TextView tv;                                            //tv란 텍스트뷰 , 인플레이터를 사용한다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocaadd);
        //vocaadd.xml과 연결

        //다른 액티비티(단어장보기, VocaListActivity)의 xml에 있는 vocalist.xml을 사용하기위해서 인플래이터를 사용한다.
        vocalist = getLayoutInflater().inflate(R.layout.vocalist, null, false);
        //TextView tv를 만들어 인스턴스화 시켜줌.
        tv = (TextView) vocalist.findViewById(R.id.tv);
        btnAdd = (Button) findViewById(R.id.btnAdd);        // 단어 추가하기 버튼
        etAddEng = (EditText) findViewById(R.id.etAddEng);  // 영단어 입력 칸
        etAddKor = (EditText) findViewById(R.id.etAddKor);  // 단어뜻 입력 칸

        mwv = (WebView) findViewById(R.id.webView);         // 웹뷰 참조 변수입니다.
        btnSearch = (Button) findViewById(R.id.btnSearch);  // 웹뷰를 실행하기위한 단어검색 참조변수 입니다.

        WebSettings mws = mwv.getSettings();//Mobile Web Setting
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        mws.setDomStorageEnabled(true);

        //웹뷰가 실행 이벤트 처리입니다.
        mwv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 단어추가 이벤트 처리 입니다.
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Voca클래스를 voca라는 참조변수에 인스턴스화 시키고
                Voca voca = new Voca();

                //voca클래스의 객체 eng에 영어단어 입력값을 넣습니다.
                voca.eng = String.valueOf(etAddEng.getText());
                //voca클래스의 객체 kor에 단어뜻 입력값을 넣습니다.
                voca.kor = String.valueOf(etAddKor.getText());
                // whatLanguage라는 함수 조건문을 통해서 voca.eng의 들어온 값이 영어가 맞는지 확인하는 처리단계입니다.
                if (!whatLanguage(voca.eng).equals("eng")) {
                    //영어가 아니면 토스트 메세지를 띄우고 editText입력값을 지워버림
                    Toast.makeText(getApplicationContext(), "영어단어 란에는 영어 소문자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etAddEng.setText("");
                }
                if (!whatLanguage(voca.kor).equals("kor")) {
                    //한글이 아니면 토스트 메세지를 띄우고 editText입력값을 지워버림
                    Toast.makeText(getApplicationContext(), "단어 뜻 란에는 한국어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etAddKor.setText("");
                }
                if (whatLanguage(voca.eng).equals("eng") && whatLanguage(voca.kor).equals("kor")) {
                    //영어는 영어대로 한글은 한글대로 들어간다면 추가 되었다는 메세지를 띄워주고
                    //동적배열에 editText값을 넣었던 voca 클래스에 있는 값을 add합니다.
                    vocaArr.add(voca);
                    Toast.makeText(getApplicationContext(), "단어가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    //그리고 나서 editText 입력했던 값을 지워버림
                    etAddEng.setText("");
                    etAddKor.setText("");

                    //저장하고
                    save();
                    //기존에 있던것을 지웁니다. == 왜냐하면 만든 save메서드는 덮어쓰기 방식이므로 기존에 있던게 불필요
                    tv.setText("");
                    //VocaListActivity에 있는 TextView에 데이터가
                    //중복으로 겹쳐보이지 않게 하기위해 설정한 플래그 입니다.
                    is_through = true;
                    //tv에 vocaArr에 있는 데이터를 띄움
                    showVoca();
                }
            }
        });

        // 들어오는 값이 영어가 맞는지 확인 후 웹뷰를 실행 해주는 메서드 입니다.
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!whatLanguage(etAddEng.getText().toString()).equals("eng")) {
                    Toast.makeText(getApplicationContext(), "영어단어 란에는 영어 소문자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etAddEng.setText("");
                }
                if (whatLanguage(etAddEng.getText().toString()).equals("eng")) {
                    mwv.setVisibility(View.VISIBLE);
                    mwv.loadUrl(goUrl(etAddEng.getText().toString()));
                    etAddEng.setText("");
                    etAddKor.setText("");
                }
            }
        });
    }

    // 덮어쓰기 방식으로 vocaArr에 있는 값들을 배열 사이즈 만큼 'temp.eng,temp.kor'로 써주는 메서드 입니다.
    public void save() {
        try {
            FileWriter fw = new FileWriter(getFilesDir() + "/voca.txt", false);

            for (int i = 0; i < vocaArr.size(); i++) {
                Voca temp = vocaArr.get(i);
                fw.write(temp.eng + "," + temp.kor + "\n");
            }
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 동적배열 vocaArr에 있는 내용을 배열크기만큼 tv에 출력해주는 메서드 입니다.
    private void showVoca() {
        for (int i = 0; i < vocaArr.size(); i++) {
            tv.append((i + 1) + ". " + vocaArr.get(i).eng + "  : " + vocaArr.get(i).kor + "\n");
        }
    }

    // 인자 값에 따라 naver사전에 띄우는 주소값이 다릅니다.
    // get방식
    public String goUrl(String url) {
        return "https://m.search.naver.com/search.naver?query=" + url + "&where=m_ldic&sm=msv_hty";
    }

    // 웹 서칭을 통해 찾은 str값이 한글인지 영어인지 숫자인지 분별해주는 메서드 입니다.
    public String whatLanguage(String str) {
        if (str.matches("^[가-힣ㄱ-ㅎㅏ-ㅣ]*$")) {
            return "kor";
        } else if (str.matches("^[a-z]*$")) {
            return "eng";
        } else if (str.matches("^[0-9]]*$")) {
            return "num";
        }
        return "";
    }

}
