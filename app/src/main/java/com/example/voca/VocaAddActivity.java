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

    private WebView mwv;//Mobile Web View

    static ArrayList<Voca> vocaArr = new ArrayList<>();

    static boolean is_through = false;

    View vocalist;
    Button btnAdd, btnSearch;
    EditText etAddEng, etAddKor;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocaadd);

        vocalist = getLayoutInflater().inflate(R.layout.vocalist, null, false);
        tv = (TextView) vocalist.findViewById(R.id.tv);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        etAddEng = (EditText) findViewById(R.id.etAddEng);
        etAddKor = (EditText) findViewById(R.id.etAddKor);

        mwv = (WebView) findViewById(R.id.webView);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        WebSettings mws = mwv.getSettings();//Mobile Web Setting
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        mws.setDomStorageEnabled(true);

        mwv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Voca voca = new Voca();

                voca.eng = String.valueOf(etAddEng.getText());
                voca.kor = String.valueOf(etAddKor.getText());
                if (!whatLanguage(voca.eng).equals("eng")) {
                    Toast.makeText(getApplicationContext(), "영어단어 란에는 영어 소문자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etAddEng.setText("");
                }
                if (!whatLanguage(voca.kor).equals("kor")) {
                    Toast.makeText(getApplicationContext(), "단어 뜻 란에는 한국어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etAddKor.setText("");
                }
                if (whatLanguage(voca.eng).equals("eng") && whatLanguage(voca.kor).equals("kor")) {
                    vocaArr.add(voca);
                    Toast.makeText(getApplicationContext(), "단어가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    etAddEng.setText("");
                    etAddKor.setText("");

//                for (int i = 0; i < vocaArr.size(); i++) {
//                    if (vocaArr.get(i).eng.equals(voca.eng)) {
//                        voca = null;
//                        Toast.makeText(getApplicationContext(),"이미 있는 단어입니다.",Toast.LENGTH_SHORT).show();
//                    } else {
//                        vocaArr.add(voca);
//                        Toast.makeText(getApplicationContext(),"이미 있는 단어입니다.",Toast.LENGTH_SHORT).show();
//                    }
//                }
                    save();
                    tv.setText("");
                    is_through = true;
                    showVoca();
                }
            }
        });

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

    private void showVoca() {
        for (int i = 0; i < vocaArr.size(); i++) {
            tv.append((i + 1) + ". " + vocaArr.get(i).eng + "  : " + vocaArr.get(i).kor + "\n");
        }
    }

    public String goUrl(String url) {
        return "https://m.search.naver.com/search.naver?query=" + url + "&where=m_ldic&sm=msv_hty";
    }

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
